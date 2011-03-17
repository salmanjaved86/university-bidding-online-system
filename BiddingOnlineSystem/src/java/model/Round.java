
package model;

public class Round {

    private static int roundNo = 0; //this is hardcoded. it will be dynamically changed when admin is able to start the bid round in the future.
    private static boolean roundStart = false;

    /**
     * retrieves the current round
     * @return the int value
     */
    public static int getRound() {
        return roundNo;
    }

    /**
     * sets the round
     * @param number the number to set into round
     */
    public static void setRound(int number) {
        roundNo = number;
    }

    /**
     * retrieve the status of round start
     * @return the boolean of round start
     */
    public static boolean getRoundStart(){
         return roundStart;
    }

    /**
     * sets the round to start
     * @param event the boolean to start round
     */
    public static void setRoundStart(boolean event){
        roundStart = event;
    }
}
