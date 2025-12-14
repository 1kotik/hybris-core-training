package bands.dao.impl;

import bands.dao.NewsDAO;
import bands.model.NewsModel;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class DefaultNewsDAO extends DefaultGenericItemModelDAO<NewsModel> implements NewsDAO {
    @Value("${dates.format}")
    private String dateFormat;

    public DefaultNewsDAO(FlexibleSearchService flexibleSearchService) {
        super(NewsModel.CODE, NewsModel._TYPECODE, flexibleSearchService);
    }

    @Override
    public List<NewsModel> findByTheDay(Date date) {
        if (date == null) {
            return Collections.emptyList();
        }
        final String day = new SimpleDateFormat(dateFormat).format(date);
        final String dateColumn = NewsModel.DATE;
        final FlexibleSearchQuery query = new FlexibleSearchQuery(
                String.format("SELECT {%s} FROM {%s} WHERE TRUNC({%s}) = ?%s",
                        ItemModel.PK, typecode, dateColumn, dateColumn));
        query.addQueryParameter(dateColumn, day);
        return flexibleSearchService.<NewsModel>search(query).getResult();
    }

    @Override
    public Optional<NewsModel> findLatest() {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(
                String.format("SELECT {%s} FROM {%s} ORDER BY {%s} DESC LIMIT 1",
                        ItemModel.PK, typecode, NewsModel.DATE));
        return flexibleSearchService.<NewsModel>search(query).getResult().stream().findAny();
    }
}
