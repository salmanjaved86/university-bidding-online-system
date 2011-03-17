/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import java.util.*;
import model.*;
import DataManager.*;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author yewwei.tay.2009
 */
public class AjaxSections extends ActionSupport {
    /**
     * Initializing private attributes
     */
    private String courseCode;
    private ArrayList<Section> sectionReturn;

    /**
     * @return the courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * @return the sectionReturn
     */
    public ArrayList<Section> getSectionReturn() {
        return sectionReturn;
    }

    /**
     * @param sectionReturn the sectionReturn to set
     */
    public void setSectionReturn(ArrayList<Section> sectionReturn) {
        this.sectionReturn = sectionReturn;
    }

    /**
     * This method retrieves the sections and the number of vacancies of a particular course
     * @return "success"
     */
    public String execute() {
        //retrives sections of the course's code
        sectionReturn = SectionDataManager.getSectionWithCourseCode(courseCode);
        for(Section section : sectionReturn){
            //for each section, retrieves the number of vacancies
            int vacancy = SectionDataManager.getSectionVacancy(section.getCourseCode(), section.getSectionCode());
            //set the vacancy of the session
            section.setVacancy(vacancy);
        }
        return "success";
    }
}

