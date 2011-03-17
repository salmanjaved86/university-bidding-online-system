package bios;
import com.opensymphony.xwork2.ActionSupport;
import DataManager.*;
import java.io.*;

public class BidBootstrap extends ActionSupport {
    // Adopted from: http://www.vaannila.com/struts-2/struts-2-example/struts-2-file-Upload-example-1.html
    private File bidFile;
    public String test;

    /**
     * Return the bidFile
     * @return bidFile
     */
    public File getBidFile() {
        return bidFile;
    }

    /**
     * Set the bid file to be bootstrapped
     * @param bidFile the bidFile to set
     */
    public void setBidFile(File bidFile) {
        this.bidFile = bidFile;
    }

    /**
     * This method executes CSV2Objects()
     * @return "success"
     */
    public String execute() throws Exception {
        BidDataManager.refresh();
        return "success";
    }
}

