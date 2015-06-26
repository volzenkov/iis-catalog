package khpi.iip.mipk.kharkiv.edu.dao.impl;

import khpi.iip.mipk.kharkiv.edu.dao.UserDAO;
import khpi.iip.mipk.kharkiv.edu.domain.CatalogItem;
import khpi.iip.mipk.kharkiv.edu.domain.User;
import khpi.iip.mipk.kharkiv.edu.domain.enums.UserType;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public void addUser(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    public List<User> listUsers() {

        return sessionFactory.getCurrentSession().createQuery("from User").list();
    }

    public List<User> listNonAdminUsers() {
        Query query = sessionFactory.getCurrentSession().getNamedQuery("getNonAdminUsers");
        query.setParameter("userType", UserType.ADMIN);
        List<User> result = query.list();
        if(result == null) {
            return Collections.emptyList();
        } else {
            return result;
        }
    }

    @Override
    public List<User> listUsersByIds(List<Long> ids) {
        if (!ids.isEmpty()) {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("getUserByIds");
            query.setParameterList("ids", ids);
            List<User> result = query.list();
            return result;
        } else {
            return null;
        }
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password) {

        Query query = sessionFactory.getCurrentSession().getNamedQuery("getUserByLoginAndPassword");
        query.setString("login", login);
        query.setString("password", password);

        User user = null;
        try {
            user = (User) query.uniqueResult();
        } catch (HibernateException e) {
            e.printStackTrace();
        }

        return user;
    }

    @Override
    public void removeUsersByIds(List<Long> ids) {
        if (!ids.isEmpty()) {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("removeUserByIds");
            query.setParameterList("ids", ids);
            query.executeUpdate();
        } else {
            return;
        }
    }

    public void removeUser(Long id) {
        User user = (User) sessionFactory.getCurrentSession().load(User.class, id);
        if (null != user) {

            sessionFactory.getCurrentSession().delete(user);
        }

    }
}
