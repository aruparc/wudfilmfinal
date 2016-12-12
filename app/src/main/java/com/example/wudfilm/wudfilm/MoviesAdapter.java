package com.example.wudfilm.wudfilm;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Bitmap;
import android.widget.Button;
import java.util.HashMap;
import java.util.List;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by susanyang on 11/15/16.
 */

public class MoviesAdapter extends BaseExpandableListAdapter {

    private Context ctx;
    private HashMap<String, List<Object>> Movies_Category;
    private List<String> Movies_List;

    public MoviesAdapter(Context ctx, HashMap<String, List<Object>> Movies_Category, List<String> Movies_List){
        this.ctx = ctx;
        this.Movies_Category = Movies_Category;
        this.Movies_List = Movies_List;
    }
    @Override
    public int getGroupCount() {
        return Movies_List.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Movies_List.get(groupPosition);
    }

    @Override
    public Object getChild(int parent, int child) {
        return Movies_Category.get(Movies_List.get(parent)).get(child);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentview) {
        String group_title = (String) getGroup(parent);
        if(convertView == null){
            LayoutInflater inflator = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.parent_layout, parentview, false);

        }
        TextView parent_textview = (TextView) convertView.findViewById(R.id.parent_txt);
        parent_textview.setTypeface(null, Typeface.BOLD);
        parent_textview.setText(group_title);
        return convertView;
    }

    @Override
    public View getChildView(final int parent, int child, boolean lastChild, View convertView, ViewGroup parentView) {
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_layout, parentView, false);
        }
        //set poster, description, and youtube link button
        ImageView imgViewChild = (ImageView) convertView.findViewById(R.id.img);
        imgViewChild.setImageBitmap((Bitmap) Movies_Category.get(Movies_List.get(parent)).get(1));
        TextView child_textview = (TextView) convertView.findViewById(R.id.child_txt);
        child_textview.setText((String) Movies_Category.get(Movies_List.get(parent)).get(0));
        Button but = (Button) convertView.findViewById(R.id.button);
        //listen for clicks on button and let user choose preferred app to open link
        but.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse((String) Movies_Category.get(Movies_List.get(parent)).get(2)));
                ctx.startActivity(browser);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}

