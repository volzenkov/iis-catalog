package khpi.iip.mipk.kharkiv.edu.service.impl;

import khpi.iip.mipk.kharkiv.edu.dao.CatalogItemDAO;
import khpi.iip.mipk.kharkiv.edu.dao.UserDAO;
import khpi.iip.mipk.kharkiv.edu.dao.exceptions.UpdateException;
import khpi.iip.mipk.kharkiv.edu.domain.CatalogItem;
import khpi.iip.mipk.kharkiv.edu.domain.User;
import khpi.iip.mipk.kharkiv.edu.service.UserService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
 
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CatalogItemDAO catalogItemDAO;

    @Transactional
    public void addUser(User user) {
        userDAO.addUser(user);
    }
 
    @Transactional
    public List<User> listUsers() {

        return userDAO.listUsers();
    }

    @Transactional
    public List<User> listNonAdminUsers() {
        return userDAO.listNonAdminUsers();
    }

    @Transactional(readOnly = true)
    public User getUserByLoginAndPassword(String login, String password) {
        return userDAO.getUserByLoginAndPassword(login, password);
    }

    @Transactional
    public List<User> getUsersByIds(List<Long> ids) {
        return userDAO.listUsersByIds(ids);
    }

    @Transactional
    public User updateUser(User user) throws UpdateException {
        return userDAO.update(user);
    }

    @Transactional
    public void removeUsersByIds(List<Long> ids) {

        for (Long id : ids) {
            List<CatalogItem> catalogItems = catalogItemDAO.listCatalogItemsByEditorId(id);
            for (CatalogItem catalogItem : catalogItems) {
                Iterator<User> iterator = catalogItem.getEditors().iterator();
                while (iterator.hasNext()) {
                    User next = iterator.next();
                    if(next.getUserId().equals(id)) {
                        iterator.remove();
                    }
                }
            }

            userDAO.removeUser(id);
        }

//        userDAO.removeUsersByIds(ids);
    }

    @Transactional
    public void removeUser(Long id) {
        userDAO.removeUser(id);
    }
}
