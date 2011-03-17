package model;

public class PrerequisiteCourse {

    private String courseCode;
    private String prereqCourseCode;

    /**
     * initialize the prerequisite course constructor
     * @param courseCode the course code
     * @param prereqCourseCode the prerequisite course code
     */
    public PrerequisiteCourse(String courseCode, String prereqCourseCode) {
        this.courseCode = courseCode;
        this.prereqCourseCode = prereqCourseCode;
    }

    /**
     * retrieve the course code
     * @return the course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * retrieves the prerequisite course code
     * @return the prerequisite course code
     */
    public String getPrereqCourseCode() {
        return prereqCourseCode;
    }

    /**
     * sets the course code
     * @param courseCode the course code
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * sets the prerequisite course code
     * @param prereqCourseCode the prerequisite course code
     */
    public void setPrereqCourseCode(String prereqCourseCode) {
        this.prereqCourseCode = prereqCourseCode;
    }
}
