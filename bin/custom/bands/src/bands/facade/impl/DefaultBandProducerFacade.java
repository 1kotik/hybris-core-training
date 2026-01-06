package bands.facade.impl;

import bands.data.BandProducerExtendedData;
import bands.facade.BandProducerFacade;
import bands.model.BandProducerModel;
import bands.service.GenericItemModelService;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultBandProducerFacade implements BandProducerFacade {
    private final GenericItemModelService<BandProducerModel> bandProducerService;
    private final Converter<BandProducerModel, BandProducerExtendedData> bandProducerExtendedDataConverter;

    @Autowired
    public DefaultBandProducerFacade(
            GenericItemModelService<BandProducerModel> bandProducerService,
            Converter<BandProducerModel, BandProducerExtendedData> bandProducerExtendedDataConverter) {
        this.bandProducerService = bandProducerService;
        this.bandProducerExtendedDataConverter = bandProducerExtendedDataConverter;
    }

    @Override
    public BandProducerExtendedData get(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Code cannot be null");
        }
        BandProducerModel bandProducerModel = bandProducerService.findByCode(code);
        return bandProducerExtendedDataConverter.convert(bandProducerModel);
    }
}
