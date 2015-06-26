package khpi.iip.mipk.kharkiv.edu.web;

import khpi.iip.mipk.kharkiv.edu.dao.exceptions.UpdateException;
import khpi.iip.mipk.kharkiv.edu.domain.*;
import khpi.iip.mipk.kharkiv.edu.domain.enums.CatalogItemType;
import khpi.iip.mipk.kharkiv.edu.domain.enums.UserType;
import khpi.iip.mipk.kharkiv.edu.service.CatalogItemService;
import khpi.iip.mipk.kharkiv.edu.service.UserService;
import org.primefaces.model.DualListModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @autor vzenkov
 */

@Component
@ManagedBean(name = "userBean")
@SessionScoped
public class UserBean {

    @Autowired
    UserService userService;

    @Autowired
    CatalogItemService catalogItemService;

    @Autowired
    ChapterController chapterController;

    @Autowired
    CategoryController categoryController;

    private User currentUser = null;

    private String login;
    private String password;

    private DualListModel<User> editorsDualListModel = new DualListModel<User>();

    private CatalogItem catalogItemToAddEditors;
    private Chapter newChapter = new Chapter();

    public void initLoginDialog() {

        this.login = null;
        this.password = null;

    }

    public String doLogin() {

        try {
            User userByLoginAndPassword = userService.getUserByLoginAndPassword(login, password);

            if (userByLoginAndPassword != null) {
                currentUser = userByLoginAndPassword;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Welcome " + login + "!", null));
                return "loginSuccess";
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login failed!", null));
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }

    public String doLogout() {

        try {
            currentUser = null;
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            request.getSession().invalidate();
            return "logout";
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }

    public boolean isUserCanEditCatalogItem(CatalogItem catalogItem) {
        if (currentUser != null && (currentUser.getUserType().equals(UserType.ADMIN) ||
                (catalogItem.getEditors() != null && catalogItem.getEditors().contains(currentUser)))) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isUserAdmin() {
        if (currentUser != null && (currentUser.getUserType().equals(UserType.ADMIN))) {
            return true;
        } else {
            return false;
        }
    }

    public void refreshEditorsList(CatalogItem catalogItem) {

        try {
            CatalogItem item = catalogItemService.updateCatalogItem(catalogItem);

            List<User> users = userService.listNonAdminUsers();
            List<User> editors = item.getEditors();

            if (editors != null) {
                users.removeAll(editors);
            } else {
                editors = Collections.emptyList();
            }
            editorsDualListModel = new DualListModel<User>(users, editors);
            catalogItemToAddEditors = item;
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
        }
    }

    private List<Long> convertEditorsIds(List editors) {
        List<Long> editorsIds = new ArrayList<Long>();
        for (Object id : editors) {
            editorsIds.add(Long.valueOf((String) id));
        }
        return editorsIds;
    }

    public String updateEditorsList() {
        try {
            List<Long> ids = convertEditorsIds(editorsDualListModel.getTarget());
            catalogItemService.updateCatalogItemEditors(catalogItemToAddEditors, ids);
            switch (catalogItemToAddEditors.getCatalogItemType()) {
                case CHAPTER: {chapterController.refreshChaptersList(); break;}
                case CATEGORY: {categoryController.refreshCategoriesList(); break;}
            }
            editorsDualListModel = new DualListModel<User>();
            return "loginSuccess";
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), null));
            return null;
        }
    }


    public void initNewChapterDlg() {
        System.out.println(">>>>>> initNewChapter()");
        newChapter = new Chapter();
        newChapter.setCatalogItemType(CatalogItemType.CHAPTER);
        newChapter.setParent(null);
    }

    public String addChapter() {
        try {
        System.out.println(">>>>>> addCategory()");
        catalogItemService.addCatalogItem(newChapter);
        chapterController.refreshChaptersList();

//            FacesContext.getCurrentInstance().getExternalContext().redirect("/index.xhtml");
            return "toChapters";
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DualListModel<User> getEditorsDualListModel() {
        return editorsDualListModel;
    }

    public void setEditorsDualListModel(DualListModel<User> editorsDualListModel) {
        this.editorsDualListModel = editorsDualListModel;
    }

    public Chapter getNewChapter() {
        return newChapter;
    }

    public void setNewChapter(Chapter newChapter) {
        this.newChapter = newChapter;
    }
}
