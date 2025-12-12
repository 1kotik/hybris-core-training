package bands.populator;

import bands.data.MusicianData;
import bands.enums.MusicInstrument;
import bands.model.MusicianModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class MusicianPopulator implements Populator<MusicianModel, MusicianData> {
    @Override
    public void populate(final MusicianModel source, final MusicianData target) throws ConversionException {
        if (source == null || target == null) {
            return;
        }
        final Map<MusicInstrument, Long> instrumentsExperience = source.getInstrumentsExperience();
        Map<String, Long> formattedInstrumentsExperience = Collections.emptyMap();
        if (instrumentsExperience != null) {
            formattedInstrumentsExperience = instrumentsExperience.entrySet().stream()
                    .map(entry -> {
                        String stringMusicInstrument = entry.getKey().getCode();
                        Long yearsOfExperience = entry.getValue();
                        return Map.entry(stringMusicInstrument, yearsOfExperience);
                    })
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        }
        target.setId(source.getCode());
        target.setName(source.getName());
        target.setInstrumentsExperience(formattedInstrumentsExperience);
        target.setSkillLevel(source.getSkillLevel().getCode());
    }
}
