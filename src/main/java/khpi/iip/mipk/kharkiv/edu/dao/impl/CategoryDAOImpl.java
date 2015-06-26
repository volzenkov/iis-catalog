package khpi.iip.mipk.kharkiv.edu.dao.impl;

import khpi.iip.mipk.kharkiv.edu.dao.CategoryDAO;
import khpi.iip.mipk.kharkiv.edu.dao.ChapterDAO;
import khpi.iip.mipk.kharkiv.edu.domain.Category;
import khpi.iip.mipk.kharkiv.edu.domain.Chapter;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAOImpl extends GenericDAOImpl<Category> implements CategoryDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Category> listCategories(Long chapterId, boolean isGuest) {
        Query query;
        if (isGuest) {
            query = sessionFactory.getCurrentSession().getNamedQuery("getCatalogItemsByParentIdForGuest");
        } else {
            query = sessionFactory.getCurrentSession().getNamedQuery("getCatalogItemsByParentId");
        }
        query.setLong("parentId", chapterId);
        List<Category> results = query.list();

        return results;
    }
}
