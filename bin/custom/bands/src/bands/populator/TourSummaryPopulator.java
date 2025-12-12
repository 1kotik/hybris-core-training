package bands.populator;

import bands.data.TourSummaryData;
import concerttours.model.ConcertModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Locale;

@Component
public class TourSummaryPopulator implements Populator<ProductModel, TourSummaryData> {
    @Override
    public void populate(final ProductModel source, final TourSummaryData target) throws ConversionException {
        if (source == null || target == null) {
            return;
        }
        target.setId(source.getCode());
        target.setTourName(source.getName(Locale.ENGLISH));
        final List<ConcertModel> concerts = source.getVariants().stream()
                .filter(ConcertModel.class::isInstance)
                .map(ConcertModel.class::cast)
                .toList();
        target.setNumberOfConcerts(String.valueOf(concerts.size()));
    }
}
