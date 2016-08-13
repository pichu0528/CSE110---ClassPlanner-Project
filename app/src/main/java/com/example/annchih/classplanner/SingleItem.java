package com.example.annchih.classplanner;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


public class SingleItem extends Activity {
    // Declare Variables
    TextView txtid;
    TextView txttitle;
    TextView txtdescription;
    String id;
    String title;
    String description;
    private LocalPlanner localPlanner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemview);
        // Retrieve data from MainActivity on item click event
        Intent i = getIntent();
        // Get the results of rank
        id = i.getStringExtra("id");
        // Get the results of country
        title = i.getStringExtra("title");
        // Get the results of population
        description = i.getStringExtra("description");

        // Locate the TextViews in singleitemview.xml
        txtid = (TextView) findViewById(R.id.id);
        txttitle = (TextView) findViewById(R.id.title);
        txtdescription = (TextView) findViewById(R.id.description);

        // Load the results into the TextViews
        txtid.setText(id);
        txttitle.setText(title);
        txtdescription.setText(description);
    }
    public void toQuarter(View view) {
        Intent i = new Intent(this, Calender_Activity.class);
        localPlanner = new LocalPlanner();
        localPlanner.setID(txtid.getText().toString());
        localPlanner.setTitle(txttitle.getText().toString());
        localPlanner.setDraft(true);
        localPlanner.setAuthor(ParseUser.getCurrentUser());
        localPlanner.pinInBackground("Class Planned",
                new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (isFinishing()) {
                            return;
                        }
                        if (e == null) {
                            setResult(Activity.RESULT_OK);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Error saving: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                });
        //localPlanner.saveEventually();
        startActivity(i);
    }
}
