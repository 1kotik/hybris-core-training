package bands.populator;

import bands.data.BandProducerData;
import bands.model.BandProducerModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class BandProducerPopulator implements Populator<BandProducerModel, BandProducerData> {
    @Override
    public void populate(final BandProducerModel bandProducerModel, final BandProducerData bandProducerData) throws ConversionException {
        if (bandProducerModel == null || bandProducerData == null) {
            return;
        }
        bandProducerData.setId(bandProducerModel.getCode());
        bandProducerData.setFirstName(bandProducerModel.getFirstName());
        bandProducerData.setLastName(bandProducerModel.getLastName());
        bandProducerData.setEmail(bandProducerModel.getEmail());
        bandProducerData.setPhone(bandProducerModel.getPhone());
    }
}
