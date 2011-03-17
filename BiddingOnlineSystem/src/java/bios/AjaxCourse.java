package bios;
import com.opensymphony.xwork2.ActionSupport;
import java.util.*;
import model.*;
import DataManager.*;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.interceptor.SessionAware;

public class AjaxCourse extends ActionSupport implements SessionAware {
    private static String[] staticLanguages = {
        "Actionscript (Flash)", "ABAP Objects", "Ada", "Aleph", "AppleScript", "Beta", "BlitzMax", "Boo", "C++", "C#", "Cecil", "Clarion", "Cobol ISO 2002", "CoDeSys", "CFML (ColdFusion Markup Language)", "Common Lisp Object System (CLOS)", "Component Pascal", "CorbaScript",
        "D", "Delphi", "Eiffel", "Fpii", "Fortran - ab Fortran 2003", "Gambas", "IDL", "IDLscript", "incr Tcl", "Java", "JavaScript / ECMAScript", "Lexico", "Lingo", "Modula-3", "Modelica", "NewtonScript", "Oberon", "Objective-C", "Objective CAML", "Object Pascal", "Perl",
        "PHP", "PowerBuilder", "Progress OpenEdge", "Python", "Ruby", "R", "Sather", "Scala", "Seed7", "Self", "Simula", "Smalltalk", "SuperCollider", "Superx++", "STOOOP", "Visual Basic", "Visual Basic .NET (VB.NET)", "Visual Basic Script", "Visual Objects", "XBase",
        "XOTcl", "Zonnon"};
    private String term;
    //private String[] courses = AjaxCourse.staticLanguages;
    private String[] courses = new String[40];
    private String studentId;
    private ArrayList<Course> courseReturn;
    private Map session;
    ViewCourses nebolly = new ViewCourses();
    ArrayList<Course> tempor = new ArrayList<Course>();
    public int sizer = 1;
    public String printer = "hello";

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
     * Method generates the courses to display
     * @return "success"
     */
    public String execute() throws Exception {
        Student student = (Student) session.get("student");
        studentId = student.getUserId();
        getBiddableCourses();
        sizer = courseReturn.size();
        String[] user = new String[sizer];
        for (int i = 0; i < courseReturn.size(); i++) {
            user[i] = courseReturn.get(i).getCourseCode() + ": " + courseReturn.get(i).getTitle();
            printer += courseReturn.get(i).getCourseCode() + ": " + courseReturn.get(i).getTitle();
        }
        courses = user;
        if (term != null && term.length() > 1) {
            ArrayList<String> tmp = new ArrayList<String>();
            for (int i = 0; i < user.length; i++) {
                if (StringUtils.contains(user[i].toLowerCase(), term.toLowerCase())) {
                    tmp.add(user[i]);
                }
            }
            courses = tmp.toArray(new String[tmp.size()]);
        }
        return "success";
    }

    /**
     * Return a String array of courses
     * @return courses
     */
    public String[] getCourses() {
        return courses;
    }

    /**
     * Return an ArrayList of courses
     * @return courses
     */
    public ArrayList<Course> getCourseReturn() {
        return courseReturn;
    }

    /**
     * Set the term
     * @return term
     */
    public void setTerm(String term) {
        this.term = term;
    }

    /**
     * Method retrieves all the courses that is available to the current student
     */
    public void getBiddableCourses() {
        //retrieve all the courses
        ArrayList<Course> courseList = CourseDataManager.retrieveAll();
        courseReturn = new ArrayList<Course>();
        //retrieves all the courses completed by the current student
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
     * Method performs check on the current round to ensure that student can bid for courses from other
     * schools only in round 2
     */
    private void checkRound(Course course) {
        if (Round.getRound() == 1) {
            if (course.getSchool().equals(StudentDataManager.getStudent(studentId).getSchool())) {
                courseReturn.add(course);
            }
        } else if (Round.getRound() == 2) {
            courseReturn.add(course);
        }
    }

    /**
     * Method checks if course is already bidded by the student
     * @return true if course is bidded
     * @return false if course is not bidded
     */
    private boolean checkBidded(Course course) {
        ArrayList<Bid> studentBidList = BidDataManager.getStudentBids(studentId);
        for (Bid bid : studentBidList) {
            Course biddedCourse = CourseDataManager.getCourse(bid.getCourseCode());
            if (biddedCourse.getCourseCode().equals(course.getCourseCode())) {
                return true;
            }
        }
        return false;
    }
}
