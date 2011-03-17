/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bios;

import java.util.*;
import model.*;
import DataManager.*;
import com.opensymphony.xwork2.ActionSupport;

/**
 *
 * @author yewwei.tay.2009
 */
public class RoundAction extends ActionSupport{

    private int choice;
    private String roundAction;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRoundAction() {
        return roundAction;
    }

    public void setRoundAction(String roundAction) {
        this.roundAction = roundAction;
    }

    public String execute() {
        choice = Integer.parseInt(roundAction);

        if (choice == Round.getRound()) {
          message = "Round has already started!";
        } else if (choice != Round.getRound() && choice != 3) {

            if (Round.getRoundStart()) {
                message = "Please stop round first.";
            } else {
                Round.setRound(choice);
                Round.setRoundStart(true);
                message = "Round " + choice + " started.";
            }

        }

        if (choice == 3 && !Round.getRoundStart()) {
            System.out.println("4");
            message = "Round has not been started yet!";
        } else if (choice == 3 && Round.getRoundStart()) {
            Round.setRoundStart(false);
            RoundControl.clearRound();
            message = "Round stopped.";
        }

        return "SUCCESS";
    }
}
