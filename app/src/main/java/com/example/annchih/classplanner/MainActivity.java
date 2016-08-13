package com.example.annchih.classplanner;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity{

    public static final String PREF_NAME = "MyPrefFile";
    @Override
    protected void onCreate(Bundle savedInstanceState){

        SharedPreferences prefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        /*if(prefs.getString("college_key",null)!=null && prefs.getString("major_key",null)!=null
                &&prefs.getString("year_key",null)!=null){
            Intent intent = new Intent(this,Select_classesTaken.class);
            this.startActivity(intent);
        }*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        Spinner college_spinner = (Spinner) findViewById(R.id.college_spinner);
        String college = college_spinner.getSelectedItem().toString();
        editor.putString("college_key", college);
        editor.commit();

        Spinner major_spinner = (Spinner) findViewById(R.id.major_spinner);
        String major = major_spinner.getSelectedItem().toString();
        editor.putString("major_key", major);
        Log.d("major",major);
        editor.commit();

        Spinner year_spinner = (Spinner) findViewById(R.id.year_spinner);
        String year = year_spinner.getSelectedItem().toString();
        editor.putString("year_key", year);
        editor.commit();
        Button toMain = (Button)findViewById(R.id.finishFirstStep);
        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Select_classesTaken.class);
                startActivity(intent);

            }
        });

    }




}
