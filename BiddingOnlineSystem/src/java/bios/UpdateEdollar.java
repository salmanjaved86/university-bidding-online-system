package bios;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import java.util.*;
import model.*;

public class UpdateEdollar extends ActionSupport implements SessionAware {
    /**
     * initializing private attributes
     */
    private Student student;
    private Map session;
    private String message;

    /**
     * Return the message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Return the current student
     * @return student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Set the student
     * @param student the student to set
     */
    public void setStudent(Student student) {
        this.student = student;
    }

    /**
     * Set the session
     * @param session the session to set
     */
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * Return the current session
     * @return session
     */
    public Map getSession() {
        return session;
    }

    /**
     * This method calls the function to format the student eDollar amount to 2 decimal place
     * @return "success"
     */
    public String execute(){
        student = (Student)session.get("student");
        message = student.getEdollarTwoDecimal();
        return "SUCCESS";
    }


}
