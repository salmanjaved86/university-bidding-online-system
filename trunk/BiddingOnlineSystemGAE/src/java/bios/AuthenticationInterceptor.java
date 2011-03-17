package bios;

import java.util.Map;

import JDOModel.*;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import JDODataManager.*;

/*
 * This interceptor provides authentication for the secure actions of the application.
 * It does two things.  First, it checks the session scope map to see if there's user 
 * object present, which inidcates that the current user is already logged in.  If this
 * object is not present, the interceptor alters the workflow of the request by returning 
 * a login control string that causes the request to forward to the login page.
 */
public class AuthenticationInterceptor implements Interceptor {

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {

        /* Get the session map from the invocation context ( the ActionContext actually )
         * and check for the user object.  Note, we are not going directly to the Servlet
         * API's session object, even though this is most likely the map being returned
         * by this code; we need to keep our Struts 2 components cleanly separated from the
         * Servlet API so that our testing can be as simple as faking a map, rather than
         * faking Servlet API objects.
         */
        Map session = actionInvocation.getInvocationContext().getSession();

        purgeStaleTokens(session);

        //Student student = (Student) session.get("student");
        Student student = StudentDataManager.getStudent("fred.ng.2009");
        //Admin admin = (Admin) session.get("admin");
        /*
         * If use doesn't exist return the LOGIN control string.  This will cause the
         * execution of this action to stop and the request will return the globally defined
         * login result.
         */
        //if (student != null || admin != null) {
            return actionInvocation.invoke();
        //}

        //return Action.LOGIN;

    }

    private void purgeStaleTokens(Map session) {

        Object userToken = session.get("student");
        if (!(userToken instanceof Student)) {
            session.remove("student");
        }

    }
}
