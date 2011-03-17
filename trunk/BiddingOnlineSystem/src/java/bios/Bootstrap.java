package bios;

import model.*;
import DataManager.*;
import DataManager.SystemMetaDataManager;
import com.opensymphony.xwork2.ActionSupport;
import java.io.*;
import java.util.zip.*;
import java.sql.*;
import model.ConnectionManager;
import au.com.bytecode.opencsv.*;
import java.util.*;
import model.Round;

public class Bootstrap extends ActionSupport {
    // Adopted from: http://www.vaannila.com/struts-2/struts-2-example/struts-2-file-Upload-example-1.html

    /**
     * Initializing private attributes
     */
    private File zippedFile;
    private String contentType;
    private String filename;
    private List<Bid> bidList = new ArrayList<Bid>();

    /**
     * Set the zippedFile to be bootstrapped
     * @param zippedFile the zippedFile to set
     */
    public void setZippedFile(File zippedFile) {
        this.zippedFile = zippedFile;
    }

    /**
     * Set the content type
     * @param contentType the contentType to set
     */
    public void setZippedFileContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * Set the file name
     * @param filename the filename to set
     */
    public void setZippedFileFileName(String filename) {
        this.filename = filename;
    }

    /**
     * Return the zippedFile
     * @return zippedFile
     */
    public File getZippedFile() {
        return zippedFile;
    }

    /**
     * Return the content type
     * @return contentType
     */
    public String getZippedFileContentType() {
        return contentType;
    }

    /**
     * Return the file name
     * @return filename
     */
    public String getZippedFileFilename() {
        return filename;
    }
    //initialise String attribute to print bootstrap success message 
    private String test = "";
    private String[] csvReqList = {"prerequisite.csv", "bid.csv", "course_completed.csv", "student.csv", "section.csv", "course.csv"}; // to be moved to a properties file

    /**
     * Execute the bootstrap function by creating an output directory, flush the existing data,
     * unzip the zippedFile. Method also checks if there are missing files that are supposed
     * to be bootstrapped. Finally, read the csv files and write to objects and display success message. 
     * @return "SUCCESS"
     */
    public String execute() throws Exception {
        //Create a temporary directory, source: http://stackoverflow.com/questions/617414/create-a-temporary-directory-in-java
        //String outputDir = File.createTempFile("temp", Long.toString(System.nanoTime())).getPath();
        String outputDir = "outputDir";

        if (filename != null) {
            if (filename.substring(filename.length() - 4).equals(".zip")) {
                //Flush the entire database at the given point
                flushDb();

                // Calls the unZip function and receives an arraylist of all files extracted
                ArrayList<String> tempRecd = UnZip(zippedFile.toString(), outputDir);

                // Calls the method to check if all files expected were present
                ArrayList<String> missingFiles = containsAllCSVFiles(csvReqList, tempRecd);

                // if there are no missing files in the Zip, begin reading the CSV Files and writing to objects
                if (missingFiles.isEmpty()) {
                    CSV2Objects(outputDir, tempRecd);
                    test += "Congratuations! Bootstrap is successful! <br/><br/> Round 1 started!";
                    //Start round 1
                    Round.setRound(1);
                    Round.setRoundStart(true);
                    //refresh database
                    StudentDataManager.refresh();
                    for (Bid bid : bidList) {
                        BidDataManager.addBid(bid.getUserId(), bid.getCourseCode(), bid.getSectionCode(), bid.getAmount());
                    }
                    BidDataManager.refresh();
                    CourseCompletedManager.refresh();
                    CourseDataManager.refresh();
                    PrerequisiteCourseDataManager.refresh();
                    SectionDataManager.refresh();
                    SuccessfulBidDataManager.refresh();
                    //Edit System_Meta table
                    SystemMetaDataManager.editMeta("round1status", "started");
                } else {
                }
            } else if (filename.equals("bid.csv")) {
                CSV2Objects();
            } else {
                test = "Please upload either a .zip or .csv file.";
            }
        } else {
            test = "Please upload a file.";
        }
        return "success";
    }

    /**
     * Method unzips a zipfile
     * @return String test
     */
    public ArrayList<String> UnZip(String zipName, String tempCSVDirPath) {
        // Source: http://java.sun.com/developer/technicalArticles/Programming/compression/
        ArrayList<String> csvGotList = new ArrayList<String>();
        final int BUFFER = 1024;
        try {
            BufferedOutputStream bos = null;
            FileInputStream fis = new FileInputStream(zipName);
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                test += "Extracting: " + entry;
                csvGotList.add(entry.toString());
                int count;
                byte data[] = new byte[BUFFER];
                // write the files to the disk
                FileOutputStream fos = new FileOutputStream(tempCSVDirPath + entry.getName());
                bos = new BufferedOutputStream(fos, BUFFER);
                while ((count = zis.read(data, 0, BUFFER)) != -1) {
                    bos.write(data, 0, count);
                }
                bos.flush();
                bos.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return csvGotList;
    }

    /**
     * Return the test String
     * @return the String test
     */
    public String getTest() {
        return test;
    }

    /**
     * Method to check for missing csv files in the zippedFile
     * @param required the required files
     * @param received the received files
     * @return missing the missing files
     */
    public ArrayList<String> containsAllCSVFiles(String[] required, ArrayList<String> received) {
        ArrayList<String> missing = new ArrayList<String>();
        for (String req : required) {
            boolean filePresence = false;
            for (String recd : received) {
                if (req.equals(recd)) {
                    filePresence = true;
                    break;
                }
            }
            if (filePresence == false) {
                missing.add(req);
            }
        }
        return missing;
    }

    /**
     * Reads CSV files and write to objects
     * @param inputDir the input directory
     * @param CSVs the csv files
     * @return SUCCESS
     */
    public void CSV2Objects(String inputDir, ArrayList<String> CSVs) throws FileNotFoundException, IOException {
        int count = 0;
        while (count < CSVs.size()) {
            String fileName = CSVs.get(count);
            count++;
            //creating CSVReader reader, source: http://opencsv.sourceforge.net/
            CSVReader reader = new CSVReader(new FileReader(inputDir + fileName));
            String[] nextLine;
            String[] dump = reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                if (fileName.equals("student.csv")) {
                    StudentDataManager.addStudent(nextLine[0].trim(), nextLine[2].trim(), nextLine[1].trim(), nextLine[3].trim(), Double.parseDouble(nextLine[4]));
                } else if (fileName.equals("course.csv")) {
                    CourseDataManager.addCourse(nextLine[0].trim(), nextLine[1].trim(), nextLine[2].trim(), nextLine[3].trim(), nextLine[4].trim(), nextLine[5].trim(), nextLine[6].trim());
                } else if (fileName.equals("section.csv")) {
                    SectionDataManager.addSection(nextLine[0].trim(), nextLine[1].trim(), nextLine[2].trim(), nextLine[3].trim(), nextLine[4].trim(), nextLine[5].trim(), nextLine[6].trim(), Integer.parseInt(nextLine[7]));
                } else if (fileName.equals("bid.csv")) {
                    bidList.add(new Bid(nextLine[0].trim(), nextLine[2].trim(), nextLine[3].trim(), Double.parseDouble(nextLine[1]), false));
                } else if (fileName.equals("course_completed.csv")) {
                    CourseCompletedManager.addCourseCompleted(nextLine[0].trim(), nextLine[1].trim());
                } else if (fileName.equals("prerequisite.csv")) {
                    PrerequisiteCourseDataManager.addPrerequisiteCourse(nextLine[0].trim(), nextLine[1].trim());
                }
            }
        }
    }

    /**
     * Method to clear the cache
     */
    public void flushDb() {
        //Truncate student
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE STUDENT");
            pstmt.executeUpdate();
            test += "Student table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

        // Truncate bid
        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE BID");
            pstmt.executeUpdate();
            test += "Bid table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

        // Truncate course
        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE COURSE");
            pstmt.executeUpdate();
            test += "Course table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

        // Truncate course_completed
        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE COURSE_COMPLETED");
            pstmt.executeUpdate();
            test += "Course completed table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

        // Truncate prerequisite
        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE PREREQUISITE");
            pstmt.executeUpdate();
            test += "Prerequisite table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

        // Truncate section
        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE SECTION");
            pstmt.executeUpdate();
            test += "Section table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

        // Truncate successfulbids
        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE SUCCESSFULBID");
            pstmt.executeUpdate();
            test += "Successfulbid table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

        // Truncate System Meta Table
        conn = null;
        pstmt = null;
        rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("TRUNCATE TABLE SYSTEM_META");
            pstmt.executeUpdate();
            test += "SYSTEM_META table flushed. ";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }

    /*
     * This method reads the bidFile convert the values into bid objects
     */
    public void CSV2Objects() throws FileNotFoundException, IOException {
        //creating CSVReader reader, source: http://opencsv.sourceforge.net/
        CSVReader reader = new CSVReader(new FileReader(zippedFile.toString()));
        String[] nextLine;
        reader.readNext();
        while ((nextLine = reader.readNext()) != null) {
            AddBidURLAction temp = new AddBidURLAction();
            test += temp.addBid(nextLine[0].trim(), nextLine[2].trim(), nextLine[3].trim(), nextLine[1].trim());
        }
    }
}
