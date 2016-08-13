package com.example.annchih.classplanner;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ListView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnnChih on 11/7/15.
 */
public class Select_classesTaken extends Activity {
    List<ParseObject> ob;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    private List<ClassData> list_of_classes = null;
    ListView listview;
    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("classplanner");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_class);

        new RemoteDataTask().execute();
    }
    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(Select_classesTaken.this);
            // Set progressdialog message
            mProgressDialog.setTitle("Class Planner");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create the array
            //Log.d("I am here!","got it");
            list_of_classes = new ArrayList<ClassData>();

            try {
                // Locate the class table named "Country" in Parse.com
                ob = query.find();
                for (ParseObject classplanner : ob) {
                    ClassData new_class = new ClassData();
                    new_class.set_class_id_2((String) classplanner.get("class_id"));
                    new_class.set_class_name_2((String) classplanner.get("class_name"));
                    new_class.set_taken((boolean)classplanner.get("taken"));
                    list_of_classes.add(new_class);

                }
            }  catch (com.parse.ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            // Locate the listview in listview_main.xml
            listview = (ListView) findViewById(R.id.list);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(Select_classesTaken.this,
                    list_of_classes);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog

            mProgressDialog.dismiss();
        }
    }
    public void toQuarterListView(View view) {
        Intent intent = new Intent(this, QuarterListView.class);
        startActivity(intent);
    }

    public void taken_this_class(View view) throws ParseException{
        /*CheckBox cb = (CheckBox)view;
        String class_name = cb.getText().toString();
        if(((CheckBox)view).isChecked())
            //class_name = ((CheckBox) view).hol

        Log.d("This class is ",class_name);
        query.whereEqualTo("class_id",class_name);
        //ParseObject taken_class = query.get("class_id");
        //taken_class.put("taken",true);
        //taken_class.save();*/
    }
}
