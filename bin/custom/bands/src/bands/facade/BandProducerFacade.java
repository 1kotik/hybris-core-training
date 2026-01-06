package bands.facade;

import bands.data.BandProducerExtendedData;

public interface BandProducerFacade {
    BandProducerExtendedData get(final String code);
}
