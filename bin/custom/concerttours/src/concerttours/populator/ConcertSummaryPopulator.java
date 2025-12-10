package concerttours.populator;

import bands.data.ConcertSummaryData;
import concerttours.enums.ConcertType;
import concerttours.model.ConcertModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import org.springframework.stereotype.Component;

@Component
public class ConcertSummaryPopulator implements Populator<ConcertModel, ConcertSummaryData> {
    @Override
    public void populate(final ConcertModel source, final ConcertSummaryData target) throws ConversionException {
        if (source == null || target == null) {
            return;
        }
        final String concertType = source.getConcertType() == ConcertType.INDOOR ? "Indoors" : "Outdoors";
        target.setId(source.getCode());
        target.setDate(source.getDate());
        target.setVenue(source.getVenue());
        target.setType(concertType);
    }
}
