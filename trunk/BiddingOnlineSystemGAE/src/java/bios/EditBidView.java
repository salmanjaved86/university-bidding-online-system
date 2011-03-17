/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author yewwei.tay.2009
 */
public class EditBidView extends ActionSupport {

    private String courseCode;
    private String sectionCode;

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }


    public String execute() {
        return "SUCCESS";
    }
}

