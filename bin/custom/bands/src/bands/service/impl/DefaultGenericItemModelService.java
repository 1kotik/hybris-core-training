package bands.service.impl;

import bands.dao.GenericItemModelDAO;
import bands.service.GenericItemModelService;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.servicelayer.exceptions.AmbiguousIdentifierException;
import de.hybris.platform.servicelayer.exceptions.UnknownIdentifierException;

import java.util.List;

public class DefaultGenericItemModelService<T extends ItemModel> implements GenericItemModelService<T> {
    protected final GenericItemModelDAO<T> genericItemModelDAO;
    protected final String entityName;

    public DefaultGenericItemModelService(GenericItemModelDAO<T> genericItemModelDAO, String entityName) {
        this.genericItemModelDAO = genericItemModelDAO;
        this.entityName = entityName;
    }

    @Override
    public T findByCode(String code) {
        List<T> result = genericItemModelDAO.findByCode(code);
        if (result.isEmpty()) {
            throw new UnknownIdentifierException(entityName + " with code " + code + " not found");
        } else if (result.size() > 1) {
            throw new AmbiguousIdentifierException(entityName + " code " + code + " is not unique");
        }
        return result.get(0);
    }

    @Override
    public List<T> findAll() {
        return genericItemModelDAO.findAll();
    }
}
