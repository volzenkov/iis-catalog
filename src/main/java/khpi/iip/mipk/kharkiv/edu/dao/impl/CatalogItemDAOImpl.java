package khpi.iip.mipk.kharkiv.edu.dao.impl;

import khpi.iip.mipk.kharkiv.edu.dao.CatalogItemDAO;
import khpi.iip.mipk.kharkiv.edu.domain.CatalogItem;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CatalogItemDAOImpl extends GenericDAOImpl<CatalogItem> implements CatalogItemDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void addCatalogItem(CatalogItem catalogItem) {
        sessionFactory.getCurrentSession().save(catalogItem);
    }

    @Override
    public void updateCatalogItem(CatalogItem catalogItem) {
        sessionFactory.getCurrentSession().merge(catalogItem);
    }

    public List<CatalogItem> listCatalogItems() {

        return sessionFactory.getCurrentSession().createQuery("from CatalogItem").list();
    }

    @Override
    public List<CatalogItem> listCatalogItemsByParentId(Long parentId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("getCatalogItemsByParentId");
        query.setLong("parentId", parentId);
        List<CatalogItem> results = query.list();

        return results;
    }

    @Override
    public List<CatalogItem> listCatalogItemsByEditorId(Long editorId) {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("getCatalogItemsByEditorId");
        query.setLong("editorId", editorId);
        List<CatalogItem> results = query.list();

        return results;
    }

    public void removeCatalogItem(Long id) {
        CatalogItem catalogItem = (CatalogItem) sessionFactory.getCurrentSession().load(CatalogItem.class, id);
        if (null != catalogItem) {
            List<CatalogItem> catalogItems = listCatalogItemsByParentId(catalogItem.getItemId());
            for (CatalogItem item : catalogItems) {
                listCatalogItemsByParentId(item.getItemId());
                for (CatalogItem item1 : catalogItems) {
                    sessionFactory.getCurrentSession().delete(item1);
                }
                sessionFactory.getCurrentSession().delete(item);
            }
            sessionFactory.getCurrentSession().delete(catalogItem);
        }

    }
}
