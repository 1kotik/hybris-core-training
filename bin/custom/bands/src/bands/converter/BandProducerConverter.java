package bands.converter;

import bands.data.BandProducerData;
import bands.model.BandProducerModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BandProducerConverter implements Converter<BandProducerModel, BandProducerData> {
    private final Populator<BandProducerModel, BandProducerData> bandProducerPopulator;

    @Autowired
    public BandProducerConverter(Populator<BandProducerModel, BandProducerData> bandProducerPopulator) {
        this.bandProducerPopulator = bandProducerPopulator;
    }

    @Override
    public BandProducerData convert(BandProducerModel bandProducerModel) throws ConversionException {
        BandProducerData bandProducerData = new BandProducerData();
        return convert(bandProducerModel, bandProducerData);
    }

    @Override
    public BandProducerData convert(BandProducerModel bandProducerModel, BandProducerData bandProducerData) throws ConversionException {
        bandProducerPopulator.populate(bandProducerModel, bandProducerData);
        return bandProducerData;
    }
}
