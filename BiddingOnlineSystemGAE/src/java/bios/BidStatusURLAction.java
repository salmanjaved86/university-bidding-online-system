/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import model.*;
import DataManager.*;
import org.json.*;
import java.util.*;
import org.apache.struts2.interceptor.RequestAware;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author yewwei.tay.2009
 */
public class BidStatusURLAction extends ActionSupport implements RequestAware {

    private String courseCode;
    private String sectionCode;
    private ArrayList<JSONObject> list;
    private Map request;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public ArrayList<JSONObject> getList() {
        return list;
    }

    public void setList(ArrayList<JSONObject> list) {
        this.list = list;
    }

    public void setRequest(Map arg0) {
        request = arg0;
    }

    public Map getRequest() {
        return request;
    }

    public String execute() {

        try {
            ArrayList<Bid> successfulBidsList = RoundControl.getSuccessfulBids();
            ArrayList<Bid> allBidsList = BidDataManager.retrieveAll();
            ArrayList<Bid> bidListForSection = new ArrayList<Bid>();
            list = new ArrayList<JSONObject>();

            for (Bid bid : allBidsList) {
                if (bid.getCourseCode().equals(courseCode) && bid.getSectionCode().equals(sectionCode)) {
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
        return "SUCCESS";
    }
}
