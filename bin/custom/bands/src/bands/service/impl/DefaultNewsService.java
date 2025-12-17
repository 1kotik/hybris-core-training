package bands.service.impl;

import bands.dao.GenericItemModelDAO;
import bands.dao.NewsDAO;
import bands.model.NewsModel;
import bands.service.NewsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DefaultNewsService extends DefaultGenericItemModelService<NewsModel> implements NewsService {
    private final NewsDAO newsDAO;
    @Value("${dates.format}")
    private String dateFormat;

    public DefaultNewsService(final GenericItemModelDAO<NewsModel> genericItemModelDAO,
                              final NewsDAO newsDAO) {
        super(genericItemModelDAO, "News");
        this.newsDAO = newsDAO;
    }

    @Override
    public List<NewsModel> findByTheDay(final Date date) {
        List<NewsModel> news = newsDAO.findByTheDay(date);
        if (news.isEmpty()) {
            final String day = new SimpleDateFormat(dateFormat).format(date);
            throw new NoSuchElementException(String.format("No news for requested day: %s", day));
        }
        return news;
    }

    @Override
    public NewsModel findLatest() {
        return newsDAO.findLatest().orElseThrow(() -> new NoSuchElementException("News table is empty"));
    }
}
