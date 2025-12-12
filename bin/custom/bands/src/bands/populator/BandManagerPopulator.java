package bands.populator;

import bands.data.BandManagerData;
import bands.model.BandManagerModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class BandManagerPopulator implements Populator<BandManagerModel, BandManagerData> {
    @Override
    public void populate(final BandManagerModel source, final BandManagerData target) throws ConversionException {
        if (source == null || target == null) {
            return;
        }
        target.setId(source.getCode());
        target.setName(source.getName());
        target.setEmail(source.getEmail());
        target.setPhone(source.getPhone());
        target.setYearsOfExperience(source.getYearsOfExperience());
    }
}
