/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import java.util.*;
import JDOModel.*;
import JDODataManager.*;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author yewwei.tay.2009
 */
public class ViewSections extends ActionSupport {

    private String courseCode;
    private List<Section> sectionReturn = new ArrayList<Section>();

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public List<Section> getSectionReturn() {
        return sectionReturn;
    }

    public void setSectionReturn(ArrayList<Section> sectionReturn) {
        this.sectionReturn = sectionReturn;
    }

    public String execute() {
        sectionReturn = SectionDataManager.getSectionWithCourseCode(courseCode);
        return "SUCCESS";
    }
}

