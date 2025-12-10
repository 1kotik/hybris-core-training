package concerttours.converter;

import bands.data.TourData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourConverter implements Converter<ProductModel, TourData> {
    private final Populator<ProductModel, TourData> tourPopulator;

    @Autowired
    public TourConverter(final Populator<ProductModel, TourData> tourPopulator) {
        this.tourPopulator = tourPopulator;
    }

    @Override
    public TourData convert(final ProductModel source) throws ConversionException {
        final TourData target = new TourData();
        return convert(source, target);
    }

    @Override
    public TourData convert(final ProductModel source, final TourData target) throws ConversionException {
        tourPopulator.populate(source, target);
        return target;
    }
}
