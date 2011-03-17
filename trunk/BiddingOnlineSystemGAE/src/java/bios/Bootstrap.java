package bios;

import JDODataManager.*;
import JDOModel.*;
import org.apache.commons.io.IOUtils;
import com.opensymphony.xwork2.ActionSupport;
import java.io.*;
import java.util.zip.*;
import java.sql.Date;
import java.sql.*;
import model.ConnectionManager;

import au.com.bytecode.opencsv.*;
import javax.jdo.Query;
import java.util.ArrayList;
import java.util.List;
import javax.jdo.PersistenceManager;
import model.Round;


/**
 *
 * @author Two-three-one team; mostly contributed by Phylis Png and Varun Arora
 */
public class Bootstrap extends ActionSupport {

    // Adopted from: http://www.vaannila.com/struts-2/struts-2-example/struts-2-file-Upload-example-1.html
    private String zippedFile;
    private String contentType;
    private String filename;
    private InputStream zippedFileStream;

    public String upload() throws  Exception {
        zippedFileStream = IOUtils.toInputStream(zippedFile,"ISO-8859-1");
        //return "input";
        return execute();
    }


    public void setZippedFile(String zippedFile) {
        this.zippedFile = zippedFile;
    }

    public void setZippedFileContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setZippedFileFileName(String filename) {
        this.filename = filename;
    }

    public String getZippedFile() {
        return zippedFile;
    }

    public String getZippedFileContentType() {
        return contentType;
    }

    public String getZippedFileFilename() {
        return filename;
    }

     public InputStream getZippedFileStream() {
        return zippedFileStream;
     }

     public void setZippedFileStream(InputStream zippedFileStream) {
        this.zippedFileStream = zippedFileStream;
     }

    private String test = "Hi";
    private String[] csvReqList = {"prerequisite.csv", "bid.csv", "course_completed.csv", "student.csv", "section.csv", "course.csv"}; // to be moved to a properties file
    

    byte myBytes[] =
{

( (1 << 2) + (1 << 3) ), // 2^2 + 2^3 = 12 -- 1100

( (1 << 4) + (1 << 2) ), // 2^4 + 2^2 = 20 -- 10100

( (1 << 2) + (1 << 0) ) // 2^2 + 2^0 = 5 -- 101

};

 
    
    ByteArrayInputStream s = new ByteArrayInputStream(myBytes);


    public String execute() throws Exception {

        String outputDir ="";

        //Flush the entire database at the given point
        flushDb();

        // Calls the unZip function and receives an arraylist of all files extracted
        ArrayList<String> tempRecd = UnZip(zippedFileStream, outputDir);

        return "success";

    }

    public ArrayList<String> UnZip(InputStream zipName, String tempCSVDirPath) {

        // Source: http://java.sun.com/developer/technicalArticles/Programming/compression/

        ArrayList<String> csvGotList = new ArrayList<String>();
        test += "entered the UnZip function with " + zipName + " as the name of the zip and " + " as the path";
        int counter = 0;

        final int BUFFER = 1024;
        try {
            //test += "lets take a crap";
            BufferedOutputStream bos = null;
            //test += "what about now";
            //test += "and now?";
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(zipName));
            //test += "and lastly, now?";
            ZipEntry entry;
            //test += "I came until this point" + zis.toString() + "and this is the original" + zippedFileStream.toString();
            
            
            while ((entry = zis.getNextEntry()) != null) {
                //System.out.println("Extracting: " + entry);
                test += "Extracting: " + entry;
                String fileName = entry.toString();
                csvGotList.add(fileName);
                int count;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                byte data[] = new byte[BUFFER];

                /*byte[] something = IOUtils.toByteArray(entry.);*/
                // write the files to the disk
                //FileOutputStream fos = new FileOutputStream(tempCSVDirPath + entry.getName());
                //bos = new BufferedOutputStream(fos, BUFFER);
                //s = new ByteArrayInputStream(data);
                
                while ((count = zis.read(data, 0, BUFFER)) != -1) {
                    //s.read(data, 0, count);
                    /*test += "output looks like " + output.toString();*/
                    //test += "came here for entry " + fileName;
                    output.write(data, 0, count);
                    
                }
                ByteArrayInputStream pis = new ByteArrayInputStream(output.toByteArray());
                BufferedReader cool = new BufferedReader(new InputStreamReader(pis));
                CSVReader reader = new CSVReader(cool);
                    String[] nextLine;
                    String[] dump = reader.readNext();

                //test += "this is the first array item: " + reader.readNext()[0].toString();
                //test += "this is the second array item: " + reader.readNext()[1].toString();
                while ((nextLine = reader.readNext()) != null) {

                if (counter == 3) {
                    test += "I was here";
                    StudentDataManager.addStudent(nextLine[0], nextLine[2], nextLine[1], nextLine[3], Double.parseDouble(nextLine[4]));
                } else if (counter == 5) {
                    //test += "She was here";
                    CourseDataManager.addCourse(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5], nextLine[6]);
                } else if (counter == 4) {
                    //test += "We both stared at each other";
                    SectionDataManager.addSection(nextLine[0], nextLine[1], nextLine[2], nextLine[3], nextLine[4], nextLine[5], nextLine[6], Integer.parseInt(nextLine[7]));
                } else if (counter == 1) {
                    //test += "She called me closer to her";
                    BidDataManager.addToDataStore(nextLine[0], nextLine[2], nextLine[3], Double.parseDouble(nextLine[1]));
                } else if (counter == 2) {
                    //test += "We kissed.. and...";
                    CourseCompletedManager.addCourseCompleted(nextLine[0], nextLine[1]);
                } else if (counter == 0) {
                    //test += "Today after two years, I am a proud father";
                    PrerequisiteCourseDataManager.addPrerequisiteCourse(nextLine[0], nextLine[1]);
                }
                



                    
                }
                counter++;
                output.flush();
                cool.close();
                pis.close();
                //bos.flush();
                //bos.close();
                //output.close();

            }
            test += "csvgotlist item 1" + csvGotList.get(0);
            test += "csvgotlist item 2" + csvGotList.get(1);
            test += "csvgotlist item 3" + csvGotList.get(2);
            test += "csvgotlist item 4" + csvGotList.get(3);
            test += "csvgotlist item 5" + csvGotList.get(4);
            test += "csvgotlist item 6" + csvGotList.get(5);
           //output.
            //test += output.toString();
            zis.close();
            zipName.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
            StudentDataManager.refresh();
            CourseDataManager.refresh();
            Round.setRound(1);
            Round.setRoundStart(true);
            Round.setRound(1);
            BidDataManager.refresh();
            CourseCompletedManager.refresh();

            PrerequisiteCourseDataManager.refresh();
            SectionDataManager.refresh();
            return csvGotList;
    }

    public String getTest() {
        return test;
    }

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

    public void flushDb(){

        //Truncate student

        PersistenceManager pm = PMF.get().getPersistenceManager();
        Query query = pm.newQuery("select from " + Student.class.getName());
        List<Student> objs = (List<Student>) query.execute();
        pm.deletePersistentAll(objs);
        test += "Student table flushed. ";

        // Truncate bid

        Query query2 = pm.newQuery("select from " + Bid.class.getName());
        List<Bid> objs2 = (List<Bid>) query2.execute();
        pm.deletePersistentAll(objs2);
        test += "Bid table flushed. ";

        // Truncate course

        Query query3 = pm.newQuery("select from " + Course.class.getName());
        List<Course> objs3 = (List<Course>) query3.execute();
        pm.deletePersistentAll(objs3);
        test += "Course table flushed. ";

        // Truncate course_completed

        Query query4 = pm.newQuery("select from " + CourseCompleted.class.getName());
        List<CourseCompleted> objs4 = (List<CourseCompleted>) query4.execute();
        pm.deletePersistentAll(objs4);
        test += "Course completed table flushed. ";

        // Truncate prerequisite

        Query query5 = pm.newQuery("select from " + PrerequisiteCourse.class.getName());
        List<PrerequisiteCourse> objs5 = (List<PrerequisiteCourse>) query5.execute();
        pm.deletePersistentAll(objs5);
        test += "Prerequisite table flushed. ";

        // Truncate section

        Query query6 = pm.newQuery("select from " + Section.class.getName());
        List<Section> objs6 = (List<Section>) query6.execute();
        pm.deletePersistentAll(objs6);
        test += "Section table flushed. ";

    }


}
