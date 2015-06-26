package khpi.iip.mipk.kharkiv.edu.dao;

import khpi.iip.mipk.kharkiv.edu.domain.Category;
import khpi.iip.mipk.kharkiv.edu.domain.Document;

import java.util.List;

public interface DocumentDAO extends GenericDAO<Document>{

    public List<Document> listDocuments(Long categoryId, boolean isGuest);

}