package bands.listener;

import bands.model.BandModel;
import bands.model.NewsModel;
import bands.service.NewsService;
import de.hybris.platform.servicelayer.event.events.AfterItemCreationEvent;
import de.hybris.platform.servicelayer.event.impl.AbstractEventListener;
import de.hybris.platform.servicelayer.model.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.NoSuchElementException;

@Component
public class NewBandEventListener extends AbstractEventListener<AfterItemCreationEvent> {
    private final ModelService modelService;
    private final NewsService newsService;

    @Autowired
    public NewBandEventListener(ModelService modelService, NewsService newsService) {
        this.modelService = modelService;
        this.newsService = newsService;
    }

    @Override
    protected void onEvent(final AfterItemCreationEvent event) {
        if (event == null || event.getSource() == null) {
            return;
        }
        final Object source = modelService.get(event.getSource());
        if (source instanceof BandModel band) {
            final NewsModel news = modelService.create(NewsModel.class);
            final String bandName = band.getName();
            news.setCode(generateUniqueCode());
            news.setHeadline(String.format("New band: %s", bandName));
            news.setContent(String.format("New band in town: %s", bandName));
            news.setDate(new Date());
            modelService.save(news);
        }
    }

    private String generateUniqueCode() {
        final String prefix = "N";
        try {
            final NewsModel newsModel = newsService.findLatest();
            final String code = newsModel.getCode();
            int intCodeValue = Integer.parseInt(code.substring(1));
            return prefix + ++intCodeValue;
        } catch (final NoSuchElementException e) {
            return prefix + "1";
        }
    }
}
