package model;

public class Section {

    private String courseCode;
    private String sectionCode;
    private String day;
    private String start;
    private String end;
    private String instructor;
    private String venue;
    private int size;
    private int vacancy;
    private double minBid = 10.0;

    /**
     * initialize the section constructor
     * @param courseCode the course code
     * @param sectionCode the section code
     * @param day the day of the section
     * @param start the start time of the section
     * @param end the end time of the section
     * @param instructor the instructor for the section
     * @param venue the venue of the section
     * @param size the size of the section
     */
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

    /**
     * retrieve the vacancy of the section
     * @return the vacancy of the section
     */
    public int getVacancy() {
        return vacancy;
    }

    /**
     * sets the vacancy of the section
     * @param vacancy the vacancy of the section
     */
    public void setVacancy(int vacancy) {
        this.vacancy = vacancy;
    }

    /**
     * retrieve the minimum bid of the section
     * @return the minimum bid of the section
     */
    public double getMinBid() {
        return minBid;
    }

    /**
     * sets the minimum bid of the section
     * @param minBid the minimum bid of the section
     */
    public void setMinBid(double minBid) {
        this.minBid = minBid;
    }

    /**
     * sets the course code
     * @param courseCode the course code
     */
    public void setCouresCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * sets the day of the section
     * @param day the day of the section
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * sets the end time of the section
     * @param end the end time of the section
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * sets the instructor of the section
     * @param instructor the instructor of the section
     */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    /**
     * sets the section code of the section
     * @param sectionCode the section code of the section
     */
    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    /**
     * sets the size of the section
     * @param size the size of the section
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * sets the start time of the section
     * @param start the start time of the section
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * sets the venue of the section
     * @param venue the venue of the section
     */
    public void setVenue(String venue) {
        this.venue = venue;
    }

    /**
     * retrieve the course code of the section
     * @return the course code of the section
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * retrieve the day of the section
     * @return the day of the section
     */
    public String getDay() {
        return day;
    }

    /**
     * retrieve the end time of the section
     * @return the end time of the section
     */
    public String getEnd() {
        return end;
    }

    /**
     * retrieve the instructor of the section
     * @return the instructor of the section
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * retrieve the section code of the section
     * @return the section code of the section
     */
    public String getSectionCode() {
        return sectionCode;
    }

    /**
     * retrieve the size of the section
     * @return the size of the section
     */
    public int getSize() {
        return size;
    }

    /**
     * retrieve the start time of the section
     * @return the start time of the section
     */
    public String getStart() {
        return start;
    }

    /**
     * retrieve the venue of the section
     * @return the venue of the section
     */
    public String getVenue() {
        return venue;
    }
}
