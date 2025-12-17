package bands.dao;

import bands.model.NewsModel;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface NewsDAO extends GenericItemModelDAO<NewsModel> {
    List<NewsModel> findByTheDay(Date date);
    Optional<NewsModel> findLatest();
}
