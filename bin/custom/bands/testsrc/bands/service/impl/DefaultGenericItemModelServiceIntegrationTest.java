package bands.service.impl;

import bands.dao.GenericItemModelDAO;
import bands.dao.impl.DefaultGenericItemModelDAO;
import bands.model.BandModel;
import bands.service.GenericItemModelService;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@IntegrationTest
public class DefaultGenericItemModelServiceIntegrationTest extends ServicelayerTransactionalTest {
    @Resource
    private ModelService modelService;
    @Resource
    private FlexibleSearchService flexibleSearchService;
    private GenericItemModelService<BandModel> bandService;

    @Before
    public void setUp() {
        GenericItemModelDAO<BandModel> bandDAO = new DefaultGenericItemModelDAO<>(
                BandModel.CODE,
                BandModel._TYPECODE,
                flexibleSearchService
        );
        bandService = new DefaultGenericItemModelService<>(bandDAO, "Band");
    }

    @Test
    public void shouldFindByCode() {
        String code = "code";
        createAndSaveBand(code);
        BandModel result = bandService.findByCode(code);
        assertNotNull(result);
        assertEquals(code, result.getCode());
    }

    @Test
    public void shouldThrowUnknownIdentifierExceptionWhenFindingByCode() {
        assertThrows(UnknownIdentifierException.class, () -> bandService.findByCode("code"));
    }

    @Test
    public void shouldThrowAmbiguousIdentifierExceptionWhenFindingByCode() {
        String code = "code";
        createAndSaveBand(code);
        createAndSaveBand(code);
        assertThrows(AmbiguousIdentifierException.class, () -> bandService.findByCode(code));
    }

    @Test
    public void shouldFindAll() {
        int initialSize = bandService.findAll().size();
        createAndSaveBand("band1");
        createAndSaveBand("band2");
        List<BandModel> results = bandService.findAll();
        assertNotNull(results);
        assertEquals(initialSize + 2, results.size());
    }

    private void createAndSaveBand(String code) {
        BandModel band = modelService.create(BandModel.class);
        band.setCode(code);
        modelService.save(band);
    }
}