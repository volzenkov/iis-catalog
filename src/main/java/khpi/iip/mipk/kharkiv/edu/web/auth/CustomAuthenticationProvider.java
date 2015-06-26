package khpi.iip.mipk.kharkiv.edu.web.auth;

import khpi.iip.mipk.kharkiv.edu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

/**
 * @autor vzenkov
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    UserService userService;

    @Override
    public boolean supports(Class<? extends Object> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String principal = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();



        return new UsernamePasswordAuthenticationToken(principal, password, authentication.getAuthorities());
//        if (authentication.getName().equals(authentication.getCredentials()))
//            return new UsernamePasswordAuthentication(authentication.getName(), authentication.getCredentials(), null);
//        else
//            return null;
    }
}
