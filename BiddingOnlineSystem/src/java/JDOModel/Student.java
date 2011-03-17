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
    private String userid = null;
    
    @Persistent
    private String password = null;

    @Persistent
    private String name = null;

    @Persistent
    private String school = null;
    
    @Persistent
    private String edollar = null;

    public void Student2(){

    }

    public Student(String userid, String password, String name, String school, String edollar){
        this.setUserid(userid);
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
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
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
    public String getEdollar() {
        return edollar;
    }

    /**
     * @param edollar the edollar to set
     */
    public void setEdollar(String edollar) {
        this.edollar = edollar;
    }


}
