package concerttours.facade;

import bands.data.TourData;

public interface TourFacade {
    TourData get(final String code);
}
