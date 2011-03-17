package bios;
import java.util.*;
import org.apache.struts2.interceptor.SessionAware;

public class LogoutAction implements SessionAware{
    /**
     * Initializing private attributes
     */
    private Map session;

    /**
     * Return the current session
     * @return session
     */
    public Map getSession() {
        return session;
    }

    /**
     * Set the session
     * @param session the session to set
     */
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * Execute the logout function by clearing the session
     * @return SUCCESS
     */
    public String execute(){
        session.clear();
        return "SUCCESS";
    }
}
