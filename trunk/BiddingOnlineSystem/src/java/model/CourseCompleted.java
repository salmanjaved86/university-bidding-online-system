package model;

public class CourseCompleted {

    private String userId;
    private String courseCode;

    /**
     * initialize the course completed constructor
     * @param userId the student's user id
     * @param courseCode the course code
     */
    public CourseCompleted(String userId, String courseCode) {
        this.userId = userId;
        this.courseCode = courseCode;
    }

    /**
     * retrieves the course code
     * @return the course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * retrieves the user id
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * sets the course code
     * @param courseCode the course code
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * sets the user id
     * @param userId the student's user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
