package net.emhs.runaway.dialogs;

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

    private Activity activity;
    private AppDatabase db;
    private Athlete athlete;
    private int position;
    public boolean edited = false;


    public AthleteEditDialog(Activity activity, Athlete athlete) {
        super(activity, R.style.Theme_Runaway_Popup);
        this.activity = activity;
        db = AppDatabase.getDbInstance(activity.getApplicationContext());
        this.athlete = athlete;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_athlete_edit);

        UpdateAdapters.updateAthleteAdapter(activity);

        findViewById(R.id.athlete_edit_records).setOnClickListener(this);
        findViewById(R.id.athlete_edit_save).setOnClickListener(this);
        findViewById(R.id.athlete_edit_delete).setOnClickListener(this);
        findViewById(R.id.athlete_edit_close).setOnClickListener(this);

        EditText name = findViewById(R.id.athlete_edit_name);
        name.setHint(athlete.name);
    }

    @Override
    public void onClick(View view) {
        EditText name = findViewById(R.id.athlete_edit_name);
        switch (view.getId()) {
            case R.id.athlete_edit_records:
                RecordEditDialog dialog = new RecordEditDialog(activity, athlete, this);
                dialog.show();
                dismiss();
                break;
            case R.id.athlete_edit_save:
                String nameInput = name.getText().toString();
                if (!nameInput.equalsIgnoreCase("") && !nameInput.equalsIgnoreCase(" "))
                    db.athleteDao().updateName(nameInput, athlete.uid);
                db.athleteDao().updateRecords(athlete.records, athlete.uid);
                dismiss();
                break;
            case R.id.athlete_edit_delete:
                ConfirmationDialog confirmationDialog = new ConfirmationDialog(activity, athlete, this, "Are you sure you want to delete this athlete?");
                confirmationDialog.show();
                UpdateAdapters.updateAthleteAdapter(activity);
                dismiss();
                break;
            case R.id.athlete_edit_close:
                System.out.println(edited);
                if (!name.getText().toString().trim().isEmpty() || edited) {
                    CloseDialog closeDialog = new CloseDialog(activity, athlete, this, "There is unsaved progress, are you sure you want to close?");
                    closeDialog.show();
                }
                dismiss();
                break;
        }
    }

    @Override
    public void show() {
        super.show();
    }
}
