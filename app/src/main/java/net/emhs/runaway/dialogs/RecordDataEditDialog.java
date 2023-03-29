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
import net.emhs.runaway.util.MapConverter;
import net.emhs.runaway.util.Time;
import net.emhs.runaway.util.UpdateAdapters;

import java.text.ParseException;
import java.util.Objects;

public class RecordDataEditDialog extends Dialog implements View.OnClickListener {

    // Initializes global variables
    private final Activity activity;
    private final Athlete athlete;
    private final Dialog parentDialog;
    private final int distanceKey;

    public RecordDataEditDialog(Activity activity, Athlete athlete, Dialog parentDialog, int distanceKey) {
        super(activity, R.style.Theme_Runaway_Popup); // Sets activity and theme

        this.distanceKey = distanceKey;
        this.activity = activity;
        this.athlete = athlete;
        this.parentDialog = parentDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_edit_data); // Shows dialog view

        // Sets time input hint to previous time
        EditText time = findViewById(R.id.record_edit_data_time);
        time.setHint(Objects.requireNonNull(MapConverter.fromString(athlete.records).get(distanceKey)).toString());

        // On click listeners for actions
        findViewById(R.id.record_edit_data_save).setOnClickListener(this);
        findViewById(R.id.record_edit_data_cancel).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId") // For switch statement
    @Override
    public void onClick(View view) {

        // Time input
        EditText time = findViewById(R.id.record_edit_data_time);
        String timeInput = time.getText().toString();

        switch (view.getId()) {
            case R.id.record_edit_data_save: // Save data
                if (timeInput.trim().isEmpty()) { // If time input is empty shows a warning and shakes dialog
                    Toast.makeText(activity.getApplicationContext(), "Input distance and time", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.record_edit_data).startAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.shake));
                    break;
                }
                try {
                    athlete.addRecord(distanceKey, new Time(timeInput)); // Checks if time input is a valid time format
                    UpdateAdapters.updateRecordAdapter(parentDialog, athlete); // Updates record list if it is valid
                    dismiss();
                } catch (ParseException e) { // Shows warning and shakes dialog if time input is invalid
                    Toast.makeText(activity.getApplicationContext(), "Invalid time format, use mm:ss.ms", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.record_edit_data).startAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.shake));
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.record_edit_data_cancel: // Cancel
                dismiss();
                break;
        }
    }
}
