package net.emhs.runaway.adapters;

import android.annotation.SuppressLint;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import net.emhs.runaway.R;
import net.emhs.runaway.db.Element;
import net.emhs.runaway.util.AfterTextWatcher;

import java.util.ArrayList;

public class ElementListAdapter extends BaseExpandableListAdapter {

    public ArrayList<View> groupList;
    public Element tempChild;
    public ArrayList<Element> childItem = new ArrayList<>();
    public LayoutInflater inflater;
    public Activity activity;

    public ElementListAdapter(Activity activity, ArrayList<Integer> list) {
        this.activity = activity;

        ArrayList<View> groupList = new ArrayList<>();
        for (int i : list) { groupList.add(inflater.inflate(R.layout.element_list, null)); }
        this.groupList = groupList;

        this.inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Element child;
        for (int i : list) {
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
    public Object getChild(int i) {
        return childItem.get(i);
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getGroupView(int position, boolean isExpanded, View convertView, ViewGroup parentViewGroup) {
        ExpandableListView parent = (ExpandableListView) parentViewGroup;
        convertView = groupList.get(position);


        TextView text = convertView.findViewById(R.id.element_list_text);
        text.setText("Section "+ (position+1));

        ImageView close = convertView.findViewById(R.id.element_list_close);
        close.setOnClickListener(view -> {
            for (int i = position; i<groupList.size(); i++){
                if (parent.isGroupExpanded(i)) {
                    parent.collapseGroup(i);
                    if (i != 0)
                        parent.expandGroup(i - 1);
                }
                TextView textView = groupList.get(i).findViewById(R.id.element_list_text);
                textView.setText("Section " + (i+1));
            }
            groupList.remove(position);
            childItem.remove(position);
            notifyDataSetChanged();
        });

        return groupList.get(position);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        tempChild = (Element) childItem.get(groupPosition);

        EditText distanceEdit = tempChild.view.findViewById(R.id.section_row_distance_edit);
        EditText paceEdit = tempChild.view.findViewById(R.id.section_row_pace_edit);
        paceEdit.addTextChangedListener(new AfterTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) return;
                int i = Integer.parseInt(editable.toString());
                if (i > 5000) {
                    paceEdit.setText("5000");
                    paceEdit.setSelection(4);
                }
            }
        });
        distanceEdit.addTextChangedListener(new AfterTextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().isEmpty()) return;
                int i = Integer.parseInt(editable.toString());
                if (i > 5000) {
                    distanceEdit.setText("5000");
                    distanceEdit.setSelection(4);
                }
            }
        });
        return tempChild.view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        Element element = childItem.get(groupPosition);

        EditText distanceEdit = element.view.findViewById(R.id.section_row_distance_edit);
        if (!distanceEdit.getText().toString().isEmpty())
            element.distanceProgress = Integer.parseInt(distanceEdit.getText().toString());
        else
            element.paceProgress = 0;
        EditText paceEdit = element.view.findViewById(R.id.section_row_pace_edit);
        if (!paceEdit.getText().toString().isEmpty())
            element.paceProgress = Integer.parseInt(paceEdit.getText().toString());
        else
            element.paceProgress = 0;
        super.onGroupCollapsed(groupPosition);
    }

    public void addChild(ExpandableListView parent) {
        Element element = new Element(0, 0);
        element.view = inflater.inflate(R.layout.section_row, null);
        groupList.add(inflater.inflate(R.layout.element_list, null));
        childItem.add(element);
        parent.expandGroup(groupList.size()-1);
        notifyDataSetChanged();
    }
}
