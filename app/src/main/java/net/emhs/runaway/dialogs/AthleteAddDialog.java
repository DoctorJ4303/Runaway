package net.emhs.runaway.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.util.MapConverter;
import net.emhs.runaway.util.UpdateAdapters;

import java.util.HashMap;

public class AthleteAddDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private AppDatabase db;
    private Athlete athlete;
    private Button addRecords;
    private Button saveAthlete;

    public AthleteAddDialog(Activity activity) {
        super(activity, R.style.Theme_Runaway_Popup);
        this.activity = activity;
        db = AppDatabase.getDbInstance(activity.getApplicationContext());
        athlete = new Athlete();
        athlete.records = MapConverter.fromMap(new HashMap<>());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_athlete_add);

        findViewById(R.id.athlete_add_records).setOnClickListener(this);
        findViewById(R.id.athlete_add_save).setOnClickListener(this);
        findViewById(R.id.athlete_add_close).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        EditText name = findViewById(R.id.athlete_add_name);

        switch (view.getId()) {
            case R.id.athlete_add_records:
                RecordAddDialog dialog = new RecordAddDialog(activity, athlete);
                dialog.show();
                break;
            case R.id.athlete_add_save:
                String nameInput = name.getText().toString();
                if (!nameInput.trim().isEmpty()) {
                    athlete.name = name.getText().toString();
                    db.athleteDao().insertAthlete(athlete);
                    UpdateAdapters.updateAthleteAdapter(activity);
                    dismiss();
                } else {
                    Toast toast = Toast.makeText(activity.getApplicationContext(), "Please add name", Toast.LENGTH_SHORT);
                    findViewById(R.id.athlete_add).startAnimation(AnimationUtils.loadAnimation(activity.getApplicationContext(), R.anim.shake));
                    toast.show();
                }
                break;
            case R.id.athlete_add_close:
                System.out.println(athlete.name==null);
                System.out.println(MapConverter.fromString(athlete.records).isEmpty());
                if (name.getText().toString().isEmpty() && !MapConverter.fromString(athlete.records).isEmpty()) {
                    CloseDialog closeDialog = new CloseDialog(activity, athlete, this, "There is unsaved progress, are you sure you want to close?");
                    closeDialog.show();
                    dismiss();
                } else {
                    dismiss();
                }
                break;
        }
    }


}
