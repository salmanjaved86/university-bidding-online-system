/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import java.util.*;
import JDOModel.*;
import JDODataManager.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import model.Round;

/**
 *
 * @author yewwei.tay.2009
 */
public class ViewCourses extends ActionSupport implements SessionAware {

    private String studentId;
    private ArrayList<Course> courseReturn;
    private Map session;

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    public ArrayList<Course> getCourseReturn() {
        return courseReturn;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setCourseReturn(ArrayList<Course> courseReturn) {
        this.courseReturn = courseReturn;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String execute() {
        //Student student = (Student) session.get("student");
        Student student = StudentDataManager.getStudent("fred.ng.2009");
        setStudentId(student.getUserId());
        getBiddableCourses();
        return "SUCCESS";
    }

    public void getBiddableCourses() {

        List<Course> courseList = CourseDataManager.retrieveAll();
        courseReturn = new ArrayList<Course>();

        List<CourseCompleted> userCompletedCourseList = CourseCompletedManager.getCourseCompleted(studentId);

        for (Course course : courseList) {

            int completed = 0;

            //for each course, check if the student has already completed it
            for (CourseCompleted courseCompleted : userCompletedCourseList) {
                if (courseCompleted.getCourseCode().equals(course.getCourseCode())) {
                    completed++;
                }
            }

            //if the student has not completed the course
            if (completed == 0) {

                List<PrerequisiteCourse> prereqCourses = PrerequisiteCourseDataManager.getPrerequisiteCourse(course.getCourseCode());

                //check if the course has prerequisite
                if (!prereqCourses.isEmpty()) {

                    int count = 0;

                    // check if student has completed the prerequisite courses
                    for (PrerequisiteCourse prereq : prereqCourses) {
                        for (CourseCompleted courseCompleted : userCompletedCourseList) {
                            if (courseCompleted.getCourseCode().equals(prereq.getPrereqCourseCode())) {
                                count++;
                            }
                        }
                    }

                    //if student has completed all the prerequisite courses
                    if (count == prereqCourses.size()) {

                        if (!checkBidded(course)) {
                            checkRound(course);
                        }

                    }
                } else {
                    if (!checkBidded(course)) {
                        checkRound(course);
                    }

                }
            }

        }
    }

    private void checkRound(Course course) {
        if (Round.getRound() == 1) {
            if (course.getSchool().equals(StudentDataManager.getStudent(studentId).getSchool())) {
                courseReturn.add(course);
            }
        } else if (Round.getRound() == 2) {
            courseReturn.add(course);
        }
    }

    private boolean checkBidded(Course course) {

        List<Bid> studentBidList = BidDataManager.getStudentBids(studentId);

        for (Bid bid : studentBidList) {

            Course biddedCourse = CourseDataManager.getCourse(bid.getCourseCode());

            if (biddedCourse.getCourseCode().equals(course.getCourseCode())) {
                return true;
            }
        }
        return false;
    }

    

}
