package bands.facade;

import bands.data.BandData;
import bands.data.BandManagerData;
import bands.data.BandProducerData;
import bands.data.MusicianData;
import bands.data.TourSummaryData;
import bands.facade.impl.DefaultBandFacade;
import bands.model.BandManagerModel;
import bands.model.BandModel;
import bands.model.BandProducerModel;
import bands.model.MusicianModel;
import bands.service.GenericItemModelService;
import concerttours.model.ConcertModel;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.catalog.CatalogVersionService;
import de.hybris.platform.catalog.model.CatalogModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import de.hybris.platform.servicelayer.model.ModelService;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@IntegrationTest
public class DefaultBandFacadeIntegrationTest extends ServicelayerTransactionalTest {
    @Resource
    private GenericItemModelService<BandModel> bandService;
    @Resource
    private ModelService modelService;
    @Resource
    private CatalogVersionService catalogVersionService;
    @Resource
    private Converter<BandModel, BandData> basicBandConverter;
    @Resource
    private Converter<BandManagerModel, BandManagerData> bandManagerConverter;
    @Resource
    private Converter<MusicianModel, MusicianData> musicianConverter;
    @Resource
    private Converter<BandProducerModel, BandProducerData> bandProducerConverter;
    @Resource
    private Converter<ProductModel, TourSummaryData> tourSummaryConverter;
    private BandFacade bandFacade;
    private CatalogVersionModel catalogVersion;

    @Before
    public void setup() {
        bandFacade = new DefaultBandFacade(bandService, basicBandConverter, bandManagerConverter,
                musicianConverter, tourSummaryConverter, bandProducerConverter);
        CatalogModel catalog = modelService.create(CatalogModel.class);
        catalog.setId("catalog");
        catalogVersion = modelService.create(CatalogVersionModel.class);
        catalogVersion.setCatalog(catalog);
        catalogVersion.setVersion("Online");
        modelService.saveAll(catalog, catalogVersion);
        catalogVersionService.setSessionCatalogVersions(Collections.singleton(catalogVersion));
    }

    @Test
    public void shouldThrowUnknownIdentifierExceptionWhenGettingByNonExistentCode() {
        assertThrows(UnknownIdentifierException.class, () -> bandFacade.get("code1"));
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenCodeIsNullForGettingByCode() {
        assertThrows(IllegalArgumentException.class, () -> bandFacade.get(null));
    }

    @Test
    public void shouldReturnBandDataWhenGettingByCode() {
        BandModel band = createAndSaveBandModel("band1");
        BandData result = bandFacade.get(band.getCode());
        assertEquals(band.getCode(), result.getId());
        assertEquals(band.getName(), result.getName());
        assertEquals(band.getManager().getCode(), result.getManager().getId());
        assertEquals(band.getMembers().size(), result.getMembers().size());
        assertEquals(band.getTours().size(), result.getTours().size());
        assertEquals(band.getTours().stream().toList().get(0).getVariants().size(), result.getTours().size());
    }

    @Test
    public void shouldGetAll() {
        int initialSize = bandFacade.getAll().size();
        BandModel band1 = createAndSaveBandModel("band1");
        BandModel band2 = createAndSaveBandModel("band2");
        List<BandData> result = bandFacade.getAll();
        assertEquals(initialSize + 2, result.size());
        assertTrue(result.stream().map(BandData::getId).anyMatch(id -> id.equals(band1.getCode())));
        assertTrue(result.stream().map(BandData::getId).anyMatch(id -> id.equals(band2.getCode())));
    }

    private BandModel createAndSaveBandModel(String code) {
        BandModel bandModel = modelService.create(BandModel.class);
        bandModel.setCode(code);
        bandModel.setName("name_" + code);
        BandManagerModel bandManager = modelService.create(BandManagerModel.class);
        bandManager.setCode("manager_" + code);
        MusicianModel musician = modelService.create(MusicianModel.class);
        musician.setCode("musician_" + code);
        ConcertModel concert = modelService.create(ConcertModel.class);
        concert.setCatalogVersion(catalogVersion);
        concert.setCode("concert_" + code);
        ProductModel product = modelService.create(ProductModel.class);
        product.setCatalogVersion(catalogVersion);
        product.setCode("tour_" + code);
        product.setVariants(List.of(concert));
        bandModel.setManager(bandManager);
        bandModel.setMembers(Set.of(musician));
        modelService.save(bandModel);
        return bandModel;
    }
}
