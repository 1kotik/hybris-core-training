package concerttours.facade.impl;

import bands.data.ConcertSummaryData;
import bands.data.TourData;
import bands.service.GenericItemModelService;
import concerttours.enums.ConcertType;
import concerttours.facade.TourFacade;
import concerttours.model.ConcertModel;
import de.hybris.platform.core.model.product.ProductModel;
import de.hybris.platform.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("defaultTourFacade")
public class DefaultTourFacade implements TourFacade {
    private final ProductService tourService;

    @Autowired
    public DefaultTourFacade(ProductService tourService) {
        this.tourService = tourService;
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
        final List<ConcertSummaryData> concerts = getConcertHistory(tour);

        final TourData tourData = setBriefTourData(tour);
        tourData.setConcerts(concerts);
        return tourData;
    }

    private List<ConcertSummaryData> getConcertHistory(final ProductModel tour) {
        final List<ConcertModel> concerts = tour.getVariants().stream()
                .filter(ConcertModel.class::isInstance)
                .map(ConcertModel.class::cast)
                .toList();
        return concerts.isEmpty() ? new ArrayList<>()
                : concerts.stream().map(this::setConcertSummary).toList();
    }

    private ConcertSummaryData setConcertSummary(final ConcertModel concert) {
        final ConcertSummaryData concertSummary = new ConcertSummaryData();
        final String concertType = concert.getConcertType() == ConcertType.INDOOR ? "Indoors" : "Outdoors";
        concertSummary.setId(concert.getCode());
        concertSummary.setDate(concert.getDate());
        concertSummary.setVenue(concert.getVenue());
        concertSummary.setType(concertType);
        return concertSummary;
    }

    private TourData setBriefTourData(final ProductModel tour) {
        final TourData tourData = new TourData();
        tourData.setId(tour.getCode());
        tourData.setTourName(tour.getName());
        tourData.setDescription(tour.getDescription());
        return tourData;
    }
}
