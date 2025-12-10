package concerttours.converter;

import bands.data.ConcertSummaryData;
import concerttours.model.ConcertModel;
import de.hybris.platform.converters.Populator;
import de.hybris.platform.servicelayer.dto.converter.ConversionException;
import de.hybris.platform.servicelayer.dto.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class ConcertSummaryConverter implements Converter<ConcertModel, ConcertSummaryData> {
    private final Populator<ConcertModel, ConcertSummaryData> concertSummaryPopulator;

    @Autowired
    public ConcertSummaryConverter(final Populator<ConcertModel, ConcertSummaryData> concertSummaryPopulator) {
        this.concertSummaryPopulator = concertSummaryPopulator;
    }

    @Override
    public ConcertSummaryData convert(final ConcertModel source) throws ConversionException {
        final ConcertSummaryData target = new ConcertSummaryData();
        return convert(source, target);
    }

    @Override
    public ConcertSummaryData convert(final ConcertModel source, final ConcertSummaryData target) throws ConversionException {
        concertSummaryPopulator.populate(source, target);
        return target;
    }

    @Override
    public List<ConcertSummaryData> convertAll(final Collection<? extends ConcertModel> source) throws ConversionException {
        return source.stream().map(this::convert).toList();
    }
}
