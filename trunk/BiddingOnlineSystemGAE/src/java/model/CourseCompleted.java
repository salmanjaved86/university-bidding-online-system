/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author yewwei.tay.2009
 */
public class CourseCompleted {

    private String userId;
    private String courseCode;

    public CourseCompleted(String userId, String courseCode) {
        this.userId = userId;
        this.courseCode = courseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
