package bios;
import java.util.*;
import model.*;
import DataManager.*;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

public class ViewBids extends ActionSupport implements SessionAware {
    /**
     * Initializing private attributes
     */
    private ArrayList<Bid> monBids;
    private ArrayList<Bid> tueBids;
    private ArrayList<Bid> wedBids;
    private ArrayList<Bid> thuBids;
    private ArrayList<Bid> friBids;
    private ArrayList<Bid> studentBids;
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
     * Set the ArrayList of student bids
     * @param studentBids the studentBids
     */
    public void setStudentBids(ArrayList<Bid> studentBids) {
        this.studentBids = studentBids;
    }

    /**
     * Return the ArrayList of student bids
     * @return studentBids
     */
    public ArrayList<Bid> getStudentBids() {
        return studentBids;
    }

    /**
     * Return all monday bids
     * @return monBids
     */
    public ArrayList<Bid> getMonBids() {
        return monBids;
    }

    /**
     * Return all Tuesdays bids
     * @return tueBids
     */
    public ArrayList<Bid> getTueBids() {
        return tueBids;
    }

    /**
     * Return all wednesday bids
     * @return wedBids
     */
    public ArrayList<Bid> getWedBids() {
        return wedBids;
    }

    /**
     * Return all thursday bids
     * @return thuBids
     */
    public ArrayList<Bid> getThuBids() {
        return thuBids;
    }

    /**
     * Return all friday bids
     * @return friBids
     */
    public ArrayList<Bid> getFriBids() {
        return friBids;
    }

    /**
     * Set friday bids
     * @param friBids the friBids
     */
    public void setFriBids(ArrayList<Bid> friBids) {
        this.friBids = friBids;
    }

    /**
     * Set monday bids
     * @param monBids the monBids
     */
    public void setMonBids(ArrayList<Bid> monBids) {
        this.monBids = monBids;
    }

    /**
     * Set thurday bids
     * @param thuBids the thuBids
     */
    public void setThuBids(ArrayList<Bid> thuBids) {
        this.thuBids = thuBids;
    }

    /**
     * Set tuesday bids
     * @param tueBids the tueBids
     */
    public void setTueBids(ArrayList<Bid> tueBids) {
        this.tueBids = tueBids;
    }

    /**
     * Set wednesday bids
     * @param wedBids the wedBids
     */
    public void setWedBids(ArrayList<Bid> wedBids) {
        this.wedBids = wedBids;
    }

    /**
     * Executes the viewBids function by retrieving pending bids and successful bids from student.
     * Executes sortBidsForDisplay() method to sort the bids.
     * Add "no bids" message if student do not have bids
     * @return SUCCESS
     */
    public String execute() {
        Student student = (Student) session.get("student");
        studentBids = new ArrayList<Bid>();
        ArrayList<Bid> studentPendingBidList = BidDataManager.getStudentBids(student.getUserId());
        ArrayList<Bid> studentSuccessfulBidList = SuccessfulBidDataManager.getStudentSuccessfulBids(student.getUserId());
        for (Bid bid : studentPendingBidList) {
            studentBids.add(bid);
        }
        for (Bid bid : studentSuccessfulBidList) {
            studentBids.add(bid);
        }
        sortBidsForDisplay();
        if (studentBids.isEmpty()) {
            //display "no bids"
            addActionMessage("You do not have any bids.");
        }
        return "SUCCESS";
    }

    /**
     * Method executes sortBidsIntoDays() and sortDayBidsByTime() for display purpose
     */
    public void sortBidsForDisplay() {
        sortBidsIntoDays();
        sortDayBidsByTime(monBids);
        sortDayBidsByTime(tueBids);
        sortDayBidsByTime(wedBids);
        sortDayBidsByTime(thuBids);
        sortDayBidsByTime(friBids);
    }

    /**
     * Method to sort bids into five different ArrayList<Bid> for display purpose
     */
    public void sortBidsIntoDays() {
        monBids = new ArrayList<Bid>();
        tueBids = new ArrayList<Bid>();
        wedBids = new ArrayList<Bid>();
        thuBids = new ArrayList<Bid>();
        friBids = new ArrayList<Bid>();
        for (Bid bid : studentBids) {
            Section section = SectionDataManager.getSection(bid.getCourseCode(), bid.getSectionCode());
            if (section.getDay().equals("Monday")) {
                monBids.add(bid);
            } else if (section.getDay().equals("Tuesday")) {
                tueBids.add(bid);
            } else if (section.getDay().equals("Wednesday")) {
                wedBids.add(bid);
            } else if (section.getDay().equals("Thursday")) {
                thuBids.add(bid);
            } else if (section.getDay().equals("Friday")) {
                friBids.add(bid);
            }
        }
    }

    /**
     * Method to sort bids into different Time for display purpose
     * @param dayBids the bids to sort
     */
    public void sortDayBidsByTime(ArrayList<Bid> dayBids) {

        for (int i = 0; i < dayBids.size() - 1; i++) {
            String time = SectionDataManager.getSection(dayBids.get(i).getCourseCode(), dayBids.get(i).getSectionCode()).getStart();
            int colon = time.indexOf(":");
            int digit = Integer.parseInt(time.substring(0, colon));
            int guess = i;
            for (int j = i + 1; j < dayBids.size(); ++j) {
                String time2 = SectionDataManager.getSection(dayBids.get(j).getCourseCode(), dayBids.get(j).getSectionCode()).getStart();
                int colon2 = time2.indexOf(":");
                int digit2 = Integer.parseInt(time2.substring(0, colon2));
                if (digit2<digit) {
                    guess = j;
                }
            }
            Collections.swap(dayBids, i, guess);
        }
    }
}

