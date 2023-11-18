package net.emhs.runaway.adapters;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SeekBar;
import android.widget.TextView;

import net.emhs.runaway.R;
import net.emhs.runaway.db.Element;

import java.util.ArrayList;

public class ElementListAdapter extends BaseExpandableListAdapter {

    public ArrayList<Integer> groupList;
    public Element tempChild;
    public ArrayList<Element> childItem = new ArrayList<>();
    public LayoutInflater inflater;
    public Activity activity;

    public ElementListAdapter(Context context, ArrayList<Integer> groupList) {
        this.groupList = groupList;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Element child;
        for (int i : groupList) {
            child = new Element(0, 0);
            child.view = inflater.inflate(R.layout.section_row, null);
            this.childItem.add(child);

        }
    }

    @Override
    public int getGroupCount() {
        return groupList.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return 1;
    }

    @Override
    public Object getGroup(int i) {
        return null;
    }

    @Override
    public Object getChild(int i, int i1) {
        return null;
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int position, boolean isExpanded, View convertView, ViewGroup parent) {
        if(convertView==null) {
            convertView = inflater.inflate(R.layout.element_list, null);
        }
        TextView text = convertView.findViewById(R.id.element_list_text);
        text.setText("Section "+ groupList.get(position));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        tempChild = (Element) childItem.get(groupList.get(groupPosition));
        EditText distanceEdit = tempChild.view.findViewById(R.id.section_row_distance_edit);
        EditText paceEdit = tempChild.view.findViewById(R.id.section_row_pace_edit);
        SeekBar distanceBar = tempChild.view.findViewById(R.id.section_row_distance_bar);
        SeekBar paceBar = tempChild.view.findViewById(R.id.section_row_pace_bar);
        distanceEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String s = ((EditText)view).getText().toString();
                if(!s.isEmpty()) {
                    int i = Integer.parseInt(s);
                    if (i<0) i=0;
                    if (i>100) i=100;
                    distanceBar.setProgress(i);
                }
            }
        });
        distanceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) { distanceEdit.setText(String.valueOf(i)); }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { } });
        paceEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String s = ((EditText)view).getText().toString();
                if(!s.isEmpty()) {
                    int i = Integer.parseInt(s);
                    if (i<0) i=0;
                    if (i>100) i=100;
                    paceBar.setProgress(i);
                }
            }
        });
        paceBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) { paceEdit.setText(String.valueOf(i)); }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { } });
        return tempChild.view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        Element element = childItem.get(groupList.get(groupPosition));

        SeekBar distanceBar = element.view.findViewById(R.id.section_row_distance_bar);
        element.distanceProgress = distanceBar.getProgress();
        SeekBar paceBar = element.view.findViewById(R.id.section_row_pace_bar);
        element.paceProgress = paceBar.getProgress();

        EditText distanceEdit = element.view.findViewById(R.id.section_row_distance_edit);
        System.out.println(distanceEdit.getText().toString());
        if (!distanceEdit.getText().toString().isEmpty())
            element.distanceProgress = Integer.parseInt(distanceEdit.getText().toString());
        EditText paceEdit = element.view.findViewById(R.id.section_row_pace_edit);
        System.out.println(paceEdit.getText().toString());
        if (!paceEdit.getText().toString().isEmpty())
            element.paceProgress = Integer.parseInt(paceEdit.getText().toString());

        super.onGroupCollapsed(groupPosition);
    }
}
