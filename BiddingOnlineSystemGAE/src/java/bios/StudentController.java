/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bios;

import java.util.*;
import JDOModel.*;
import JDODataManager.*;
import org.apache.struts2.interceptor.SessionAware;
/**
 *
 * @author yewwei.tay.2009
 */
public class StudentController implements SessionAware {

    private static Map session;

    public static Map getSession(){
        return session;
    }

    public void setSession(Map session){
        this.session = session;
    }
    public static void editStudenteDollar(Student student,double bal){
        student.setEdollar(bal);
        StudentDataManager.editStudent(student,bal);
     }
}
