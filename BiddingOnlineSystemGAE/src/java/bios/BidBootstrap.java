/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bios;

import com.opensymphony.xwork2.ActionSupport;
import DataManager.*;
import java.io.*;
import au.com.bytecode.opencsv.*;

/**
 *
 * @author Administrator
 */
public class BidBootstrap extends ActionSupport {
    
    // Adopted from: http://www.vaannila.com/struts-2/struts-2-example/struts-2-file-Upload-example-1.html
    private File bidFile;

    /**
     * @return the bidFile
     */
    public File getBidFile() {
        return bidFile;
    }

    /**
     * @param bidFile the bidFile to set
     */
    public void setBidFile(File bidFile) {
        this.bidFile = bidFile;
    }


    public String execute() throws Exception{

            CSV2Objects();
            BidDataManager.refresh();
            return "success";
    }

    public void CSV2Objects() throws FileNotFoundException, IOException {

            //creating CSVReader reader, source: http://opencsv.sourceforge.net/
            CSVReader reader = new CSVReader(new FileReader(bidFile.toString()));
            String[] nextLine;
            String[] dump = reader.readNext();

            while ((nextLine = reader.readNext()) != null) {
                    //test += "She called me closer to her";
                    BidDataManager.addBid(nextLine[0], nextLine[2], nextLine[3], Double.parseDouble(nextLine[1]));
            }


        }


    }

