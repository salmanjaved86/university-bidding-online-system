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
public class Section {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String courseCode;

    @Persistent
    private String sectionCode;

    @Persistent
    private String day;

    @Persistent
    private String start;

    @Persistent
    private String end;

    @Persistent
    private String instructor;

    @Persistent
    private String venue;

    @Persistent
    private int size;

    public Section(String courseCode, String sectionCode, String day, String start, String end, String instructor, String venue, int size) {
        this.courseCode = courseCode;
        this.sectionCode = sectionCode;
        this.day = day;
        this.start = start;
        this.end = end;
        this.instructor = instructor;
        this.venue = venue;
        this.size = size;
    }

    public Key getKey() {
        return key;
    }


    public void setCouresCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDay() {
        return day;
    }

    public String getEnd() {
        return end;
    }

    public String getInstructor() {
        return instructor;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public int getSize() {
        return size;
    }

    public String getStart() {
        return start;
    }

    public String getVenue() {
        return venue;
    }
}
