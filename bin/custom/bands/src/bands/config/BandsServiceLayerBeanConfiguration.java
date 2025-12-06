package bands.config;

import bands.dao.GenericItemModelDAO;
import bands.dao.impl.DefaultGenericItemModelDAO;
import bands.model.BandModel;
import bands.service.GenericItemModelService;
import bands.service.impl.DefaultGenericItemModelService;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BandsServiceLayerBeanConfiguration {
    private final FlexibleSearchService flexibleSearchService;

    @Autowired
    public BandsServiceLayerBeanConfiguration(final FlexibleSearchService flexibleSearchService) {
        this.flexibleSearchService = flexibleSearchService;
    }

    @Bean
    public GenericItemModelDAO<BandModel> bandDAO() {
        return new DefaultGenericItemModelDAO<>(BandModel.CODE, BandModel._TYPECODE, flexibleSearchService);
    }

    @Bean
    public GenericItemModelService<BandModel> bandService() {
        return new DefaultGenericItemModelService<>(bandDAO(), "Band");
    }
}
