package com.example.libraryuserinformation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeScreen extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    public static final String DEFAULT="N/A";
     TextView tv1;
    String myemail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        setTitle("Home Screen");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        sharedPreferences = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        myemail = sharedPreferences.getString("email",DEFAULT);
        tv1 = (TextView) findViewById(R.id.tvv1);
        tv1.setText(myemail);

    }
    public void openProfile(View view){
        Intent i = new Intent(this,Profile.class);
        startActivity(i);
    }
    public void openNotification(View view){
        Intent i = new Intent(this,Notification.class);
        startActivity(i);

    }
    public void openBookBorrow(View view){
        Intent i = new Intent(this,BooksBorrow.class);
        startActivity(i);

    }

    public void openReturnBook(View view){
        Intent i = new Intent(this,BooksReturn.class);
        startActivity(i);

    }

    public  void logoutUser(View v){
        SharedPreferences sharedPreferences=getSharedPreferences("MyData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        String logout=DEFAULT;
        editor.putString("loginsuccess",logout);
        editor.commit();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
        finish();
    }
}
