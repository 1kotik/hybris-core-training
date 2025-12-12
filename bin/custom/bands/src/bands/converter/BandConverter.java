package bands.converter;

import bands.data.BandData;
import bands.model.BandModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BandConverter implements Converter<BandModel, BandData> {
    private final Populator<BandModel, BandData> bandPopulator;


    @Autowired
    public BandConverter(final Populator<BandModel, BandData> bandPopulator) {
        this.bandPopulator = bandPopulator;
    }

    @Override
    public BandData convert(final BandModel source) throws ConversionException {
        final BandData target = new BandData();
        return convert(source, target);
    }

    @Override
    public BandData convert(final BandModel source, final BandData target) throws ConversionException {
        bandPopulator.populate(source, target);
        return target;
    }
}
