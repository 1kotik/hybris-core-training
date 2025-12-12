package concerttours.populator;

import bands.data.TourData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TourPopulator implements Populator<ProductModel, TourData> {
    @Override
    public void populate(final ProductModel source, final TourData target) throws ConversionException {
        if(source == null || target == null) {
            return;
        }
        final Locale locale = Locale.ENGLISH;
        target.setId(source.getCode());
        target.setTourName(source.getName(locale));
        target.setDescription(source.getDescription(locale));
    }
}
