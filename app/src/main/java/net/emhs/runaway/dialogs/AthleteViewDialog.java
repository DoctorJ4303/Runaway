package net.emhs.runaway.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import net.emhs.runaway.R;
import net.emhs.runaway.db.Athlete;

public class AthleteViewDialog extends Dialog implements View.OnClickListener {

    // Initializes global variables
    private final Activity activity;
    private final Athlete athlete;

    public AthleteViewDialog(Activity activity, Athlete athlete) {
        super(activity, R.style.Theme_Runaway_Popup); // Sets theme and activity

        this.activity = activity;
        this.athlete = athlete;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_athlete_view); // Sets dialog view

        // On click listeners for actions
        findViewById(R.id.athlete_view_records).setOnClickListener(this);
        findViewById(R.id.athlete_view_edit).setOnClickListener(this);
        findViewById(R.id.athlete_view_close).setOnClickListener(this);

        // Sets name value
        TextView name = findViewById(R.id.athlete_view_name);
        name.setText(athlete.name);
    }

    @SuppressLint("NonConstantResourceId") // For switch statement
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.athlete_view_records: // View records
                RecordViewDialog viewDialog = new RecordViewDialog(activity, athlete, this); // View records dialog
                viewDialog.show();
                dismiss();
                break;
            case R.id.athlete_view_edit: // Edit athlete
                AthleteEditDialog editDialog = new AthleteEditDialog(activity, athlete); // Edit athlete dialog
                editDialog.show();
                dismiss();
                break;
            case R.id.athlete_view_close: // Close
                dismiss(); // Doesn't need a check because there is no things to change
                break;
        }
    }
}
