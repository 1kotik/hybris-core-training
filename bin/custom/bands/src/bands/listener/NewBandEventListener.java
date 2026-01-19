package bands.listener;

import bands.model.BandModel;
import bands.model.NewsModel;
import de.hybris.platform.servicelayer.event.events.AfterItemCreationEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class NewBandEventListener extends AbstractEventListener<AfterItemCreationEvent> {
    private final ModelService modelService;

    @Autowired
    public NewBandEventListener(ModelService modelService) {
        this.modelService = modelService;
    }

    @Override
    protected void onEvent(final AfterItemCreationEvent event) {
        if (!isEventValid(event)) {
            return;
        }
        final Object source = modelService.get(event.getSource());
        if (source instanceof BandModel band) {
            final NewsModel news = modelService.create(NewsModel.class);
            final String bandName = band.getName();
            news.setHeadline(String.format("New band: %s", bandName));
            news.setContent(String.format("New band in town: %s", bandName));
            news.setDate(new Date());
            modelService.save(news);
        }
    }

    private boolean isEventValid(final AfterItemCreationEvent event) {
        return event != null
                && BandModel._TYPECODE.equals(event.getTypeCode())
                && event.getSource() != null;
    }
}
