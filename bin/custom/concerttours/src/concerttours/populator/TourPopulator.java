package concerttours.populator;

import bands.data.TourData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.i18n.I18NService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TourPopulator implements Populator<ProductModel, TourData> {
    private final I18NService i18NService;

    @Autowired
    public TourPopulator(final I18NService i18NService) {
        this.i18NService = i18NService;
    }

    @Override
    public void populate(final ProductModel source, final TourData target) throws ConversionException {
        if(source == null || target == null) {
            return;
        }
        final Locale locale = i18NService.getCurrentLocale();
        target.setId(source.getCode());
        target.setTourName(source.getName(locale));
        target.setDescription(source.getDescription(locale));
    }
}
