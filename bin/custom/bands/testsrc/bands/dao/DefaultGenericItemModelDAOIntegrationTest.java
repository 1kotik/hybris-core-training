package bands.dao;

import bands.dao.impl.DefaultGenericItemModelDAO;
import bands.model.BandManagerModel;
import bands.model.BandModel;
import de.hybris.bootstrap.annotations.IntegrationTest;
import de.hybris.platform.servicelayer.ServicelayerTransactionalTest;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@IntegrationTest
public class DefaultGenericItemModelDAOIntegrationTest extends ServicelayerTransactionalTest {
    private GenericItemModelDAO<BandModel> bandDAO;
    @Resource
    private ModelService modelService;
    @Resource
    private FlexibleSearchService flexibleSearchService;

    @Before
    public void setup() {
        bandDAO = new DefaultGenericItemModelDAO<>(BandModel.CODE, BandModel._TYPECODE, flexibleSearchService);
    }

    @Test
    public void shouldFindByCode() {
        String bandCode = "code";
        String bandHistory = "history";
        BandModel band = modelService.create(BandModel.class);
        BandManagerModel manager = modelService.create(BandManagerModel.class);
        manager.setCode("manager");
        band.setCode(bandCode);
        band.setName("band");
        band.setHistory(bandHistory);
        band.setAlbumSales(1000000L);
        band.setManager(manager);
        modelService.save(band);
        List<BandModel> results = bandDAO.findByCode(bandCode);
        BandModel result = results.get(0);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals(bandCode, result.getCode());
        assertEquals(bandHistory, result.getHistory());
        assertEquals(manager.getCode(), result.getManager().getCode());
    }

    @Test
    public void shouldReturnEmptyListWhenFindByCode() {
        List<BandModel> results = bandDAO.findByCode("code");
        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    public void shouldFindAll() {
        int initialSize = bandDAO.findAll().size();
        createAndSaveBand("band1");
        createAndSaveBand("band2");
        List<BandModel> results = bandDAO.findAll();
        assertEquals(initialSize + 2, results.size());
    }

    private void createAndSaveBand(String code) {
        BandModel band = modelService.create(BandModel.class);
        band.setCode(code);
        modelService.save(band);
    }
}
