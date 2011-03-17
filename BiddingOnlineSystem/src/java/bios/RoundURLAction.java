package bios;
import model.*;
import org.json.*;
import java.util.Map;
import org.apache.struts2.interceptor.RequestAware;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;

public class RoundURLAction extends ActionSupport implements RequestAware, SessionAware {
    /**
     * Initializing private attributes
     */
    private String action;
    private JSONObject obj;
    private Map request;
    private Map session;
    private String password;

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
     * Return the password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password to check
     * @param password the password to check
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Return the JSON object
     * @return obj
     */
    public JSONObject getObj() {
        return obj;
    }

    /**
     * Set the JSON object
     * @param obj the obj to set
     */
    public void setObj(JSONObject obj) {
        this.obj = obj;
    }

    /**
     * Return the action
     * @return action
     */
    public String getAction() {
        return action;
    }

    /**
     * Set the action
     * @param action the action to set
     */
    public void setAction(String action) {
        this.action = action;
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
     * @return the request
     */
    public Map getRequest() {
        return request;
    }

    /**
     * This method perform checks and start/stop round according to the user input in the URL
     * @return success
     */
    public String execute() {
        String ADMIN_PASSWORD = getText("admin.password");
        if (!ADMIN_PASSWORD.equals(password)) {
            try {
                obj = new JSONObject();
                //display invalid
                obj.put("status", "INVALID");
                request.put("JSONObject", obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                obj = new JSONObject();
                //user wants to start round
                if (action.equals("start")) {
                    //if round is already started
                    if (Round.getRoundStart() == true) {
                        //print invalid
                        obj.put("status", "INVALID");
                    } else {
                        //current round is 1
                        if (Round.getRound() == 1) {
                            //round is started
                            Round.setRoundStart(true);
                            //set start round to round 2
                            Round.setRound(2);
                            //prints success message
                            obj.put("status", "OK");
                        } else {
                            //invalid because admin has to bootstrap to return from round 2 to round 1
                            obj.put("status", "INVALID");
                        }
                    }
                }
                //user wants to stop round
                else if (action.equals("stop")) {
                    //if round already stopped
                    if (Round.getRoundStart() == false) {
                        //prints unsuccessful message
                        obj.put("status", "INVALID");
                    }
                    //round is not stopped
                    else {
                        //stop the round
                        Round.setRoundStart(false);
                        //clear round
                        RoundControl.clearRound();
                        //prints success message
                        obj.put("status", "OK");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                request.put("JSONObject", obj);
            }
        }
        return "SUCCESS";
    }
}
