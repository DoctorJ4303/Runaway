package net.emhs.runaway.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import net.emhs.runaway.R;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.util.Time;
import net.emhs.runaway.util.UpdateAdapters;

import java.text.ParseException;

public class RecordDataAddDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Athlete athlete;
    private Dialog parentDialog;

    public RecordDataAddDialog(Activity activity, Athlete athlete, Dialog parentDialog) {
        super(activity, R.style.Theme_Runaway_Popup);

        this.activity = activity;
        this.athlete = athlete;
        this.parentDialog = parentDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_add);

        findViewById(R.id.record_add_save).setOnClickListener(this);
        findViewById(R.id.record_add_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_add_save:
                EditText distance = findViewById(R.id.record_add_distance);
                EditText time = findViewById(R.id.record_add_time);

                String distanceInput = distance.getText().toString();
                String timeInput = time.getText().toString();

                if (distanceInput.trim().isEmpty() || timeInput.trim().isEmpty()) {
                    Toast.makeText(activity.getApplicationContext(), "Input distance and time", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.record_add).startAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.shake));
                    break;
                }
                try {
                    athlete.addRecord(Integer.parseInt(distanceInput), new Time(timeInput));
                    UpdateAdapters.updateRecordAdapter(parentDialog, athlete);
                    dismiss();
                } catch (ParseException e) {
                    Toast.makeText(activity.getApplicationContext(), "Invalid time format, use mm:ss.ms", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.record_add).startAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.shake));
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.record_add_cancel:
                dismiss();
                break;
        }
    }
}
