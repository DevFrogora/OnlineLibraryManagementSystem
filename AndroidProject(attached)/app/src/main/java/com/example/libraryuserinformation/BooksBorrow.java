package com.example.libraryuserinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.libraryuserinformation.JsonReader.readJsonFromUrl;

public class BooksBorrow extends AppCompatActivity {
    public static final String DEFAULT="N/A";
    private TextView tborrowiddata1, tborrowdatedata1, tbkname1data, tborrowiddata2, tborrowdatedata2, tbkname2data,load,bktitle1,bktitle2;
    private TextView bkidborrow1,bkidborrow2,borrowdatetxt1,borrowdatetxt2,borrowiddata1,borrowiddata2;
    private TextView bkname1text,bkname2text,bkname1data,bkname2data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Books Borrow");
        setContentView(R.layout.activity_books_borrow);

        tborrowiddata1 = (TextView) findViewById(R.id.borrowiddata1) ;
        tborrowdatedata1= (TextView) findViewById(R.id.borrowdatedata1) ;
        tbkname1data=(TextView) findViewById(R.id.bkname1data) ;
        borrowdatetxt1=(TextView) findViewById(R.id.borrowdatetxt1);
        bkname1text=(TextView) findViewById(R.id.bkname1text);
        bktitle1=(TextView) findViewById(R.id.borrowtitle1);
        bkidborrow1=(TextView) findViewById(R.id.bkidborrow1);




        tborrowiddata2 = (TextView) findViewById(R.id.borrowiddata2) ;
        tborrowdatedata2= (TextView) findViewById(R.id.borrowdatedata2) ;
        tbkname2data=(TextView) findViewById(R.id.bkname2data) ;
        bktitle2=(TextView) findViewById(R.id.borrowtitle2);
        bkidborrow2=(TextView) findViewById(R.id.bkidborrow2);
        borrowdatetxt2=(TextView) findViewById(R.id.borrowdatetxt2);
        bkname2text=(TextView) findViewById(R.id.bkname2text);


        load=(TextView) findViewById(R.id.loadingbookborrow);

        new MyTassk().execute();
    }


    class MyTassk extends AsyncTask<Void, Void, Void> {
        String borrowiddata1, borrowdatedata1, bkname1data, borrowiddata2, borrowdatedata2, bkname2data;
        String Host, Database, User, Password;
        int Port;
        int count;

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
                String sql = "select * from issue where Member_id='" + memid + "'";
                String query = "select count(*) from issue where Member_id='" + memid + "'";
                //Executing the query
                ResultSet rscount = st.executeQuery(query);
                //Retrieving the result
                rscount.next();
                count = rscount.getInt(1);

                final ResultSet rs = st.executeQuery(sql);
                rs.next();
                borrowiddata1 = rs.getString("Book_id");
                borrowdatedata1 = rs.getString("Issue_Date");
                bkname1data = rs.getString("Book_Name");
                rs.next();
                borrowiddata2 = rs.getString("Book_id");
                borrowdatedata2 = rs.getString("Issue_Date");
                bkname2data = rs.getString("Book_Name");




                //   email = rs.getString("Email");


            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }
        protected void onPostExecute(Void aVoid) {
            if(count == 0){
                load.setText("NO BookBorrow");
            }else {
                load.setVisibility(View.INVISIBLE);
                if (count == 1) {
                    tborrowiddata1.setText(borrowiddata1);
                    tborrowdatedata1.setText(borrowdatedata1);
                    tbkname1data.setText(bkname1data);

                    tborrowiddata1.setVisibility(View.VISIBLE);
                    tborrowdatedata1.setVisibility(View.VISIBLE);
                    tbkname1data.setVisibility(View.VISIBLE);
                    borrowdatetxt1.setVisibility(View.VISIBLE);
                    bkname1text.setVisibility(View.VISIBLE);
                    bktitle1.setVisibility(View.VISIBLE);
                    bkidborrow1.setVisibility(View.VISIBLE);
                }else if(count == 2){

                    tborrowiddata1.setText(borrowiddata1);
                    tborrowdatedata1.setText(borrowdatedata1);
                    tbkname1data.setText(bkname1data);

                    tborrowiddata1.setVisibility(View.VISIBLE);
                    tborrowdatedata1.setVisibility(View.VISIBLE);
                    tbkname1data.setVisibility(View.VISIBLE);
                    borrowdatetxt1.setVisibility(View.VISIBLE);
                    bkname1text.setVisibility(View.VISIBLE);
                    bktitle1.setVisibility(View.VISIBLE);
                    bkidborrow1.setVisibility(View.VISIBLE);

                    tborrowiddata2.setText(borrowiddata2);
                    tborrowdatedata2.setText(borrowdatedata2);
                    tbkname2data.setText(bkname2data);

                    tborrowiddata2.setVisibility(View.VISIBLE);
                    tborrowdatedata2.setVisibility(View.VISIBLE);
                    tbkname2data.setVisibility(View.VISIBLE);
                    borrowdatetxt2.setVisibility(View.VISIBLE);
                    bkname2text.setVisibility(View.VISIBLE);
                    bktitle2.setVisibility(View.VISIBLE);
                    bkidborrow2.setVisibility(View.VISIBLE);
                }else{

                }
            }


            super.onPostExecute(aVoid);
        }

    }
}
