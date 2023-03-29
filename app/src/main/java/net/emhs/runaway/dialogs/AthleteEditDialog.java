package net.emhs.runaway.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.util.UpdateAdapters;

public class AthleteEditDialog extends Dialog implements View.OnClickListener {

    // Initializes global variables
    private final Activity activity;
    private final Athlete athlete;
    private final AppDatabase db;
    public boolean edited = false;


    public AthleteEditDialog(Activity activity, Athlete athlete) {
        super(activity, R.style.Theme_Runaway_Popup); // Sets activity and theme of super class

        this.activity = activity;
        this.athlete = athlete;
        this.db = AppDatabase.getDbInstance(activity.getApplicationContext()); // Sets database instance
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_athlete_edit); // Sets dialog view

        UpdateAdapters.updateAthleteAdapter(activity); // Updates athlete list of AthleteListActivity

        // On click listeners for actions
        findViewById(R.id.athlete_edit_records).setOnClickListener(this);
        findViewById(R.id.athlete_edit_save).setOnClickListener(this);
        findViewById(R.id.athlete_edit_delete).setOnClickListener(this);
        findViewById(R.id.athlete_edit_close).setOnClickListener(this);

        // Sets hint for name text input
        EditText name = findViewById(R.id.athlete_edit_name);
        name.setHint(athlete.name);
    }

    @SuppressLint("NonConstantResourceId") // For switch statements
    @Override
    public void onClick(View view) {

        // Current name input updates every click
        EditText name = findViewById(R.id.athlete_edit_name);
        String nameInput = name.getText().toString();

        switch (view.getId()) {
            case R.id.athlete_edit_records: // Edit records
                RecordEditDialog dialog = new RecordEditDialog(activity, athlete, this);
                dialog.show();
                dismiss();
                break;
            case R.id.athlete_edit_save: // Save athlete
                if (!nameInput.trim().isEmpty()) // If the name input is not empty, then the DAO updates name
                    db.athleteDao().updateName(nameInput, athlete.uid);
                db.athleteDao().updateRecords(athlete.records, athlete.uid); // Updates records
                dismiss();
                break;
            case R.id.athlete_edit_delete: // Delete athlete
                // Confirms delete
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(activity, athlete, this, "Are you sure you want to delete this athlete?");
                confirmationDialog.show();
                UpdateAdapters.updateAthleteAdapter(activity); // Updates AthleteListActivity athlete list
                dismiss();
                break;
            case R.id.athlete_edit_close: // Close
                if (!nameInput.trim().isEmpty() || edited) { // If it is edited, a dialog confirms the closure with unsaved progress
                    CloseDialog closeDialog = new CloseDialog(activity, this, "There is unsaved progress, are you sure you want to close?");
                    closeDialog.show();
                }
                dismiss();
                break;
        }
    }
}
