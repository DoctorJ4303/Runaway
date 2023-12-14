package net.emhs.runaway.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import net.emhs.runaway.R;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.util.Converter;
import net.emhs.runaway.util.RecyclerItemClickListener;
import net.emhs.runaway.util.UpdateAdapters;

import java.util.ArrayList;


public class RecordAddDialog extends Dialog implements View.OnClickListener {

    // Initializes global variables
    private final Athlete athlete;
    private final Activity activity;

    public RecordAddDialog(Activity activity, Athlete athlete) {
        super(activity, R.style.Theme_Runaway_Popup); // Sets theme and activity

        this.activity = activity;
        this.athlete = athlete;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_edit); // Sets dialog view

        UpdateAdapters.updateRecordAdapter(this, athlete); // Updates record list

        // On click listeners for actions
        findViewById(R.id.record_edit_add).setOnClickListener(this);
        findViewById(R.id.record_edit_back).setOnClickListener(this);

        // On click listener for individual records
        RecyclerView recyclerView = findViewById(R.id.record_edit_recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(activity, (view, position) -> {
            int distance = Converter.toRecordList(athlete.records).get(position).distance; // Sets distance (wish there was a cleaner way to do this)
            RecordDataEditDialog dataEditDialog = new RecordDataEditDialog(activity, athlete, RecordAddDialog.this, distance);
            dataEditDialog.show();
        }));
    }

    @SuppressLint("NonConstantResourceId") // For switch statement
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_edit_add: // Add record
                RecordDataAddDialog dialog = new RecordDataAddDialog(activity, athlete, this);
                dialog.show();
                break;
            case R.id.record_edit_back: // Go back
                dismiss();
                break;
        }
    }
}

