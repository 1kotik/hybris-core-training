package concerttours.facade.impl;

import bands.data.ConcertSummaryData;
import bands.data.TourData;
import concerttours.facade.TourFacade;
import concerttours.model.ConcertModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("defaultTourFacade")
public class DefaultTourFacade implements TourFacade {
    private final ProductService tourService;
    private final Converter<ProductModel, TourData> tourConverter;
    private final Converter<ConcertModel, ConcertSummaryData> concertSummaryConverter;

    public DefaultTourFacade(ProductService tourService,
                             Converter<ProductModel, TourData> tourConverter,
                             Converter<ConcertModel, ConcertSummaryData> concertSummaryConverter) {
        this.tourService = tourService;
        this.tourConverter = tourConverter;
        this.concertSummaryConverter = concertSummaryConverter;
    }

    @Override
    public TourData get(final String code) {
        if (code == null) {
            throw new IllegalArgumentException("Code cannot be null");
        }
        final ProductModel tour = tourService.getProductForCode(code);
        if (tour == null) {
            return null;
        }
        final TourData tourData = tourConverter.convert(tour);
        if(tourData == null) {
            return null;
        }
        final List<ConcertModel> concerts = tour.getVariants().stream()
                .filter(ConcertModel.class::isInstance)
                .map(ConcertModel.class::cast)
                .toList();
        final List<ConcertSummaryData> concertSummaryData = concertSummaryConverter.convertAll(concerts);
        tourData.setConcerts(concertSummaryData);
        return tourData;
    }
}
