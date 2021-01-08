package com.example.libraryuserinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.json.JSONObject;

import static com.example.libraryuserinformation.JsonReader.readJsonFromUrl;

public class Profile extends AppCompatActivity {
    public static final String DEFAULT="N/A";
    private TextView mid,mName,mEmailtv,mdepartment,mjoinyear,msemester,mtype;
    SharedPreferences sharedPreferences;
    String myemail="";
    SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
         myemail = sharedPreferences.getString("email",DEFAULT);
        setTitle("Profile");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        // Database connDatabase= new Database();
        mid = (TextView) findViewById(R.id.tv2);
        mName = (TextView) findViewById(R.id.tv4);
        mEmailtv =(TextView) findViewById(R.id.tv6);
        mdepartment = (TextView) findViewById(R.id.tv8);
        mjoinyear = (TextView) findViewById(R.id.tv10);
        msemester = (TextView) findViewById(R.id.tv12);
        mtype = (TextView) findViewById(R.id.tv14);
        //  Name.setText("what");
        new  MyTask().execute();
      //  Toast.makeText(getApplicationContext(),"eroor 1",Toast.LENGTH_LONG).show();


    }



    private class MyTask extends AsyncTask<Void,Void,Void>{
        String name,email,id,department,joinyear,sem,type;
        String Host,Database,User,Password;
        int Port;



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


           // sharedPreferences.getString("email",DEFAULT);



            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://"+Host+":"+Port+"/"+Database,User,Password);

                Statement st =con.createStatement();
                String sql ="select * from member where Email='"+myemail+"'";

                final ResultSet rs = st.executeQuery(sql);
                rs.next();
                name  = rs.getString("Name");
                id  = rs.getString("Member_id");
                email  = rs.getString("Email");
                department   = rs.getString("Department");
                joinyear  = rs.getString("Join_Year");
                sem  = rs.getString("Semester");
                type  = rs.getString("Member_Type");

                 //   email = rs.getString("Email");




            }catch (Exception e){
               e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            mid.setText(id);
            mName.setText(name);
            mEmailtv.setText(email);
            mdepartment.setText(department);
            mjoinyear.setText(joinyear);
            msemester.setText(sem);
            mtype.setText(type);

            super.onPostExecute(result);
        }
    }




}
