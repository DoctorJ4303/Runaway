package net.emhs.runaway.dialogs;

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

public class RecordDataEditDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Athlete athlete;
    private Dialog parentDialog;
    private int distanceKey;

    public RecordDataEditDialog(Activity activity, Athlete athlete, Dialog parentDialog, int distanceKey) {
        super(activity, R.style.Theme_Runaway_Popup);

        this.distanceKey = distanceKey;
        this.activity = activity;
        this.athlete = athlete;
        this.parentDialog = parentDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_edit_data);

        EditText time = findViewById(R.id.record_edit_data_time);
        time.setHint(MapConverter.fromString(athlete.records).get(distanceKey).toString());

        findViewById(R.id.record_edit_data_save).setOnClickListener(this);
        findViewById(R.id.record_edit_data_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_edit_data_save:
                EditText time = findViewById(R.id.record_edit_data_time);

                String timeInput = time.getText().toString();

                if (timeInput.trim().isEmpty()) {
                    Toast.makeText(activity.getApplicationContext(), "Input distance and time", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.record_edit_data).startAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.shake));
                    break;
                }
                try {
                    athlete.addRecord(distanceKey, new Time(timeInput));
                    UpdateAdapters.updateRecordAdapter(parentDialog, athlete);
                    dismiss();
                } catch (ParseException e) {
                    Toast.makeText(activity.getApplicationContext(), "Invalid time format, use mm:ss.ms", Toast.LENGTH_SHORT).show();
                    findViewById(R.id.record_edit_data).startAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.shake));
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.record_edit_data_cancel:
                dismiss();
                break;
        }
    }
}
