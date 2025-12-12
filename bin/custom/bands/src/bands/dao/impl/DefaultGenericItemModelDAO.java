package bands.dao.impl;

import bands.dao.GenericItemModelDAO;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;

import java.util.List;

public class DefaultGenericItemModelDAO<T extends ItemModel> implements GenericItemModelDAO<T> {
    protected final String codeColumnName;
    protected final String typecode;
    protected final FlexibleSearchService flexibleSearchService;

    public DefaultGenericItemModelDAO(String codeColumnName, String typecode, FlexibleSearchService flexibleSearchService) {
        this.codeColumnName = codeColumnName;
        this.typecode = typecode;
        this.flexibleSearchService = flexibleSearchService;
    }

    @Override
    public List<T> findByCode(String code) {
        FlexibleSearchQuery query = new FlexibleSearchQuery(
                String.format("SELECT {%s} FROM {%s} WHERE {%s} = ?%s",
                        T.PK, typecode, codeColumnName, codeColumnName));
        query.addQueryParameter(codeColumnName, code);
        return flexibleSearchService.<T>search(query).getResult();
    }

    @Override
    public List<T> findAll() {
        FlexibleSearchQuery query = new FlexibleSearchQuery(
                String.format("SELECT {%s} FROM {%s}",
                        T.PK, typecode));
        return flexibleSearchService.<T>search(query).getResult();
    }
}
