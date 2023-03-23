package net.emhs.runaway.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;

public class AthleteViewDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private AppDatabase db;
    private Athlete athlete;

    public AthleteViewDialog(Activity activity, Athlete athlete) {
        super(activity, R.style.Theme_Runaway_Popup);
        this.activity = activity;
        db = AppDatabase.getDbInstance(activity.getApplicationContext());
        this.athlete = athlete;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_athlete_view);

        findViewById(R.id.athlete_view_records).setOnClickListener(this);
        findViewById(R.id.athlete_view_edit).setOnClickListener(this);
        findViewById(R.id.athlete_view_close).setOnClickListener(this);

        TextView name = findViewById(R.id.athlete_view_name);
        name.setText(athlete.name);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.athlete_view_records:
                RecordViewDialog viewDialog = new RecordViewDialog(activity, athlete, this);
                viewDialog.show();
                dismiss();
                break;
            case R.id.athlete_view_edit:
                AthleteEditDialog editDialog = new AthleteEditDialog(activity, athlete);
                editDialog.show();
                dismiss();
                break;
            case R.id.athlete_view_close:
                dismiss();
                break;
        }
    }
}
