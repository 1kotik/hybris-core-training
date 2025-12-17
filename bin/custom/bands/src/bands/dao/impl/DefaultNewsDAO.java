package bands.dao.impl;

import bands.dao.NewsDAO;
import bands.model.NewsModel;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
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
        final String dateColumn = NewsModel.DATE;
        final String startOfDayColumn = "startOfDay";
        final String startOfNextDayColumn = "startOfNextDay";
        final Date startOfDay = getStartOfDay(date);
        final Date startOfNextDay = getStartOfDay(DateUtils.addDays(date, 1));
        final FlexibleSearchQuery query = new FlexibleSearchQuery(
                String.format("""
                                SELECT {%s} FROM {%s}
                                WHERE {%s} >= ?%s AND {%s} < ?%s""",
                        ItemModel.PK, typecode, dateColumn, startOfDayColumn, dateColumn, startOfNextDayColumn));
        query.addQueryParameter(dateColumn, date);
        query.addQueryParameter(startOfDayColumn, startOfDay);
        query.addQueryParameter(startOfNextDayColumn, startOfNextDay);
        return flexibleSearchService.<NewsModel>search(query).getResult();
    }

    private Date getStartOfDay(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    @Override
    public Optional<NewsModel> findLatest() {
        final FlexibleSearchQuery query = new FlexibleSearchQuery(
                String.format("SELECT {%s} FROM {%s} ORDER BY {%s} DESC",
                        ItemModel.PK, typecode, NewsModel.DATE));
        query.setCount(1);
        return flexibleSearchService.<NewsModel>search(query).getResult().stream().findAny();
    }
}
