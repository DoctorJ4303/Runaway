package net.emhs.runaway.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import net.emhs.runaway.R;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.util.Converter;
import net.emhs.runaway.util.RecyclerItemClickListener;
import net.emhs.runaway.util.UpdateAdapters;

import java.util.ArrayList;

public class RecordEditDialog extends Dialog implements View.OnClickListener {

    // Initializes global variables
    private final Activity activity;
    private final Athlete athlete;
    private final AthleteEditDialog parentDialog;
    private final String currentRecords;

    public RecordEditDialog(Activity activity, Athlete athlete, AthleteEditDialog parentDialog) {
        super(activity, R.style.Theme_Runaway_Popup); // Sets activity and theme

        this.activity = activity;
        this.athlete = athlete;
        this.parentDialog = parentDialog;
        this.currentRecords = athlete.records;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_edit);

        // Updates record list
        UpdateAdapters.updateRecordAdapter(this, athlete);

        // On click listener for actions
        findViewById(R.id.record_edit_add).setOnClickListener(this);
        findViewById(R.id.record_edit_back).setOnClickListener(this);

        // On click listener for individual records
        RecyclerView recyclerView = findViewById(R.id.record_edit_recycler_view);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(activity, (view, position) -> {
            int distance = Converter.toRecordList(athlete.records).get(position).distance;
            RecordDataEditDialog dataEditDialog = new RecordDataEditDialog(activity, athlete, RecordEditDialog.this, distance);
            dataEditDialog.show();
        }));
    }

    @SuppressLint("NonConstantResourceId") // For switch statement
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_edit_add: // Add record
                RecordDataAddDialog dialog = new RecordDataAddDialog(activity, athlete, this); // Opens add record data dialog
                dialog.show();
                break;
            case R.id.record_edit_back: // Back
                if (!athlete.records.equals(currentRecords)) // If the records were changed, set parent dialog's edited value to true. For checking for closure
                    parentDialog.edited = true;
                parentDialog.show();
                dismiss();
                break;
        }
    }
}
