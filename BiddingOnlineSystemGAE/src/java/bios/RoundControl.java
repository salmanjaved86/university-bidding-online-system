/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import DataManager.*;
import model.*;
import java.util.*;

/**
 *
 * @author yewwei.tay.2009
 */
public class RoundControl {

    private static ArrayList<Bid> allBidsList = BidDataManager.retrieveAll();

    /*
     * Call this method to clear round
     */
    public static void clearRound() {

        ArrayList<Bid> successfulBidsList = getSuccessfulBids();

        //Add each successful bid into the SQL server
        for (Bid successfulBid : successfulBidsList) {
            SuccessfulBidDataManager.addSuccessfulBid(successfulBid.getUserId(), successfulBid.getCourseCode(), successfulBid.getSectionCode(), successfulBid.getAmount());
        }

        //Makes a clone of allBidsList so as to avoid concurrent modification error
        ArrayList<Bid> cloneList = (ArrayList<Bid>) allBidsList.clone();

        //Clear all current bids after a successful round clearance.
        //By using the cloneList, we can delete the data in the SQL server and at the same time repopulate allBidsList
        for (Bid bid : cloneList) {

            int count = 0;
            for (Bid successfulBid : successfulBidsList) {

                if (bid == successfulBid) {
                    count++;
                }
            }

            //if a bid is unsuccessful, refund student
            if (count == 0) {
                Student student = StudentDataManager.getStudent(bid.getUserId());
                double bal = student.getEdollar() + bid.getAmount();
                StudentController.editStudenteDollar(student, bal);
            }

            BidDataManager.dropBid(bid.getUserId(), bid.getCourseCode());
        }
    }


    /*
     * Method to get all successful bids for a round clearance
     */
    public static ArrayList<Bid> getSuccessfulBids() {

        ArrayList<Bid> successfulBidsList = new ArrayList<Bid>();
        ArrayList<Bid> cloneList = (ArrayList<Bid>) allBidsList.clone();

        for (int i = 0; i < cloneList.size(); i++) {
            Bid aBid = cloneList.get(i);

            ArrayList<Bid> bidListForSection = new ArrayList<Bid>();

            //for loop to populate an array bidListForSection, for bids with the same courseCode & sectionCode
            for (Bid bid : allBidsList) {
                if (bid.getCourseCode().equals(aBid.getCourseCode()) && bid.getSectionCode().equals(aBid.getSectionCode())) {
                    bidListForSection.add(bid);
                    cloneList.remove(bid);
                }
            }

            //We use section size - no. of successful bids to find the vacancy
            int vacancy = SectionDataManager.getSection(bidListForSection.get(0).getCourseCode(), bidListForSection.get(0).getSectionCode()).getSize()
                    - SuccessfulBidDataManager.getSuccessfulBidsByCourseAndSection(bidListForSection.get(0).getCourseCode(), bidListForSection.get(0).getSectionCode()).size();

            //if no. of bids < vacancy, we just add it as a successful bid
            if (bidListForSection.size() < vacancy) {
                for (Bid bid : bidListForSection) {
                    successfulBidsList.add(bid);
                }

            } else {

                Collections.sort(bidListForSection, new Bid.CompareAmount()); //arrange the ArrayList<Bid> from highest to lowest bid amount

                //Find the clearing price, using the Nth bid value (where N = section.size())
                double clearingPrice = bidListForSection.get(SectionDataManager.getSection(bidListForSection.get(0).getCourseCode(), bidListForSection.get(0).getSectionCode()).getSize() - 1).getAmount();

                if (Round.getRound() == 1) {
                    int count = 0;

                    //count no. of bids with the same amount as clearing price
                    for (Bid bid : bidListForSection) {
                        if (bid.getAmount() == clearingPrice) {
                            count++;
                        }
                    }

                    Iterator iter = bidListForSection.iterator();
                    while (iter.hasNext()) {
                        Bid bid = (Bid) iter.next();

                        //remove all bids lower than clearing price
                        if (bid.getAmount() < clearingPrice) {
                            iter.remove();
                        }

                        //if more than 1 bid with the same amount as clearing price, remove all of them
                        if (count > 1) {
                            if (bid.getAmount() == clearingPrice) {
                                iter.remove();
                            }
                        }
                    }
                } else if (Round.getRound() == 2) {
                    
                    int size = SectionDataManager.getSection(bidListForSection.get(0).getCourseCode(), bidListForSection.get(0).getSectionCode()).getSize();

                    int count = 0;

                   //check if there are any bids with the same amount as the clearing price after the Nth bid.
                   //To get the size+1 bid in the Array, we use size
                    for (int k = size; k < bidListForSection.size(); k++) {
                        if (bidListForSection.get(k).getAmount() == clearingPrice) {
                            count++;
                        }
                    }

                    Iterator iter = bidListForSection.iterator();
                    while (iter.hasNext()) {
                        Bid bid = (Bid) iter.next();

                        //if there are bids after the Nth bid with bid amount same as the clearing price, remove them
                        if (count != 0) {
                            if (bid.getAmount() == clearingPrice) {
                                iter.remove();
                            }
                        }

                        //remove all bids lower than clearing price
                        if (bid.getAmount() < clearingPrice) {
                            iter.remove();
                        }
                    }
                }

                //add the successful bids into an ArrayList<Bid> for return
                for (Bid bid : bidListForSection) {
                    successfulBidsList.add(bid);
                }
            }
        }
        return successfulBidsList;
    }
}


