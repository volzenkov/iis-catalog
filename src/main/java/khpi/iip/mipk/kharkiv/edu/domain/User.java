package khpi.iip.mipk.kharkiv.edu.domain;

import khpi.iip.mipk.kharkiv.edu.domain.enums.UserType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "getNonAdminUsers", query = "from User user where user.userType != :userType"),
        @NamedQuery(name = "getUserByLoginAndPassword", query = "from User user where user.login = :login and user.password = :password"),
        @NamedQuery(name = "getUserByIds", query = "from User user where user.userId in (:ids)"),
        @NamedQuery(name = "removeUserByIds", query = "delete from User user where user.userId in (:ids)")
})

@Entity
@Table(name = "user")
public class User extends PersistentEntity {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_login", nullable = false, unique = true)
    private String login;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_email")
    private String email;

    @Column(name = "user_type")
    private UserType userType;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!login.equals(user.login)) return false;
        if (!userId.equals(user.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + login.hashCode();
        return result;
    }

}
