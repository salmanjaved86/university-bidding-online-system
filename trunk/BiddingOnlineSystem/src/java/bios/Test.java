/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bios;

import java.util.*;
import model.*;
import DataManager.*;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author yewwei.tay.2009
 */
public class Test extends ActionSupport{

    private String courseCode;

    public String getCourseCode(){
        return courseCode;
    }

    public void setCourseCode(String courseCode){
        this.courseCode = courseCode;
    }

    public String execute(){
        return "SUCCESS";
    }



}
