package khpi.iip.mipk.kharkiv.edu.dao.impl;

import khpi.iip.mipk.kharkiv.edu.dao.CategoryDAO;
import khpi.iip.mipk.kharkiv.edu.dao.DocumentDAO;
import khpi.iip.mipk.kharkiv.edu.dao.exceptions.RemoveException;
import khpi.iip.mipk.kharkiv.edu.domain.Category;
import khpi.iip.mipk.kharkiv.edu.domain.Document;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.InputStream;
import java.util.List;

@Repository
public class DocumentDAOImpl extends GenericDAOImpl<Document> implements DocumentDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Document> listDocuments(Long categoryId, boolean isGuest) {
        Query query;
        if (isGuest) {
            query = sessionFactory.getCurrentSession().getNamedQuery("getCatalogItemsByParentIdForGuest");
        } else {
            query = sessionFactory.getCurrentSession().getNamedQuery("getCatalogItemsByParentId");
        }
        query.setLong("parentId", categoryId);
        List<Document> results = query.list();

        return results;
    }

    @Override
    public void remove(Document document) throws RemoveException {
        String link = document.getLink();
        super.remove(document);

        try {
            if (link != null) {
                String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/iis-catalog/documents/");
                File file = new File(path + File.separator + link);
                if (file.exists()) {
                    file.delete();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
