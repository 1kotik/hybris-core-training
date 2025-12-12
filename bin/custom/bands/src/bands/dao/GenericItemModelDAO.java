package bands.dao;

import de.hybris.platform.core.model.ItemModel;

import java.util.List;

public interface GenericItemModelDAO<T extends ItemModel> {
    List<T> findByCode(String code);
    List<T> findAll();
}
