package bios;
import java.util.*;
import model.*;
import DataManager.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

public class ViewCourses extends ActionSupport implements SessionAware {
    /**
     * Initializing private attributes
     */
    private String studentId;
    private ArrayList<Course> courseReturn;
    private Map session;

    /**
     * Set the session
     * @param session the session to set
     */
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * Return the current session
     * @return session
     */
    public Map getSession() {
        return session;
    }

    /**
     * Return the ArrayList of courses
     * @return courseReturn
     */
    public ArrayList<Course> getCourseReturn() {
        return courseReturn;
    }

    /**
     * Return the studentId
     * @return studentId
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Set the courses to return
     * @param courseReturn the courseReturn to set
     */
    public void setCourseReturn(ArrayList<Course> courseReturn) {
        this.courseReturn = courseReturn;
    }

    /**
     * Set the studentId
     * @param studentId the studentId to set
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Executes the getBiddableCourses() method
     * @return "SUCCESS"
     */
    public String execute() {
        Student student = (Student) session.get("student");
        setStudentId(student.getUserId());
        getBiddableCourses();
        return "SUCCESS";
    }

    /**
     * Method retrieves the list of courses the student is able to bid for
     */
    public void getBiddableCourses() {
        //retrieve all courses
        ArrayList<Course> courseList = CourseDataManager.retrieveAll();
        courseReturn = new ArrayList<Course>();
        //retrieves list of courses that are completed by the student
        ArrayList<CourseCompleted> userCompletedCourseList = CourseCompletedManager.getCourseCompleted(studentId);
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
                //retrieves prerequisite courses
                ArrayList<PrerequisiteCourse> prereqCourses = PrerequisiteCourseDataManager.getPrerequisiteCourse(course.getCourseCode());
                //check if the course has prerequisite
                if (prereqCourses.size() != 0) {
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

    /**
     * Method checks for the round to determine the courses to be returned
     * @param course the course to check
     */
    private void checkRound(Course course) {
        if (Round.getRound() == 1) {
            //if course's school is the same school where the student is from
            if (course.getSchool().equals(StudentDataManager.getStudent(studentId).getSchool())) {
                courseReturn.add(course);
            }
        } else if (Round.getRound() == 2) {
            courseReturn.add(course);
        }
    }

    /**
     * Method checks if a particular course is already bidded by the student
     * @param course the course to check
     * @return true if the course is bidded by the student
     * @return false if the course is not bidded by the student
     */
    private boolean checkBidded(Course course) {
        ArrayList<Bid> studentBidList = BidDataManager.getStudentBids(studentId);
        ArrayList<Bid> studentSuccessfulBidList = SuccessfulBidDataManager.getStudentSuccessfulBids(studentId);
        for (Bid bid : studentBidList) {
            if (bid.getCourseCode().equals(course.getCourseCode())) {
                return true;
            }
        }
        for (Bid bid : studentSuccessfulBidList) {
            if (bid.getCourseCode().equals(course.getCourseCode())) {
                return true;
            }
        }
        return false;
    }
}
