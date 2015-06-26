package khpi.iip.mipk.kharkiv.edu.web;

import khpi.iip.mipk.kharkiv.edu.dao.exceptions.UpdateException;
import khpi.iip.mipk.kharkiv.edu.domain.CatalogItem;
import khpi.iip.mipk.kharkiv.edu.domain.Category;
import khpi.iip.mipk.kharkiv.edu.domain.Chapter;
import khpi.iip.mipk.kharkiv.edu.domain.User;
import khpi.iip.mipk.kharkiv.edu.domain.enums.CatalogItemType;
import khpi.iip.mipk.kharkiv.edu.service.CatalogItemService;
import khpi.iip.mipk.kharkiv.edu.service.UserService;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @autor vzenkov
 */

@Component
@ManagedBean(name = "chapterController")
@SessionScoped
public class ChapterController {

    private List<Chapter> chaptersList = new ArrayList<Chapter>();
    private Chapter chapterForEdit = new Chapter();

    //    @ManagedProperty(value = "#{UserService}")
    @Autowired
    CatalogItemService catalogItemService;

    @Autowired
    CategoryController categoryController;

    @Autowired
    UserService userService;

    @Autowired
    UserBean userBean;

    @PostConstruct
    public void init(){
        refreshChaptersList();
    }

    public void refreshChaptersList() {
        chaptersList = catalogItemService.listRootChapters(userBean.getCurrentUser() == null);
    }

    public void initEditChapterDialog(Chapter chapter) {
        chapterForEdit = chapter;
    }

    public String updateChapter() {
        try {
            catalogItemService.updateCatalogItem(chapterForEdit);
            refreshChaptersList();
            chapterForEdit = new Chapter();
            return "toChapters";

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public String selectChapter(Chapter chapter) {

        categoryController.setParentChapter(chapter);
        categoryController.refreshCategoriesList();

        return "toCategories";
    }

    public String removeChapter(Chapter chapter) {
        try {
        catalogItemService.removeCatalogItem(chapter.getItemId());
        refreshChaptersList();
            return "toCategories";
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public List<Chapter> getChaptersList() {
        return chaptersList;
    }

    public void setChaptersList(List<Chapter> chaptersList) {
        this.chaptersList = chaptersList;
    }

    public Chapter getChapterForEdit() {
        return chapterForEdit;
    }

}
