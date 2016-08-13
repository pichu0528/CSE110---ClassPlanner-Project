package com.example.annchih.classplanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.ui.ParseLoginBuilder;

import java.text.ParseException;
import java.util.List;

public class Calender_Activity extends Activity {

    private LayoutInflater inflater;
    private ParseQueryAdapter<LocalPlanner> classPlannedListAdapter;
    private static final int LOGIN_ACTIVITY_CODE = 100;
    private static final int EDIT_ACTIVITY_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender_);

        ParseQueryAdapter.QueryFactory<LocalPlanner> factory =
                new ParseQueryAdapter.QueryFactory<LocalPlanner>() {
                    public ParseQuery<LocalPlanner> create() {
                        ParseQuery<LocalPlanner> query = LocalPlanner.getQuery();

                        query.orderByDescending("createdAt");
                        query.fromLocalDatastore();
                        return query;
                    }
                };

        // Set up the adapter
        inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        classPlannedListAdapter = new LocalListAdapter(this, factory);

        /*noTodosView = (LinearLayout) findViewById(R.id.no_todos_view);
        todoListView.setEmptyView(noTodosView);*/
        // Attach the query adapter to the view
        ListView classPlannedListView = (ListView) findViewById(R.id.quarterListView);

        classPlannedListView.setAdapter(classPlannedListAdapter);

    }

    public void classesTaken(View view) {
        Intent intent = new Intent(this, ClassList.class);
        startActivity(intent);
    }
    public void delete(View view) {
        LocalPlanner localPlanner = new LocalPlanner();
        String ID = localPlanner.getObjectId();
        localPlanner.createWithoutData("LocalPlanner","ID").deleteInBackground();
        //localPlanner.deleteInBackground();
        setResult(Activity.RESULT_OK);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // An OK result means the pinned dataset changed or
        // log in was successful
        if (resultCode == RESULT_OK) {
            if (requestCode == EDIT_ACTIVITY_CODE) {
                // Coming back from the edit view, update the view
                classPlannedListAdapter.loadObjects();
            } else if (requestCode == LOGIN_ACTIVITY_CODE) {
                // If the user is new, sync data to Parse,
                // else get the current list from Parse
                if (ParseUser.getCurrentUser().isNew()) {
                    syncTodosToParse();
                } else {
                    loadFromParse();
                }
            }

        }
    }
    private void syncTodosToParse() {
        // We could use saveEventually here, but we want to have some UI
        // around whether or not the draft has been saved to Parse
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if ((ni != null) && (ni.isConnected())) {
            if (!ParseAnonymousUtils.isLinked(ParseUser.getCurrentUser())) {
                // If we have a network connection and a current logged in user,
                // sync the classes
                // In this app, local changes should overwrite content on the
                // server.
                ParseQuery<LocalPlanner> query = LocalPlanner.getQuery();
                query.fromPin("Class Planned");
                query.whereEqualTo("isDraft", true);
                query.findInBackground(new FindCallback<LocalPlanner>() {
                    @Override
                    public void done(List<LocalPlanner> planners, com.parse.ParseException e) {
                        if (e == null) {
                            for (final LocalPlanner planner : planners) {
                                // Set is draft flag to false before
                                // syncing to Parse
                                planner.setDraft(false);
                                planner.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(com.parse.ParseException e) {
                                        if (e == null) {
                                            // Let adapter know to update view
                                            if (!isFinishing()) {
                                                classPlannedListAdapter
                                                        .notifyDataSetChanged();
                                            }
                                        } else {
                                            // Reset the is draft flag locally
                                            // to true
                                            planner.setDraft(true);
                                        }
                                    }

                                });

                            }
                        } else {
                            Log.i("TodoListActivity",
                                    "syncTodosToParse: Error finding pinned todos: "
                                            + e.getMessage());
                        }
                    }
                });
            } else {
                // If we have a network connection but no logged in user, direct
                // the person to log in or sign up.
                ParseLoginBuilder builder = new ParseLoginBuilder(this);
                startActivityForResult(builder.build(), LOGIN_ACTIVITY_CODE);
            }
        } else {
            // If there is no connection, let the user know the sync didn't
            // happen
            Toast.makeText(
                    getApplicationContext(),
                    "Your device appears to be offline. Some todos may not have been synced to Parse.",
                    Toast.LENGTH_LONG).show();
        }

    }

    private void loadFromParse() {
        ParseQuery<LocalPlanner> query = LocalPlanner.getQuery();
        query.whereEqualTo("author", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<LocalPlanner>() {

            @Override
            public void done(List<LocalPlanner> objects, com.parse.ParseException e) {
                if (e == null) {
                    ParseObject.pinAllInBackground((List<LocalPlanner>) objects,
                            new SaveCallback() {
                                @Override
                                public void done(com.parse.ParseException e) {

                                }

                                public void done(ParseException e) {
                                    if (e == null) {
                                        if (!isFinishing()) {
                                            classPlannedListAdapter.loadObjects();
                                        }
                                    } else {
                                        Log.i("TodoListActivity",
                                                "Error pinning todos: "
                                                        + e.getMessage());
                                    }
                                }
                            });
                } else {
                    Log.i("TodoListActivity",
                            "loadFromParse: Error finding pinned todos: "
                                    + e.getMessage());
                }
            }
        });
    }


    private class LocalListAdapter extends ParseQueryAdapter<LocalPlanner> {

        public LocalListAdapter(Context context,
                                ParseQueryAdapter.QueryFactory<LocalPlanner> queryFactory) {
            super(context, queryFactory);
        }

        @Override
        public View getItemView(LocalPlanner localPlanner, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view == null) {

                view = inflater.inflate(R.layout.planned_item, parent, false);
                holder = new ViewHolder();
                holder.class_id = (TextView) view
                        .findViewById(R.id.class_id);
                holder.class_title = (TextView) view.findViewById(R.id.class_title);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            TextView class_title = holder.class_title;
            TextView class_ID = holder.class_id;
            class_ID.setText(localPlanner.getID());
            class_title.setText(localPlanner.getTitle());
            if (localPlanner.isDraft()) {
                class_title.setTypeface(null, Typeface.ITALIC);
            } else {
                class_title.setTypeface(null, Typeface.NORMAL);
            }
            return view;
        }
    }

    private static class ViewHolder {
        TextView class_id;
        TextView class_title;
    }
}