package bands.converter;

import bands.data.BandData;
import bands.data.BandProducerData;
import bands.data.BandProducerExtendedData;
import bands.model.BandModel;
import bands.model.BandProducerModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BandProducerExtendedDataConverter implements Converter<BandProducerModel, BandProducerExtendedData> {
    private final Converter<BandModel, BandData> bandConverter;
    private final Populator<BandProducerModel, BandProducerExtendedData> bandProducerExtendedDataPopulator;

    @Autowired
    public BandProducerExtendedDataConverter(
            Converter<BandModel, BandData> bandConverter, Populator<BandProducerModel,
            BandProducerExtendedData> bandProducerExtendedDataPopulator) {
        this.bandConverter = bandConverter;
        this.bandProducerExtendedDataPopulator = bandProducerExtendedDataPopulator;
    }

    @Override
    public BandProducerExtendedData convert(BandProducerModel bandProducerModel) throws ConversionException {
        BandProducerExtendedData bandProducerExtendedData = new BandProducerExtendedData();
        return convert(bandProducerModel, bandProducerExtendedData);
    }

    @Override
    public BandProducerExtendedData convert(BandProducerModel bandProducerModel, BandProducerExtendedData bandProducerExtendedData) throws ConversionException {
        bandProducerExtendedDataPopulator.populate(bandProducerModel, bandProducerExtendedData);
        List<BandData> bands = bandConverter.convertAll(bandProducerModel.getBands());
        bandProducerExtendedData.setBands(bands);
        return bandProducerExtendedData;
    }
}
