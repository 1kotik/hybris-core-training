package bands.populator;

import bands.data.BandProducerData;
import bands.data.BandProducerExtendedData;
import bands.model.BandProducerModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BandProducerExtendedDataPopulator implements Populator<BandProducerModel, BandProducerExtendedData> {
    private final Populator<BandProducerModel, BandProducerData> bandProducerDataPopulator;

    @Autowired
    public BandProducerExtendedDataPopulator(Populator<BandProducerModel, BandProducerData> bandProducerDataPopulator) {
        this.bandProducerDataPopulator = bandProducerDataPopulator;
    }

    @Override
    public void populate(BandProducerModel bandProducerModel, BandProducerExtendedData bandProducerExtendedData) throws ConversionException {
        bandProducerDataPopulator.populate(bandProducerModel, bandProducerExtendedData);
        bandProducerExtendedData.setDescription(bandProducerModel.getDescription());
    }
}
