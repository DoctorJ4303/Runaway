package net.emhs.runaway.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import net.emhs.runaway.R;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.db.Time;
import net.emhs.runaway.util.UpdateAdapters;

import java.text.ParseException;

public class RecordDataAddDialog extends Dialog implements View.OnClickListener {

    // Initializes global variables
    private final Activity activity;
    private final Athlete athlete;
    private final Dialog parentDialog;

    public RecordDataAddDialog(Activity activity, Athlete athlete, Dialog parentDialog) {
        super(activity, R.style.Theme_Runaway_Popup); // Sets activity and theme

        this.activity = activity;
        this.athlete = athlete;
        this.parentDialog = parentDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_add); // Sets dialog view

        // On click listeners (Save or cancel)
        findViewById(R.id.record_add_save).setOnClickListener(this);
        findViewById(R.id.record_add_cancel).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId") // For switch statement
    @Override
    public void onClick(View view) {

        // Distance and time inputs
        EditText distance = findViewById(R.id.record_add_distance);
        EditText time = findViewById(R.id.record_add_time);
        String distanceInput = distance.getText().toString();
        String timeInput = time.getText().toString();

        switch (view.getId()) {
            case R.id.record_add_save:
                if (distanceInput.trim().isEmpty() || timeInput.trim().isEmpty()) { // If distance and time inputs are both empty
                    Toast.makeText(activity.getApplicationContext(), "Input distance and time", Toast.LENGTH_SHORT).show(); // Shows toast warning
                    findViewById(R.id.record_add).startAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.shake)); // Shakes dialog
                    break;
                }
                try {
                    //athlete.addRecord(Integer.parseInt(distanceInput), new Time(timeInput)); // Checks if the time is valid format
                    UpdateAdapters.updateRecordAdapter(parentDialog, athlete); // Updates record list if format is valid
                    dismiss();
                } catch (Exception e) { // If time input is not a valid format
                    Toast.makeText(activity.getApplicationContext(), "Invalid time format", Toast.LENGTH_SHORT).show(); // Shows toast warning
                    findViewById(R.id.record_add).startAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.shake)); // Shakes dialog
                    e.printStackTrace();
                }
                break;
            case R.id.record_add_cancel: // Cancel
                dismiss();
                break;
        }
    }
}
