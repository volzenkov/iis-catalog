package khpi.iip.mipk.kharkiv.edu.web;

import khpi.iip.mipk.kharkiv.edu.domain.enums.UserType;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 * @autor vzenkov
 */
@ManagedBean
@ApplicationScoped
public class UserTypeBean {

    public UserType[] getUserTypes() {
        return UserType.values();
    }

}
