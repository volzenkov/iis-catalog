package khpi.iip.mipk.kharkiv.edu.web.filter;

import khpi.iip.mipk.kharkiv.edu.domain.enums.UserType;
import khpi.iip.mipk.kharkiv.edu.web.CategoryController;
import khpi.iip.mipk.kharkiv.edu.web.DocumentController;
import khpi.iip.mipk.kharkiv.edu.web.UserBean;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @autor vzenkov
 */
public class AdminFilter implements Filter {

    private WebApplicationContext springContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
         springContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
    }

    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        //get the request page
        String requestPath = ((HttpServletRequest) request).getRequestURI();
        if (requestPath.contains("user.xhtml")) {
            //getting the session object
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            //check if there is a user logged in your session
            //I'm assuming you save the user object in the session (not the managed bean).
            UserBean userBean = (UserBean) springContext.getBean("userBean");
            if (userBean != null) {
                //check if the user has rights to access the current page
                //you can omit this part if you only need to check if there is a valid user logged in
                if (userBean.getCurrentUser() == null || !userBean.getCurrentUser().getUserType().equals(UserType.ADMIN)) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.sendRedirect(
                            httpServletRequest.getContextPath() + "/index.xhtml");
                    return;
                }
            }
        }
        if (requestPath.contains("category.xhtml")) {
            //getting the session object
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            //check if there is a user logged in your session
            //I'm assuming you save the user object in the session (not the managed bean).
            CategoryController categoryController = (CategoryController) springContext.getBean("categoryController");
            if (categoryController != null) {
                //check if the user has rights to access the current page
                //you can omit this part if you only need to check if there is a valid user logged in
                if (categoryController.getParentChapter() == null) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.sendRedirect(
                            httpServletRequest.getContextPath() + "/index.xhtml");
                    return;
                }
            }
        }
        if (requestPath.contains("document.xhtml")) {
            //getting the session object
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            //check if there is a user logged in your session
            //I'm assuming you save the user object in the session (not the managed bean).
            DocumentController documentController = (DocumentController) springContext.getBean("documentController");
            if (documentController != null) {
                //check if the user has rights to access the current page
                //you can omit this part if you only need to check if there is a valid user logged in
                if (documentController.getParentChapter() == null || documentController.getParentCategory() == null) {
                    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                    httpServletResponse.sendRedirect(
                            httpServletRequest.getContextPath() + "/index.xhtml");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
