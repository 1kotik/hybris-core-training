package bands.converter;

import bands.data.MusicianData;
import bands.model.MusicianModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Component
public class MusicianConverter implements Converter<MusicianModel, MusicianData> {
    private final Populator<MusicianModel, MusicianData> musicianPopulator;

    @Autowired
    public MusicianConverter(final Populator<MusicianModel, MusicianData> musicianPopulator) {
        this.musicianPopulator = musicianPopulator;
    }

    @Override
    public MusicianData convert(final MusicianModel source) throws ConversionException {
        final MusicianData target = new MusicianData();
        return convert(source, target);
    }

    @Override
    public MusicianData convert(final MusicianModel source, final MusicianData target) throws ConversionException {
        musicianPopulator.populate(source, target);
        return target;
    }

    @Override
    public List<MusicianData> convertAll(final Collection<? extends MusicianModel> source) throws ConversionException {
        return source == null ? Collections.emptyList()
                : source.stream().map(this::convert).toList();
    }
}
