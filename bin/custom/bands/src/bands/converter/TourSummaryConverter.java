package bands.converter;

import bands.data.TourSummaryData;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class TourSummaryConverter implements Converter<ProductModel, TourSummaryData> {
    private final Populator<ProductModel, TourSummaryData> tourSummaryPopulator;

    @Autowired
    public TourSummaryConverter(final Populator<ProductModel, TourSummaryData> tourSummaryPopulator) {
        this.tourSummaryPopulator = tourSummaryPopulator;
    }

    @Override
    public TourSummaryData convert(final ProductModel source) throws ConversionException {
        final TourSummaryData target = new TourSummaryData();
        return convert(source, target);
    }

    @Override
    public TourSummaryData convert(final ProductModel source, final TourSummaryData target) throws ConversionException {
        tourSummaryPopulator.populate(source, target);
        return target;
    }

    @Override
    public List<TourSummaryData> convertAll(final Collection<? extends ProductModel> source) throws ConversionException {
        return source == null ? Collections.emptyList()
                : source.stream().map(this::convert).toList();
    }
}
