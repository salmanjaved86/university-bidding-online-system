package bios;
import java.util.*;
import model.*;
import DataManager.*;
import com.opensymphony.xwork2.ActionSupport;

public class ViewSections extends ActionSupport {
    /**
     * Initializing private attributes
     */
    private String courseCode;
    private ArrayList<Section> sectionReturn;

    /**
     * Return the course code
     * @return courseCode
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Set the course code
     * @param courseCode the courseCode to set
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * Return the ArrayList of sections
     * @return sectionReturn
     */
    public ArrayList<Section> getSectionReturn() {
        return sectionReturn;
    }

    /**
     * Set the sections to return
     * @param sectionReturn the sectionReturn to set
     */
    public void setSectionReturn(ArrayList<Section> sectionReturn) {
        this.sectionReturn = sectionReturn;
    }

    /**
     * Retrieves the sections for a particular courseCode
     * @return "SUCCESS"
     */
    public String execute() {
        sectionReturn = SectionDataManager.getSectionWithCourseCode(courseCode);
        for(Section section : sectionReturn){
            int vacancy = SectionDataManager.getSectionVacancy(section.getCourseCode(), section.getSectionCode());
            section.setVacancy(vacancy);
        }
        return "SUCCESS";
    }
}

