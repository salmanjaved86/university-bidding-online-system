/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import JDOModel.*;
import JDODataManager.*;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author yewwei.tay.2009
 */
public class LoginAction extends ActionSupport implements SessionAware {

    /**
     * Initializing private attributes
     */
    private String userId;
    private String password;
    private Map session;
    private String errorMessage;
    private String ADMIN_PASSWORD;

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @param errorMessage the errorMessage to set
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * @param session the session to set
     */
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * @return the session
     */
    public Map getSession() {
        return session;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Execute the authentication function to validate login
     * @return SUCCESS if authentication is invalid
     * @return ADMIN if login is admin
     * @return STUDENT if login is student
     */
    public String execute() {


        ADMIN_PASSWORD = "123";
        //checks if administrator account
        if (userId.equals("admin")) {
            if (password.equals(ADMIN_PASSWORD)) {
                //session.put("admin", new Admin("admin", ADMIN_PASSWORD));
                return "ADMIN";
            } else {
                errorMessage = "Invalid password.";
                return "SUCCESS";
            }
        } else {
            if (true == true) {
                //checks if valid student account
                if (!userId.equals("admin")) {


                    //checks student account username
                    if ((userId.equals("")) && (password.equals(""))) {
                        errorMessage = "Invalid input.";
                        return "SUCCESS";
                    } else if (StudentDataManager.getStudent(userId) == null) {
                        errorMessage = "Invalid username.";
                        return "SUCCESS";
                    } else {
                        //checks student account password
                        if (!StudentDataManager.getStudent(userId).getPassword().equals(password)) {
                            errorMessage = "Invalid password.";
                            return "SUCCESS";
                        } else {
                            Student student = StudentDataManager.getStudent(userId);
                            //session.put("student", student);
                            return "STUDENT";
                        }
                    }
                }
            } else {
                errorMessage = "Bidding round not started.";
                return "SUCCESS";
            }
        }

        return "SUCCESS";
    }
}
