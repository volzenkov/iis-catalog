package khpi.iip.mipk.kharkiv.edu.web;

import khpi.iip.mipk.kharkiv.edu.dao.exceptions.UpdateException;
import khpi.iip.mipk.kharkiv.edu.domain.Category;
import khpi.iip.mipk.kharkiv.edu.domain.Chapter;
import khpi.iip.mipk.kharkiv.edu.domain.Document;
import khpi.iip.mipk.kharkiv.edu.domain.enums.CatalogItemType;
import khpi.iip.mipk.kharkiv.edu.service.CatalogItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor vzenkov
 */

@Component
@ManagedBean(name = "categoryController")
@SessionScoped
public class CategoryController {

    private Chapter parentChapter;
    private List<Category> categoriesList = new ArrayList<Category>();

    //    @ManagedProperty(value = "#{UserService}")
    @Autowired
    CatalogItemService catalogItemService;

    @Autowired
    DocumentController documentController;

    @Autowired
    UserBean userBean;

    private Category categoryForEdit = new Category();
    private Category newCategory = new Category();

    public void refreshCategoriesList() {
        categoriesList = catalogItemService.listCategories(parentChapter.getItemId(), userBean.getCurrentUser() == null);
    }

    public void initNewCategoryDialog() {
        newCategory = new Category();
        newCategory.setCatalogItemType(CatalogItemType.CATEGORY);
        newCategory.setParent(parentChapter);
    }

    public void initEditCategoryDialog(Category category) {
        categoryForEdit = category;
    }

    public String addCategory() {
        catalogItemService.addCatalogItem(newCategory);
        refreshCategoriesList();
        return "toCategories";
    }

    public String updateCategory() {
        try {
            catalogItemService.updateCatalogItem(categoryForEdit);
            refreshCategoriesList();
            categoryForEdit = new Category();
            return "toCategories";
        } catch (UpdateException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String selectCategory(Category category) {

        documentController.setParentChapter(parentChapter);
        documentController.setParentCategory(category);
        documentController.refreshDocumentsList();

        return "toDocuments";
    }

    public String removeCategory(Category category) {
        catalogItemService.removeCatalogItem(category.getItemId());
        refreshCategoriesList();
        return "toCategories";
    }

    public Chapter getParentChapter() {
        return parentChapter;
    }

    public void setParentChapter(Chapter parentChapter) {
        this.parentChapter = parentChapter;
    }

    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public Category getCategoryForEdit() {
        return categoryForEdit;
    }

    public void setCategoryForEdit(Category categoryForEdit) {
        this.categoryForEdit = categoryForEdit;
    }

    public Category getNewCategory() {
        return newCategory;
    }

    public void setNewCategory(Category newCategory) {
        this.newCategory = newCategory;
    }

}
