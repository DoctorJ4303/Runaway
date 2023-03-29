package net.emhs.runaway.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import net.emhs.runaway.R;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.util.UpdateAdapters;

public class RecordViewDialog extends Dialog implements View.OnClickListener {

    // Initializes global variables
    private final Activity activity;
    private final Athlete athlete;
    private final Dialog parentDialog;

    public RecordViewDialog(Activity activity, Athlete athlete, Dialog parentDialog) {
        super(activity, R.style.Theme_Runaway_Popup); // Sets theme and activity

        this.activity = activity;
        this.athlete = athlete;
        this.parentDialog = parentDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_view); // Shows dialog view

        UpdateAdapters.updateRecordAdapter(this, athlete); // Updates record list

        // On click listeners for actions
        findViewById(R.id.record_view_back).setOnClickListener(this);
        findViewById(R.id.record_view_edit).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId") // For switch statement
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_view_back: // Back shows parent dialog
                parentDialog.show();
                dismiss();
                break;
            case R.id.record_view_edit: // Edit changes the current dialog to RecordEditDialog with a new AthleteEditDialog as parent
                RecordEditDialog dialog = new RecordEditDialog(activity, athlete, new AthleteEditDialog(activity, athlete));
                dialog.show();
                dismiss();
                break;
        }
    }
}
