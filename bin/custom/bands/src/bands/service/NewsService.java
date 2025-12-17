package bands.service;

import bands.model.NewsModel;

import java.util.Date;
import java.util.List;

public interface NewsService extends GenericItemModelService<NewsModel> {
    List<NewsModel> findByTheDay(Date date);
    NewsModel findLatest();
}
