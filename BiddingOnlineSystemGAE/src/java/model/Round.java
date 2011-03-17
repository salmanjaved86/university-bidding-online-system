/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author yewwei.tay.2009
 */
public class Round {

    private static int roundNo = 0; //this is hardcoded. it will be dynamically changed when admin is able to start the bid round in the future.
    private static boolean roundStart = false;

    public static int getRound() {
        return roundNo;
    }

    public static void setRound(int number) {
        roundNo = number;
    }

    public static boolean getRoundStart(){
         return roundStart;
    }

    public static void setRoundStart(boolean event){
        roundStart = event;
    }
}
