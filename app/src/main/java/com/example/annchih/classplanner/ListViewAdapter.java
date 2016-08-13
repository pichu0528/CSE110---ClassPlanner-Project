package com.example.annchih.classplanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnnChih on 11/22/15.
 */
public class ListViewAdapter extends BaseAdapter{
    Context mContext;
    private List<ClassData> list_of_classes = null;
    //private List<ClassData> classes_taken = new ArrayList<ClassData>();
    private ArrayList<ClassData> arraylist;
    LayoutInflater inflater;
    private ArrayList<ParseObject> position;
    final int SELECT_CLASSES = 1;
    final int LIST_OF_CLASSES = 0;
    boolean [] checkItems;
    List<ParseObject> ob;

    public ListViewAdapter(Context context,
                        List<ClassData> list_of_classes) {
        mContext = context;
        this.list_of_classes = list_of_classes;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<ClassData>();
        this.arraylist.addAll(list_of_classes);
        checkItems = new boolean[list_of_classes.size()];
    }
    public class ViewHolder {
        TextView class_id;
        TextView class_name;
        CheckBox checkBox;
        TextView description;
        boolean taken;
    }
    @Override
    public int getItemViewType(int position){
        if(mContext instanceof Select_classesTaken)
            return SELECT_CLASSES;
        else
            return LIST_OF_CLASSES;
    }
    @Override
    public int getCount() {
        return list_of_classes.size();
    }
    @Override
    public ClassData getItem(int position) {
        return list_of_classes.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public View getView(final int position, View view, ViewGroup parent) {
        final View convertView = view;
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            if(getItemViewType(position)==SELECT_CLASSES){
                view = inflater.inflate(R.layout.check_class_item, null);
                holder.checkBox =(CheckBox)view.findViewById(R.id.checkbox);
            }
            else{
                view = inflater.inflate(R.layout.class_item, null);
            }
            // Locate the TextViews in listview_item.xml
            holder.class_id = (TextView) view.findViewById(R.id.class_id);
            holder.class_name = (TextView) view.findViewById(R.id.class_title);
            if(getItemViewType(position)==LIST_OF_CLASSES)
                holder.description = (TextView) view.findViewById(R.id.description);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();

            if(getItemViewType(position)==SELECT_CLASSES) {
                // remove the listener so that it does not get attached to other chechboxes
                holder.checkBox.setOnCheckedChangeListener(null);
                //update the checkbox value from boolean array
                holder.checkBox.setChecked(checkItems[position]);
            }

        }
        // Set the results into TextViews
        holder.class_id.setText(list_of_classes.get(position).get_class_id_2());
        holder.class_name.setText(list_of_classes.get(position).get_class_name_2());
        //if(getItemViewType(position)==SELECT_CLASSES)
        //  holder.description.setText(list_of_classes.get(position).get_description());

        if(getItemViewType(position)==SELECT_CLASSES){
            holder.checkBox.setTag(String.valueOf(position));
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int pos = Integer.parseInt(buttonView.getTag().toString());
                    checkItems[pos] = true;
                    //getItem(position).set_taken(true);
                    //Log.d("This is: ",getItem(position).get_class_id_2()+getItem(position).get_taken());
                    /*ParseQuery<ParseObject> query = ParseQuery.getQuery("classplanner");
                    List<ParseObject> ob;
                    try {
                        // Locate the class table named "Country" in Parse.com
                        ob = query.find();
                        for (ParseObject classplanner : ob) {
                            if(classplanner.get("class_id").toString()==holder.class_id.toString()){
                                Log.d("Query: ",classplanner.get("class_id").toString());
                                Log.d("holder: ",holder.class_id.toString());
                                classplanner.put("taken",true);
                                classplanner.saveInBackground();
                            }
                        }
                    }  catch (com.parse.ParseException e) {
                        e.printStackTrace();
                    }*/

                }
            });
        }
        if(getItemViewType(position)==LIST_OF_CLASSES){
            //Log.d("List of class: ",getItem(position).get_class_id_2()+getItem(position).get_taken());
            /*ParseQuery query = ParseUser.getQuery();
            query.whereEqualTo("taken", true);
            //query.fromLocalDatastore();
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e==null)
                        convertView.setBackgroundColor(Color.RED);
                }
            });*/


            //view.setBackgroundColor(Color.RED);

        }


        if(getItemViewType(position)==LIST_OF_CLASSES){
            // Listen for ListView Item Click
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    // Send single item click data to SingleItemView Class
                    Intent intent = new Intent(mContext, SingleItem.class);
                    // Pass all data rank
                    intent.putExtra("id",
                            (list_of_classes.get(position).get_class_id_2()));
                    // Pass all data country
                    intent.putExtra("title",
                            (list_of_classes.get(position).get_class_name_2()));
                    // Pass all data country
                    intent.putExtra("description",
                            (list_of_classes.get(position).get_description()));
                    // Start SingleItemView Class
                    mContext.startActivity(intent);
                }
            });
        }


        return view;
    }


}