package khpi.iip.mipk.kharkiv.edu.dao;

import khpi.iip.mipk.kharkiv.edu.domain.CatalogItem;

import java.util.List;

public interface CatalogItemDAO extends GenericDAO<CatalogItem>{

	public void addCatalogItem(CatalogItem catalogItem);

    public void updateCatalogItem(CatalogItem catalogItem);

	public List<CatalogItem> listCatalogItems();

    public List<CatalogItem> listCatalogItemsByParentId(Long parentId);

    public List<CatalogItem> listCatalogItemsByEditorId(Long editorId);

    public void removeCatalogItem(Long id);
}