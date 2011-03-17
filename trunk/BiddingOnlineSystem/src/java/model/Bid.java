package model;

import java.util.Comparator;
import java.text.*;

public class Bid implements Comparable<Bid> {

    private String userId;
    private String courseCode;
    private String sectionCode;
    private double amount;
    private boolean bidStatus;

    /**
     * retrieves the status of the bid
     * @return the boolean of the status
     */
    public boolean getBidStatus(){
        return bidStatus;
    }

    /**
     * Sets the status of the bid
     * @param status the boolean status of the bid
     */
    public void setBidStatus(boolean status){
        this.bidStatus = status;
    }

    /**
     * default constructor
     * @param bid the bid object
     */
    public Bid(Bid bid){
        this.userId = bid.getUserId();
        this.courseCode=bid.getCourseCode();
        this.sectionCode=bid.getSectionCode();
        this.amount=bid.getAmount();
    }

    /**
     * initialize the bid object
     * @param userId the student's user id
     * @param courseCode the course code
     * @param sectionCode the section code
     * @param amount the amount bid for
     * @param status the status of the bid
     */
    public Bid(String userId, String courseCode, String sectionCode, double amount, boolean status) {
        this.userId = userId;
        this.courseCode = courseCode;
        this.sectionCode = sectionCode;
        this.amount = amount;
        this.bidStatus = status;
    }

    /**
     * Sets the amount of the bid
     * @param amount the amount to bid fir
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * sets the course code
     * @param courseCode the course code to bid for
     */
    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    /**
     * sets the section code
     * @param sectionCode the section code to bid for
     */
    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    /**
     * sets the user id
     * @param userId the user id of the student
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * retrieve the amount of the bid
     * @return the double amount of the bid
     */
    public double getAmount() {
        return amount;
    }

    /**
     * retrieves the amount in 2 decimal places
     * @return the amount in string
     */
    public String getAmountTwoDecimal() {
        DecimalFormat dmt = new DecimalFormat("#,##0.00");
        return dmt.format(amount);
    }

    /**
     * retrieve the course code
     * @return the course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * retrieve the section code
     * @return the section code
     */
    public String getSectionCode() {
        return sectionCode;
    }

    /**
     * retrieve the user id
     * @return the user id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * compares bid with another bid
     * @param other the bid object
     * @return int 0
     */
    public final int compareTo(Bid other) {
        return 0;
    }

    public static class CompareAmount implements Comparator<Bid> {

        /**
         * compares between 2 bids
         * @param bid1 the bid object
         * @param bid2 the bid object
         * @return the int value
         */
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

    public static class CompareAmountHigh implements Comparator<Bid> {

        /**
         * compares between 2 bids
         * @param bid1 the bid object
         * @param bid2 the bid object
         * @return the int value
         */
        public int compare(Bid bid1, Bid bid2) {

            int value;
            if (bid1.getAmount() > bid2.getAmount()) {
                value = 1;
            } else if (bid1.getAmount() < bid2.getAmount()) {
                value = -1;
            } else {
                value = 0;
            }
            return value;
        }
    }
}
