/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bios;

import java.util.*;
import model.*;
import org.apache.struts2.interceptor.SessionAware;
/**
 *
 * @author yewwei.tay.2009
 */
public class LogoutAction implements SessionAware{

    private Map session;

    public Map getSession() {
        return session;
    }

  public void setSession(Map session) {
        this.session = session;
    }

  public String execute(){

      return "SUCCESS";
  }

}
