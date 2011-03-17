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
public class EditBid extends ActionSupport implements SessionAware {

    private String userId;
    private Student student;
    private Map session;
    private String courseCode;
    private String sectionCode;
    private String amount;
    private double amt;
    private double bal;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
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

    public void validate() {
        student = (Student) session.get("student");
        userId = student.getUserId();

        if (amount.isEmpty()) {
            addActionError("Please enter a valid amount");
        } else {
            try {
                amt = Double.parseDouble(amount);
                if (amt <= 0) {
                    throw new Exception("Please enter a valid amount.");
                }

            } catch (NumberFormatException e) {
                addActionError("Please enter a valid amount.");
            } catch (Exception e) {
                addActionError(e.getMessage());
            }
        }

        if (!checkEditValid()) {
            addActionError("Insufficient eDollar.");
        }

    }

    public String execute() {
        StudentController.editStudenteDollar(student, bal);
        BidDataManager.editBid(student.getUserId(), courseCode, sectionCode, amt);
        return "SUCCESS";
    }

    private boolean checkEditValid() {

        ArrayList<Bid> studentBids = BidDataManager.getStudentBids(userId);

        for (int i = 0; i < studentBids.size(); i++) {
            Bid bid = studentBids.get(i);
            if (bid.getCourseCode().equals(courseCode)) {

                double biddedAmt = bid.getAmount();
                double cumulativeAmt = biddedAmt + student.getEdollar();

                if (amt < cumulativeAmt || amt == cumulativeAmt) {
                    bal = cumulativeAmt - amt;
                    return true;
                }
            }
        }
        return false;
    }
}
