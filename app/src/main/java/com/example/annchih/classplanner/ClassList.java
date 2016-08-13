package com.example.annchih.classplanner;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnnChih on 11/21/15.
 */
public class ClassList extends Activity {
    List<ParseObject> ob;
    ListViewAdapter adapter;
    ProgressDialog mProgressDialog;
    private List<ClassData> list_of_classes = null;
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classlistview);

        new RemoteDataTask().execute();
    }

    // RemoteDataTask AsyncTask
    private class RemoteDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ClassList.this);
            // Set progressdialog message
            //mProgressDialog.setTitle("Class Planner");
            //mProgressDialog.setMessage("Loading...");
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
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>(
                        "classplanner");
                ob = query.find();
                for (ParseObject classplanner : ob) {
                    ClassData new_class = new ClassData();
                    new_class.set_class_id_2((String) classplanner.get("class_id"));
                    new_class.set_class_name_2((String) classplanner.get("class_name"));
                    new_class.set_description((String) classplanner.get("description"));
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
            listview = (ListView) findViewById(R.id.class_list);
            // Pass the results into ListViewAdapter.java
            adapter = new ListViewAdapter(ClassList.this, list_of_classes);
            // Binds the Adapter to the ListView
            listview.setAdapter(adapter);
            // Close the progressdialog

            mProgressDialog.dismiss();
        }
    }

}
