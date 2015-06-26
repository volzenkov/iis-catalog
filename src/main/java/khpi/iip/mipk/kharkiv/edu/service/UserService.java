package khpi.iip.mipk.kharkiv.edu.service;

import khpi.iip.mipk.kharkiv.edu.dao.exceptions.UpdateException;
import khpi.iip.mipk.kharkiv.edu.domain.CatalogItem;
import khpi.iip.mipk.kharkiv.edu.domain.User;

import java.util.List;

public interface UserService {

	public void addUser(User user);

	public List<User> listUsers();

    public List<User> listNonAdminUsers();

    public User getUserByLoginAndPassword(String login, String password);

    public List<User> getUsersByIds(List<Long> ids);

	public User updateUser(User user) throws UpdateException;

    public void removeUsersByIds(List<Long> ids);
}
