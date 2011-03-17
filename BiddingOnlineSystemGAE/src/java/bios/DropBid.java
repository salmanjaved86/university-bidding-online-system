/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import java.util.*;
import model.*;
import DataManager.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author yewwei.tay.2009
 */
public class DropBid extends ActionSupport implements SessionAware {

    private Student student;
    private Map session;
    private String courseCode;


    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    public String execute() {

        student = (Student)session.get("student");
        ArrayList<Bid> studentBids = BidDataManager.getStudentBids(student.getUserId());

        for (int i = 0; i < studentBids.size(); i++) {
            Bid bid = studentBids.get(i);
            if (courseCode.equals(bid.getCourseCode())) {
                double bidAmt = bid.getAmount();
                double bal = bidAmt + student.getEdollar();
                StudentController.editStudenteDollar(student, bal);
                BidDataManager.dropBid(student.getUserId(), courseCode);
            }
        }

        addActionMessage("You have dropped "+courseCode);
        return "SUCCESS";
    }
}
