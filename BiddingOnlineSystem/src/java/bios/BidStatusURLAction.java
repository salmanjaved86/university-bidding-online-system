package bios;

import model.*;
import DataManager.*;
import org.json.*;
import java.util.*;
import org.apache.struts2.interceptor.RequestAware;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

public class BidStatusURLAction extends ActionSupport implements RequestAware, SessionAware {
    /**
     * Initializing private attributes
     */
    private String course;
    private String section;
    private ArrayList<JSONObject> list;
    private Map request;
    private Map session;
    private String password;

    /**
     * Return the course
     * @return course
     */
    public String getCourse() {
        return course;
    }

    /**
     * Set the course
     * @param course the course to set
     */
    public void setCourse(String course) {
        this.course = course;
    }

    /**
     * Return the section
     * @return section
     */
    public String getSection() {
        return section;
    }

    /**
     * Set the section
     * @param section the section to set
     */
    public void setSection(String section) {
        this.section = section;
    }

    /**
     * Return the current session
     * @return session
     */
    public Map getSession() {
        return session;
    }

    /**
     * Set the session
     * @param session the session to set
     */
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * Return the password of the student
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the ArrayList of JSON objects
     * @return list
     */
    public ArrayList<JSONObject> getList() {
        return list;
    }

    /**
     * Set the list of JSON objects
     * @param list the list to set
     */
    public void setList(ArrayList<JSONObject> list) {
        this.list = list;
    }

    /**
     * Set the request
     * @param arg0 the request to set
     */
    public void setRequest(Map arg0) {
        request = arg0;
    }

    /**
     * Return the request
     * @return request
     */
    public Map getRequest() {
        return request;
    }

    /**
     * This method generates the JSON responses for successful bids and unsuccessful bids
     * @return "success"
     */
    public String execute() {
        //retrives admin's password from global.properties file
        String ADMIN_PASSWORD = getText("admin.password");
        //check if password is correct
        if (!ADMIN_PASSWORD.equals(password)) {
            try {
                list = new ArrayList<JSONObject>();
                JSONObject obj = new JSONObject();
                //display invalid
                obj.put("status", "INVALID");
                request.put("JSONObjectList", list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                ArrayList<Bid> successfulBidsList = RoundControl.getSuccessfulBids();
                ArrayList<Bid> allBidsList = BidDataManager.retrieveAll();
                ArrayList<Bid> bidListForSection = new ArrayList<Bid>();
                list = new ArrayList<JSONObject>();
                for (Bid bid : allBidsList) {
                    if (bid.getCourseCode().equals(course) && bid.getSectionCode().equals(section)) {
                        bidListForSection.add(bid);
                    }
                }
                for (Bid bid : bidListForSection) {
                    JSONObject obj = new JSONObject();
                    if (successfulBidsList.contains(bid)) {
                        obj.put("userid", bid.getUserId());
                        obj.put("bid", bid.getAmount());
                        obj.put("status", "IN");
                        list.add(obj);
                    } else {
                        obj.put("userid", bid.getUserId());
                        obj.put("bid", bid.getAmount());
                        obj.put("status", "OUT");
                        list.add(obj);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                request.put("JSONObjectList", list);
            }
        }
        return "SUCCESS";
    }
}
