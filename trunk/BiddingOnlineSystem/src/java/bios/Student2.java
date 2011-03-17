/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import javax.jdo.annotations.PersistenceCapable;

/**
 *
 * @author Administrator
 */
@PersistenceCapable
public class Student2 {
    private String userid = null;
    private String password = null;
    private String name = null;
    private String school = null;
    private String edollar = null;

    public void Student2(){

    }

    public Student2(String userid, String password, String name, String school, String edollar){
        this.setUserid(userid);
        this.setPassword(password);
        this.setName(name);
        this.setSchool(school);
        this.setEdollar(edollar);
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
