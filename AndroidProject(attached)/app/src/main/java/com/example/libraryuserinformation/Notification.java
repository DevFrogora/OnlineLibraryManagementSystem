package com.example.libraryuserinformation;

import androidx.appcompat.app.AppCompatActivity;

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

public class Notification extends AppCompatActivity {
    private TextView tit1,dat1,mes1,tit2,dat2,mes2,tit3,dat3,mes3,nt1,nt2,nt3,load;
    public static final String DEFAULT="N/A";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Notification");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        tit1=(TextView) findViewById(R.id.title1);
        dat1=(TextView) findViewById(R.id.date1);
        mes1=(TextView) findViewById(R.id.message1);
        nt1=(TextView) findViewById(R.id.notificationmark1);
        load=(TextView) findViewById(R.id.loadingnotification);

        nt2=(TextView) findViewById(R.id.notificationmark2);
        tit2=(TextView) findViewById(R.id.title2);
        dat2=(TextView) findViewById(R.id.date2);
        mes2=(TextView) findViewById(R.id.message2);

        nt3=(TextView) findViewById(R.id.notificationmark3);
        tit3=(TextView) findViewById(R.id.title3);
        dat3=(TextView) findViewById(R.id.date3);
        mes3=(TextView) findViewById(R.id.message3);
        new MyTask().execute();
    }
    private class MyTask extends AsyncTask<Void,Void,Void> {
        String title1,message1,date1,title2,message2,date2,title3,message3,date3;
        String Host,Database,User,Password;
        int Port;
        int count;


        @Override
        protected Void doInBackground(Void... voids) {

            try {
                JSONObject json = readJsonFromUrl("https://hirraa.000webhostapp.com/libraryDB.txt");
                System.out.println(json.toString());
                System.out.println(json.get("User"));
                System.out.println(json.get("Host"));
                Host=json.get("Host").toString();
                Port=Integer.parseInt(json.get("Port").toString());
                Database=json.get("Database").toString();
                User=json.get("User").toString();
                Password=json.get("Password").toString();




            }catch (Exception e){
                e.printStackTrace();
            }

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://"+Host+":"+Port+"/"+Database,User,Password);
                int not_id1=1;
                Statement st =con.createStatement();
                String sql ="select * from notification ";
                String query = "select count(*) from notification";
                //Executing the query
                ResultSet rscount = st.executeQuery(query);
                //Retrieving the result
                rscount.next();
                count = rscount.getInt(1);
                final ResultSet rs = st.executeQuery(sql);

                        rs.next();
                        title1 = rs.getString("Title");
                        message1 = rs.getString("Message");
                        date1 = rs.getString("Date");

                        rs.next();
                        title2  = rs.getString("Title");
                        message2  = rs.getString("Message");
                        date2  = rs.getString("Date");

                        rs.next();
                        title3  = rs.getString("Title");
                        message3  = rs.getString("Message");
                        date3  = rs.getString("Date");


                //   email = rs.getString("Email");




            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(count == 0){
               load.setText("NO Notification");
            }else {
                load.setVisibility(View.INVISIBLE);
                if (count == 1) {
                    tit1.setText(title1);
                    dat1.setText(date1);
                    mes1.setText(message1);

                    nt1.setVisibility(View.VISIBLE);
                    tit1.setVisibility(View.VISIBLE);
                    dat1.setVisibility(View.VISIBLE);
                    mes1.setVisibility(View.VISIBLE);
                }else if(count == 2){

                    tit1.setText(title1);
                    dat1.setText(date1);
                    mes1.setText(message1);

                    nt1.setVisibility(View.VISIBLE);
                    tit1.setVisibility(View.VISIBLE);
                    dat1.setVisibility(View.VISIBLE);
                    mes1.setVisibility(View.VISIBLE);

                    tit2.setText(title2);
                    dat2.setText(date2);
                    mes2.setText(message2);

                    nt2.setVisibility(View.VISIBLE);
                    tit2.setVisibility(View.VISIBLE);
                    dat2.setVisibility(View.VISIBLE);
                    mes2.setVisibility(View.VISIBLE);
                }else if(count == 3){

                    tit1.setText(title1);
                    dat1.setText(date1);
                    mes1.setText(message1);

                    nt1.setVisibility(View.VISIBLE);
                    tit1.setVisibility(View.VISIBLE);
                    dat1.setVisibility(View.VISIBLE);
                    mes1.setVisibility(View.VISIBLE);


                    tit2.setText(title2);
                    dat2.setText(date2);
                    mes2.setText(message2);

                    nt2.setVisibility(View.VISIBLE);
                    tit2.setVisibility(View.VISIBLE);
                    dat2.setVisibility(View.VISIBLE);
                    mes2.setVisibility(View.VISIBLE);


                    tit3.setText(title3);
                    dat3.setText(date3);
                    mes3.setText(message3);


                    nt3.setVisibility(View.VISIBLE);
                    tit3.setVisibility(View.VISIBLE);
                    dat3.setVisibility(View.VISIBLE);
                    mes3.setVisibility(View.VISIBLE);
                }else{

                }
            }



            super.onPostExecute(aVoid);
        }
    }
}
