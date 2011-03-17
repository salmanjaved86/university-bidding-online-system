/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author yewwei.tay.2009
 */
public class Student {

    private String userId;
    private String password;
    private String name;
    private String school;
    private double eDollar;

    public Student(String userId, String password, String name, String school, double eDollar) {
        this.userId = userId;
        this.password = password;
        this.name = name;
        this.school = school;
        this.eDollar = eDollar;
    }

    public void setEdollar(double eDollar) {
        this.eDollar = eDollar;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getEdollar() {
        return eDollar;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSchool() {
        return school;
    }

    public String getUserId() {
        return userId;
    }
}
