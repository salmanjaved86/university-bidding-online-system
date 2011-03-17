/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package DataManager;

import model.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Administrator
 */
public class SystemMetaDataManager {

    private static ArrayList<Meta> metaList = new ArrayList<Meta>();

    public static void refresh(){
        metaList.clear();
        readFromDataStore();
    }

    private static void readFromDataStore(){

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("SELECT * FROM SYSTEM_META");
            rs = (ResultSet) pstmt.executeQuery();

            while (rs.next()) {
                String property = rs.getString(1);
                String value = rs.getString(2);
                Meta Meta = new Meta(property, value);

                metaList.add(Meta);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }
    }

    public static ArrayList<Meta> retrieveAll() {
        return metaList;
    }

    public static Meta getMeta(String property) {

        for(Meta meta: metaList){
            if(meta.getProperty().equals(property)){
                return meta;
            }
        }
        return null;
    }


      public static void editMeta(String property, String value) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = (Connection) ConnectionManager.getConnection();
            pstmt = (PreparedStatement) conn.prepareStatement("UPDATE SYSTEM_META SET VALUE = '" + value + "' WHERE PROPERTY = '" + property + "'");
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, pstmt, rs);
        }

    }

}
