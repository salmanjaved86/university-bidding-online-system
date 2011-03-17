package bios;
import model.*;
import com.opensymphony.xwork2.ActionSupport;

public class RoundAction extends ActionSupport{
    /**
     * Initializing private attributes
     */
    private int choice;
    private String roundAction;
    private String message;

    /**
     * Return the message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the message
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Return the roundAction
     * @return roundAction
     */
    public String getRoundAction() {
        return roundAction;
    }

    /**
     * Set the roundAction
     * @param roundAction the roundAction to set
     */
    public void setRoundAction(String roundAction) {
        this.roundAction = roundAction;
    }

    /**
     * This method checks the action of the user selection at the admin console and performs
     * the action accordingly
     * @return "success"
     */
    public String execute() {
        choice = Integer.parseInt(roundAction);
        if (choice==2) {
           if(Round.getRound()==0){
               //prompts user to bootstrap
               message = "Please boostrap to start round 1 first.";
            //round 1 is started but not stopped
           }else if (Round.getRoundStart() && Round.getRound()!=2) {
               //prompts user to stop round
               message = "Please stop round first.";
            }else if(Round.getRoundStart()){
                message = "Round 2 has already been started.";
            }else if(!Round.getRoundStart() && Round.getRound()==2){
                message = "Round 2 been cleared previously. Please bootstrap to return to round 1.";
            }
            else {
               //set the round
                Round.setRound(choice);
                //start the round
                Round.setRoundStart(true);
                //message
                message = "Round " + choice + " started.";
            }
        }

        if (choice == 3 && !Round.getRoundStart()) {
            message = "Round has not been started yet!";
        } else if (choice == 3 && Round.getRoundStart()) {
            Round.setRoundStart(false);
            RoundControl.clearRound();
            message = "Bidding round stopped & cleared.";
        }
        return "SUCCESS";
    }
}
