package net.emhs.runaway.dialogs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.util.UpdateAdapters;

public class ConfirmationDialog extends Dialog implements View.OnClickListener{

    // Initializes global variables
    private final String text;
    private final AppDatabase db;
    private final Athlete athlete;
    private final Activity activity;
    private final Dialog parentDialog;

    public ConfirmationDialog(Activity activity, Athlete athlete, Dialog parentDialog, String text) {
        super(activity, R.style.Theme_Runaway_Popup); // Sets theme and activity

        this.db = AppDatabase.getDbInstance(activity); // Sets database instance
        this.text = text;
        this.athlete = athlete;
        this.activity = activity;
        this.parentDialog = parentDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirmation); // Sets dialog view

        // On click listeners for actions (Confirm or deny)
        findViewById(R.id.confirmation_confirm).setOnClickListener(this);
        findViewById(R.id.confirmation_deny).setOnClickListener(this);

        // Sets warning text
        TextView textView = findViewById(R.id.confirmation_text);
        textView.setText(text);
    }

    @SuppressLint("NonConstantResourceId") // For switch statement
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmation_confirm: // Confirm
                db.athleteDao().delete(athlete); // Deletes athlete from database
                UpdateAdapters.updateAthleteAdapter(activity); // Updates AthleteListAdapter athlete list
                dismiss();
                break;
            case R.id.confirmation_deny: // Deny shows parent dialog to continue editing
                parentDialog.show();
                dismiss();
                break;
        }
    }
}
