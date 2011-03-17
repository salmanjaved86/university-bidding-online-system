package bios;
import com.opensymphony.xwork2.ActionSupport;

public class EditBidView extends ActionSupport {
    /**
     * Initializing private attributes
     */
    private String courseCode;
    private String sectionCode;

    /**
     * Return the section code of the edited bid
     * @return sectionCode
     */
    public String getSectionCode() {
        return sectionCode;
    }

    /**
     * Set the sectionCode
     * @param sectionCode the sectionCode to set
     */
    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    /**
     * Return the course code of the edited bid
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
     * This method executes the view for edit bit function
     * @return "success"
     */
    public String execute() {
        return "SUCCESS";
    }
}

