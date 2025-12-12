package bands.attributehandler;

import bands.enums.MusicInstrument;
import bands.enums.SkillLevel;
import bands.model.MusicianModel;
import de.hybris.platform.servicelayer.model.attribute.AbstractDynamicAttributeHandler;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("musicInstrumentsSkillLevelAttributeHandler")
public class MusicInstrumentsSkillLevelAttributeHandler extends AbstractDynamicAttributeHandler<SkillLevel, MusicianModel> {
    @Override
    public SkillLevel get(MusicianModel musician) {
        Map<MusicInstrument, Long> musicInstrumentsExperience = musician.getInstrumentsExperience();
        if (musicInstrumentsExperience == null) {
            return SkillLevel.BEGINNER;
        }
        long totalExperience = musicInstrumentsExperience.values().stream().reduce(0L, Long::sum);
        if (totalExperience < 2) {
            return SkillLevel.BEGINNER;
        } else if (totalExperience < 7) {
            return SkillLevel.AMATEUR;
        } else {
            return SkillLevel.PROFESSIONAL;
        }
    }
}
