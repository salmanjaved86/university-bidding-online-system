/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package JDOModel;

import java.util.Comparator;
import com.google.appengine.api.datastore.Key;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import java.text.*;

/**
 *
 * @author yewwei.tay.2009
 */
@PersistenceCapable
public class SuccessfulBid/* implements Comparable<Bid>*/ {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String userId;

    @Persistent
    private String courseCode;

    @Persistent
    private String sectionCode;

    @Persistent
    private double amount;

    @Persistent
    private boolean bidStatus;

    public boolean getBidStatus(){
        return bidStatus;
    }

    public void setBidStatus(boolean status){
        this.bidStatus = status;
    }

    public SuccessfulBid(SuccessfulBid bid){
        this.userId = bid.getUserId();
        this.courseCode=bid.getCourseCode();
        this.sectionCode=bid.getSectionCode();
        this.amount=bid.getAmount();
    }

    public SuccessfulBid(String userId, String courseCode, String sectionCode, double amount) {
        this.userId = userId;
        this.courseCode = courseCode;
        this.sectionCode = sectionCode;
        this.amount = amount;
    }

    public Key getKey() {
        return key;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public String getUserId() {
        return userId;
    }

    public String getAmountTwoDecimal() {
        DecimalFormat dmt = new DecimalFormat("#,##0.00");
        return dmt.format(amount);
    }
/*
    public final int compareTo(Bid other) {
        return 0;
    }

    public static class CompareAmount implements Comparator<Bid> {

        public int compare(Bid bid1, Bid bid2) {

            int value;
            if (bid1.getAmount() > bid2.getAmount()) {
                value = -1;
            } else if (bid1.getAmount() < bid2.getAmount()) {
                value = +1;
            } else {
                value = 0;
            }
            return value;
        }
    }
  */
 
}
