package khpi.iip.mipk.kharkiv.edu.dao;

import khpi.iip.mipk.kharkiv.edu.domain.User;

import java.util.List;

public interface UserDAO extends GenericDAO<User>{

	public void addUser(User user);

	public List<User> listUsers();

    public List<User> listNonAdminUsers();

    public List<User> listUsersByIds(List<Long> ids);

    public User getUserByLoginAndPassword(String login, String password);

	public void removeUsersByIds(List<Long> ids);

	public void removeUser(Long id);
}