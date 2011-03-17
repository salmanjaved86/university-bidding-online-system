/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDOModel;

import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 *
 * @author yewwei.tay.2009
 */
@PersistenceCapable
public class PrerequisiteCourse {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String courseCode;

    @Persistent
    private String prereqCourseCode;

    public PrerequisiteCourse(String courseCode, String prereqCourseCode) {
        this.courseCode = courseCode;
        this.prereqCourseCode = prereqCourseCode;
    }

    public Key getKey() {
        return key;
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
