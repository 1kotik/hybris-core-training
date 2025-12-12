package bands.converter;

import bands.data.BandManagerData;
import bands.model.BandManagerModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BandManagerConverter implements Converter<BandManagerModel, BandManagerData> {
    private final Populator<BandManagerModel, BandManagerData> bandManagerPopulator;

    @Autowired
    public BandManagerConverter(final Populator<BandManagerModel, BandManagerData> bandManagerPopulator) {
        this.bandManagerPopulator = bandManagerPopulator;
    }

    @Override
    public BandManagerData convert(final BandManagerModel source) throws ConversionException {
        final BandManagerData target = new BandManagerData();
        return convert(source, target);
    }

    @Override
    public BandManagerData convert(final BandManagerModel source, final BandManagerData target) throws ConversionException {
        bandManagerPopulator.populate(source, target);
        return target;
    }
}
