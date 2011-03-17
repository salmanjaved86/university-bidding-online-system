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
import java.text.*;
/**
 *
 * @author Administrator
 */
@PersistenceCapable
public class Student {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;


    @Persistent
    private String userId = null;
    
    @Persistent
    private String password = null;

    @Persistent
    private String name = null;

    @Persistent
    private String school = null;
    
    @Persistent
    private double edollar = 0;

    public void Student2(){

    }

    public Student(String userid, String password, String name, String school, double edollar){
        this.setUserId(userid);
        this.setPassword(password);
        this.setName(name);
        this.setSchool(school);
        this.setEdollar(edollar);
    }



    public Key getKey() {
        return key;
    }


    /**
     * @return the userid
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserId(String userid) {
        this.userId = userid;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * @return the edollar
     */
    public double getEdollar() {
        return edollar;
    }

    /**
     * @param edollar the edollar to set
     */
    public void setEdollar(double edollar) {
        this.edollar = edollar;
    }

    public String getEdollarTwoDecimal() {
        DecimalFormat dmt = new DecimalFormat("#,##0.00");
        return dmt.format(edollar);
    }
}
