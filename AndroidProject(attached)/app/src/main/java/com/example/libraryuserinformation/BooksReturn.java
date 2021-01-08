package com.example.libraryuserinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.libraryuserinformation.JsonReader.readJsonFromUrl;

public class BooksReturn extends AppCompatActivity {
    private TextView tbookiddata1,tissuedatedata1,treturndatedata1,treturnbooknamedata1,tbookiddata2,tissuedatedata2,treturndatedata2,treturnbooknamedata2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Books Return");
        setContentView(R.layout.activity_books_return);
        tbookiddata1 = (TextView) findViewById(R.id.bookiddata1) ;
        tissuedatedata1= (TextView) findViewById(R.id.issuedatedata1) ;
        treturndatedata1=(TextView) findViewById(R.id.returndatedata1) ;
        treturnbooknamedata1 = (TextView) findViewById(R.id.returnbooknamedata1) ;
        tbookiddata2 = (TextView) findViewById(R.id.bookiddata2) ;
        tissuedatedata2= (TextView) findViewById(R.id.issuedatedata2) ;
        treturndatedata2=(TextView) findViewById(R.id.returndatedata2) ;
        treturnbooknamedata2 = (TextView) findViewById(R.id.returnbooknamedata2) ;

        new MyTassk().execute();
    }

    class MyTassk extends AsyncTask<Void, Void, Void> {
        String bookiddata1,issuedatedata1,returndatedata1,returnbooknamedata1,bookiddata2,issuedatedata2,returndatedata2,returnbooknamedata2;
        String Host, Database, User, Password;
        int Port;
        public static final String DEFAULT="N/A";

        protected Void doInBackground(Void... voids) {

            try {
                JSONObject json = readJsonFromUrl("https://hirraa.000webhostapp.com/libraryDB.txt");
                System.out.println(json.toString());
                System.out.println(json.get("User"));
                System.out.println(json.get("Host"));
                Host = json.get("Host").toString();
                Port = Integer.parseInt(json.get("Port").toString());
                Database = json.get("Database").toString();
                User = json.get("User").toString();
                Password = json.get("Password").toString();


            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://" + Host + ":" + Port + "/" + Database, User, Password);
                SharedPreferences sharedPreferences;
                sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
                int memid = Integer.parseInt(sharedPreferences.getString("id",DEFAULT));
                Statement st = con.createStatement();
                String sql = "select * from return_table where Member_id='" + memid + "' ORDER BY DATE(Issue_Date) DESC";

                final ResultSet rs = st.executeQuery(sql);
                rs.next();
                bookiddata1 =rs.getString("Book_id");
                issuedatedata1=rs.getString("Issue_Date");
                returndatedata1 =rs.getString("Return_Date");
                returnbooknamedata1 =rs.getString("Book_Name");
                rs.next();
                bookiddata2 =rs.getString("Book_id");
                issuedatedata2 =rs.getString("Issue_Date");
                returndatedata2  =rs.getString("Return_Date");
                returnbooknamedata2=rs.getString("Book_Name");







                //   email = rs.getString("Email");


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
        protected void onPostExecute(Void aVoid) {
            tbookiddata1.setText(bookiddata1) ;
            tissuedatedata1.setText(issuedatedata1) ;
            treturndatedata1.setText(returndatedata1) ;
            treturnbooknamedata1.setText(returnbooknamedata1) ;
            tbookiddata2.setText(bookiddata2) ;
            tissuedatedata2.setText(issuedatedata2) ;
            treturndatedata2.setText(returndatedata2) ;
            treturnbooknamedata2.setText(returnbooknamedata2) ;

            super.onPostExecute(aVoid);
        }

    }
}
