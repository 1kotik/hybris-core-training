package bands.service;

import de.hybris.platform.core.model.ItemModel;

import java.util.List;

public interface GenericItemModelService<T extends ItemModel> {
    T findByCode(String code);
    List<T> findAll();
}
