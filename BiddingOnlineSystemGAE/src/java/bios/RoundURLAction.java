/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import model.*;
import org.json.*;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author yewwei.tay.2009
 */
public class RoundURLAction extends ActionSupport implements RequestAware {

    private String action;
    private JSONObject obj;
    private Map request;

    public JSONObject getObj() {
        return obj;
    }

    public void setObj(JSONObject obj) {
        this.obj = obj;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setRequest(Map arg0) {
        request = arg0;
    }

    public Map getRequest() {
        return request;
    }

    public String execute() {
        try {
            obj = new JSONObject();
            if (action.equals("start")) {
                if (Round.getRoundStart() == true) {
                    obj.put("status", "INVALID");
                } else {

                    Round.setRoundStart(true);
                    if (Round.getRound() == 1) {
                        Round.setRound(2);
                    } else {
                        Round.setRound(1);
                    }

                    obj.put("status", "OK");
                }
            } else if (action.equals("stop")) {
                if (Round.getRoundStart() == false) {
                    obj.put("status", "INVALID");
                } else {
                    Round.setRoundStart(false);
                    RoundControl.clearRound();
                    obj.put("status", "OK");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.put("JSONObject", obj);
        }
        return "SUCCESS";
    }
}
