package net.emhs.runaway.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.util.Converter;
import net.emhs.runaway.util.UpdateAdapters;

public class AthleteAddDialog extends Dialog implements View.OnClickListener {

    // Initializes global variables
    private final Activity activity;
    private final Athlete athlete;
    private final AppDatabase db;

    public AthleteAddDialog(Activity activity) {
        super(activity, R.style.Theme_Runaway_Popup); // Sets activity and theme of super class

        this.activity = activity;
        this.athlete = new Athlete(); // Creates a new athlete
        this.db = AppDatabase.getDbInstance(activity.getApplicationContext()); // Sets database instance
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_athlete_add); // Sets the view

        // Creates click listener for all actions
        findViewById(R.id.athlete_add_records).setOnClickListener(this);
        findViewById(R.id.athlete_add_save).setOnClickListener(this);
        findViewById(R.id.athlete_add_close).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId") // For switch statements
    @Override
    public void onClick(View view) {

        // Gets the name every click
        EditText name = findViewById(R.id.athlete_add_name);
        String nameInput = name.getText().toString();

        switch (view.getId()) {
            case R.id.athlete_add_records: // Add records
                RecordAddDialog dialog = new RecordAddDialog(activity, athlete);
                dialog.show(); // Shows record add list dialog
                break;
            case R.id.athlete_add_save: // Save athlete
                if (!nameInput.trim().isEmpty()) { // if the name input is not empty
                    athlete.name = nameInput; // Sets athlete's name
                    db.athleteDao().insertAthlete(athlete); // inserts the athlete into the database
                    UpdateAdapters.updateAthleteAdapter(activity); // Updates list in record add list activity
                    dismiss();
                } else { // if it is empty
                    Toast toast = Toast.makeText(activity.getApplicationContext(), "Please add name", Toast.LENGTH_SHORT); // Shows toast warning
                    findViewById(R.id.athlete_add).startAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.shake)); // Shakes dialog
                    toast.show();
                }
                break;
            case R.id.athlete_add_close: // Close
                if (!nameInput.trim().isEmpty() || !Converter.toRecordList(athlete.records).isEmpty()) { // If there is saved records or the name input text is not empty
                    CloseDialog closeDialog = new CloseDialog(activity, this, "There is unsaved progress, are you sure you want to close?"); // Warning dialog
                    closeDialog.show();
                }
                dismiss();
                break;
        }
    }
}
