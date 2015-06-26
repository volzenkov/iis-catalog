package khpi.iip.mipk.kharkiv.edu.web;

import khpi.iip.mipk.kharkiv.edu.dao.exceptions.UpdateException;
import khpi.iip.mipk.kharkiv.edu.domain.Category;
import khpi.iip.mipk.kharkiv.edu.domain.Chapter;
import khpi.iip.mipk.kharkiv.edu.domain.Document;
import khpi.iip.mipk.kharkiv.edu.domain.User;
import khpi.iip.mipk.kharkiv.edu.domain.enums.CatalogItemType;
import khpi.iip.mipk.kharkiv.edu.service.CatalogItemService;
import khpi.iip.mipk.kharkiv.edu.service.UserService;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor vzenkov
 */

@Component
@ManagedBean(name = "userController")
@SessionScoped
public class UserController {

    private List<User> usersList = new ArrayList<User>();

    //    @ManagedProperty(value = "#{UserService}")
    @Autowired
    CatalogItemService catalogItemService;

    @Autowired
    ChapterController chapterController;

    @Autowired
    UserService userService;

    @Autowired
    UserBean userBean;

    private List<User> filteredUsers;

    private User[] selectedUsers;

    private User newUser = new User();

    @PostConstruct
    private void init() {
        refreshUsersList();
    }

    public void refreshUsersList() {
        usersList = userService.listUsers();
    }


    public void onEdit(RowEditEvent event) {
        FacesMessage msg;
        try {
            User updatedUser = (User) event.getObject();
            userService.updateUser(updatedUser);
            msg = new FacesMessage("Информация о пользователе обновлена", updatedUser.getLogin());
        } catch (UpdateException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Не удалось обновить информацию", null);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void initNewUserDialog() {
        newUser = new User();
    }

    public String addUser() {
        if (newUser != null) {
            if (checkUniqueLogin()) {
                userService.addUser(newUser);
                refreshUsersList();
                return "toUsers";
            } else {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User exist", null);
                FacesContext.getCurrentInstance().addMessage("newUserLogin", msg);
            }
        }
        return null;
    }

    public boolean checkUniqueLogin() {
        for (User user : usersList) {
            if (user.getLogin().equals(newUser.getLogin())) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "User exist", null);
                FacesContext.getCurrentInstance().addMessage("newUserLogin", msg);
                return false;
            }
        }
        return true;
    }

    public void removeUsers() {
        if (selectedUsers != null && selectedUsers.length > 0) {

            List<Long> ids = new ArrayList<Long>(selectedUsers.length);
            for (User selectedUser : selectedUsers) {
                if (selectedUser.getUserId().equals(userBean.getCurrentUser().getUserId())) {
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Нельзя удалить себя", null);
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    return;
                }
                ids.add(selectedUser.getUserId());
            }
            userService.removeUsersByIds(ids);
            refreshUsersList();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Пользователи были удалены", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Необходимо выбрать хотя бы одного пользователя для удаления", null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public User getNewUser() {
        return newUser;
    }

    public void setNewUser(User newUser) {
        this.newUser = newUser;
    }

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
    }

    public List<User> getFilteredUsers() {
        return filteredUsers;
    }

    public void setFilteredUsers(List<User> filteredUsers) {
        this.filteredUsers = filteredUsers;
    }

    public User[] getSelectedUsers() {
        return selectedUsers;
    }

    public void setSelectedUsers(User[] selectedUsers) {
        this.selectedUsers = selectedUsers;
    }

}

