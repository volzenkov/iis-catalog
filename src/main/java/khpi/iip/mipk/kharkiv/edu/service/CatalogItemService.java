package khpi.iip.mipk.kharkiv.edu.service;

import khpi.iip.mipk.kharkiv.edu.dao.exceptions.UpdateException;
import khpi.iip.mipk.kharkiv.edu.domain.*;

import java.util.List;

public interface CatalogItemService {

	public void addCatalogItem(CatalogItem catalogItem);

    public CatalogItem updateCatalogItem(CatalogItem catalogItem) throws UpdateException;

    public List<Chapter> listRootChapters(boolean isGuest);

    public List<Category> listCategories(Long chapterId, boolean isGuest);

    public List<Document> listDocuments(Long categoryId, boolean isGuest);

	public void removeCatalogItem(Long id);

    public void updateCatalogItemEditors(CatalogItem catalogItem, List<Long> editors) throws UpdateException;

}
