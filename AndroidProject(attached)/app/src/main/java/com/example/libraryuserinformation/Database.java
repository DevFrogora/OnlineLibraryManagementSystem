package com.example.libraryuserinformation;


import android.os.AsyncTask;

import java.sql.*;

public class Database extends AsyncTask<Void,Void,Void> {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    public Database(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://sql12.freemysqlhosting.net:3306/sql12342303","sql12342303","ZuGTs8VGVj");
            st = con.createStatement();
        } catch (Exception e) {
           e.printStackTrace();
        }

    }
    protected Void doInBackground(Void... arg0) {

        try {

            String sql="select * from Member";
            rs= st.executeQuery(sql);

            while(rs.next()){
                String name = rs.getString("Name");
                String email = rs.getString("Email");




            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
    public void getData(){



    }
}
