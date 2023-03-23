package net.emhs.runaway.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;

public class CloseDialog extends Dialog implements View.OnClickListener{

    private String text;
    private AppDatabase db;
    private Athlete athlete;
    private Activity activity;
    private Dialog parentDialog;

    public CloseDialog(Activity activity, Athlete athlete, Dialog parentDialog, String text) {
        super(activity, R.style.Theme_Runaway_Popup);
        db = AppDatabase.getDbInstance(activity);
        this.text = text;
        this.athlete = athlete;
        this.activity = activity;
        this.parentDialog = parentDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_confirmation);


        TextView textView = findViewById(R.id.confirmation_text);
        textView.setText(text);
        findViewById(R.id.confirmation_confirm).setOnClickListener(this);
        findViewById(R.id.confirmation_deny).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmation_confirm:
                dismiss();
                break;
            case R.id.confirmation_deny:
                parentDialog.show();
                dismiss();
                break;
        }
    }
}
