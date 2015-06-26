package khpi.iip.mipk.kharkiv.edu.service.impl;

import khpi.iip.mipk.kharkiv.edu.dao.*;
import khpi.iip.mipk.kharkiv.edu.dao.exceptions.UpdateException;
import khpi.iip.mipk.kharkiv.edu.domain.*;
import khpi.iip.mipk.kharkiv.edu.service.CatalogItemService;
import khpi.iip.mipk.kharkiv.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class CatalogItemServiceImpl implements CatalogItemService {

    @Autowired
    private CatalogItemDAO catalogItemDAO;

    @Autowired
    private ChapterDAO chapterDAO;

    @Autowired
    private CategoryDAO categoryDAO;

    @Autowired
    private DocumentDAO documentDAO;

    @Autowired
    private UserService userService;

    @Transactional
    public void addCatalogItem(CatalogItem catalogItem) {
        catalogItemDAO.addCatalogItem(catalogItem);
    }

    @Transactional
    public CatalogItem updateCatalogItem(CatalogItem catalogItem) throws UpdateException {
        return catalogItemDAO.update(catalogItem);
    }

    @Transactional
    public List<Chapter> listRootChapters(boolean isGuest) {
        List<Chapter> catalogItems = chapterDAO.listRootChapters(isGuest);
        return catalogItems;
    }

    @Transactional
    public List<Category> listCategories(Long chapterId, boolean isGuest) {
        List<Category> categoriesList = categoryDAO.listCategories(chapterId, isGuest);
        return categoriesList;
    }

    @Transactional
    public List<Document> listDocuments(Long categoryId, boolean isGuest) {
        List<Document> categoriesList = documentDAO.listDocuments(categoryId, isGuest);
        return categoriesList;
    }

    @Transactional
    public void removeCatalogItem(Long id) {
        catalogItemDAO.removeCatalogItem(id);
    }

    @Transactional
    public void updateCatalogItemEditors(CatalogItem catalogItem, List<Long> editorsIds) throws UpdateException {

        List<User> usersByIds = userService.getUsersByIds(editorsIds);
        if (usersByIds != null) {
            catalogItem.setEditors(usersByIds);
        } else {
            catalogItem.setEditors(Collections.<User>emptyList());
        }
        catalogItemDAO.update(catalogItem);

    }

}
