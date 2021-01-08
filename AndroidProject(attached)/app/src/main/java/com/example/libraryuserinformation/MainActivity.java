package com.example.libraryuserinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.example.libraryuserinformation.JsonReader.readJsonFromUrl;

public class MainActivity extends AppCompatActivity {
    public static final String DEFAULT="N/A";

    String password="";
    String email="";
    String globalemail;
    String yes="yes";
    String no="no";
    String reg_member=no;
    String reg_admin=no;
    String loginsuccess=DEFAULT;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Login Screen");
         sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
         editor = sharedPreferences.edit();
        email = sharedPreferences.getString("email",DEFAULT);
        password = sharedPreferences.getString("password",DEFAULT);
        loginsuccess =  sharedPreferences.getString("loginsuccess",DEFAULT);
        super.onCreate(savedInstanceState);
        if(loginsuccess.equals(DEFAULT)){

            setContentView(R.layout.activity_main);
        }else{
            Intent i = new Intent(this,HomeScreen.class);
            startActivity(i);
        }

    }
    public void generatePassword(View v){
        et1 = (EditText) findViewById(R.id.et1);
        globalemail = et1.getText().toString();
      //  SharedPreferences sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
      //  SharedPreferences.Editor editor = sharedPreferences.edit(); // it pass the reference of the ediotr object
        new MyEmailTask().execute();
       /* if(reg_member.equals(yes)) {
            editor.putString("email", et1.getText().toString());

            String randPassword = RandomString.getAlphaNumericString(5);
            editor.putString("password", randPassword);
            // email = sharedPreferences.getString("email",DEFAULT);
            //  password = sharedPreferences.getString("password",DEFAULT);
            editor.commit(); //to save the file


            String mailrec = et1.getText().toString().trim();
            JavaMailAPI javaMailAPI = new JavaMailAPI(this, mailrec, "Library User Password", "Your Password is :  " + randPassword);
            javaMailAPI.execute();

            email = sharedPreferences.getString("email", DEFAULT);
            password = sharedPreferences.getString("password", DEFAULT);

            Toast.makeText(this, "Password sended to Email: " + et1.getText().toString(), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Unregistered Email: " + et1.getText().toString(), Toast.LENGTH_LONG).show();

        }

        */
    }

    public void loginUser(View v){
        EditText et1 = (EditText) findViewById(R.id.et1);
        EditText et2= (EditText) findViewById(R.id.et2);
        email = sharedPreferences.getString("email",DEFAULT);
        password = sharedPreferences.getString("password",DEFAULT);


        if(email.equals(DEFAULT) || password.equals(DEFAULT)) {

          //  Toast.makeText(this, "sorry no data found " + et1.getText().toString(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, "email:  " + email+" password :   "+password, Toast.LENGTH_LONG).show();
        }else{

            if (email.equals(et1.getText().toString().trim())) {
                if (password.equals(et2.getText().toString().trim())) {
                     loginsuccess="True";
                    editor.putString("loginsuccess",loginsuccess);
                    editor.commit();

                    Toast.makeText(this, "Login successfull to : " + et1.getText().toString(), Toast.LENGTH_LONG).show();
                    Intent i = new Intent(this,HomeScreen.class);
                    startActivity(i);

                }else{
                    Toast.makeText(this,  " password :   "+password, Toast.LENGTH_LONG).show();
                //    Toast.makeText(this, "password not matched " + et1.getText().toString(), Toast.LENGTH_LONG).show();
                }


            }else{
                Toast.makeText(this, "email:  " + email, Toast.LENGTH_LONG).show();
                //Toast.makeText(this, "email not matched " + et1.getText().toString(), Toast.LENGTH_LONG).show();

            }


        }


    }


    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }

    private class MyEmailTask extends AsyncTask<Void,Void,Void> {

        String Host,Database,User,Password;
        int Port;
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
                EditText et1 = (EditText) findViewById(R.id.et1);



                String sql ="select * from member where Email='"+globalemail+"'";

                final ResultSet rs = st.executeQuery(sql);
                String adminsql ="select * from admin where Email='"+globalemail+"'";


                if(rs.next()){
                    reg_member=yes;
                   String id= rs.getString(1);
                    editor.putString("id",id);
                    editor.commit();

                }
                final ResultSet rsadm = st.executeQuery(adminsql);
                if(rsadm.next()){
                    String id= rsadm.getString(1);
                    editor.putString("id",id);
                    editor.commit();
                    reg_admin=yes;

                }





                //   email = rs.getString("Email");




            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }
        protected void onPostExecute(Void aVoid) {
            if(reg_member.equals(yes) || reg_admin.equals(yes)) {
                reg_admin=no;
                reg_member=no;
                editor.putString("email", et1.getText().toString());

                String randPassword = RandomString.getAlphaNumericString(5);
                editor.putString("password", randPassword);
                // email = sharedPreferences.getString("email",DEFAULT);
                //  password = sharedPreferences.getString("password",DEFAULT);
                editor.commit(); //to save the file


                String mailrec = et1.getText().toString().trim();
                JavaMailAPI javaMailAPI = new JavaMailAPI(MainActivity.this, mailrec, "Library User Password", "Your Password is :  " + randPassword);
                javaMailAPI.execute();

                email = sharedPreferences.getString("email", DEFAULT);
                password = sharedPreferences.getString("password", DEFAULT);

                Toast.makeText(MainActivity.this, "Password sended to Email: " + et1.getText().toString(), Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this, "Unregistered Email: " + et1.getText().toString(), Toast.LENGTH_LONG).show();

            }

            super.onPostExecute(aVoid);
        }
    }


}

 class RandomString{

     static String getAlphaNumericString(int n)
     {

         // chose a Character random from this String
         String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                 + "0123456789"
                 + "abcdefghijklmnopqrstuvxyz";

         // create StringBuffer size of AlphaNumericString
         StringBuilder sb = new StringBuilder(n);

         for (int i = 0; i < n; i++) {

             // generate a random number between
             // 0 to AlphaNumericString variable length
             int index
                     = (int)(AlphaNumericString.length()
                     * Math.random());

             // add Character one by one in end of sb
             sb.append(AlphaNumericString
                     .charAt(index));
         }

         return sb.toString();
     }


}
