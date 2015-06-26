package khpi.iip.mipk.kharkiv.edu.dao.impl;

import khpi.iip.mipk.kharkiv.edu.dao.ChapterDAO;
import khpi.iip.mipk.kharkiv.edu.domain.Chapter;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChapterDAOImpl extends GenericDAOImpl<Chapter> implements ChapterDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Chapter> listRootChapters(boolean isGuest) {
        Query query;
        if (isGuest) {
            query = sessionFactory.getCurrentSession().getNamedQuery("getRootCatalogItemsForGuest");
        } else {
            query = sessionFactory.getCurrentSession().getNamedQuery("getRootCatalogItems");
        }
        List<Chapter> results = query.list();

        return results;
    }
}
