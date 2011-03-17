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
 * @author yewwei.tay.2009
 */
@PersistenceCapable
public class Round {


    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private static int roundNo = 0; //this is hardcoded. it will be dynamically changed when admin is able to start the bid round in the future.

    @Persistent
    private static boolean roundStart = false;

    public Key getKey() {
        return key;
    }


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
