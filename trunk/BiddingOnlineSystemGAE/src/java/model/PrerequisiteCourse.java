/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author yewwei.tay.2009
 */
public class PrerequisiteCourse {

    private String courseCode;
    private String prereqCourseCode;

    public PrerequisiteCourse(String courseCode, String prereqCourseCode) {
        this.courseCode = courseCode;
        this.prereqCourseCode = prereqCourseCode;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getPrereqCourseCode() {
        return prereqCourseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setPrereqCourseCode(String prereqCourseCode) {
        this.prereqCourseCode = prereqCourseCode;
    }
}
