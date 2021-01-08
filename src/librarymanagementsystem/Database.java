/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagementsystem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author root
 */
public class Database {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private boolean status = false;

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public Database() throws IOException {
       

        try {
            Class.forName("com.mysql.jdbc.Driver");
            //   con = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12342303", "sql12342303", "ZuGTs8VGVj");
            con = DriverManager.getConnection("jdbc:mysql://"+Utils.HOST+":"+Utils.PORT+"/"+Utils.DATABASE,Utils.USER , Utils.DBPASSWORD);
            st = con.createStatement();
            
            status = true; // connection established
            
        } catch (Exception e) {
            System.err.println("Error is : " + e);
        }

    }

    int executeUpdate(String s) {
        int r = 0;
        try {
            //  st = con.createStatement();
            r = st.executeUpdate(s);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return r;

    }

    void execute(String s) {

        try {
            //st = con.createStatement();
            st.execute(s);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    ResultSet executeQuery(String s) {
        try {
            //  st = con.createStatement();
            rs = st.executeQuery(s);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return rs;
    }

    Boolean status() {

        Boolean i = status;
        if (i) {
            //  System.err.println(" connection established");
            return i;
        }
        return i;
    }

    void connClose() {
        try {
            this.con.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
      public static void main(String[] args) throws IOException, JSONException {
          Database db = new Database();
          
    
  }



}

class TableModelJTable {

    static DefaultTableModel db;
// it is not predifines function

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {

        try {
            ResultSetMetaData metaData = rs.getMetaData();

            // names of columns
            Vector<String> columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            // data of the table
            Vector<Vector<Object>> data = new Vector<Vector<Object>>();
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }

            db = new DefaultTableModel(data, columnNames);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return db;
    }

}

