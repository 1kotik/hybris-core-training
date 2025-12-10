package bands.populator;

import bands.data.BandData;
import bands.enums.MusicType;
import bands.model.BandModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class BandPopulator implements Populator<BandModel, BandData> {
    @Override
    public void populate(final BandModel source, final BandData target) throws ConversionException {
        if (source == null || target == null) {
            return;
        }
        target.setId(source.getCode());
        target.setName(source.getName());
        target.setAlbumsSold(source.getAlbumSales());
        target.setDescription(source.getHistory());
        final Collection<MusicType> musicTypes = source.getTypes();
        if(musicTypes != null) {
            target.setGenres(musicTypes.stream().map(MusicType::getCode).toList());
        }
    }
}
