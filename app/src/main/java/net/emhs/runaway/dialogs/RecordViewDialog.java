package net.emhs.runaway.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.util.UpdateAdapters;

public class RecordViewDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Athlete athlete;
    private AppDatabase db;
    private Dialog parentDialog;

    public RecordViewDialog(Activity activity, Athlete athlete, Dialog parentDialog) {
        super(activity, R.style.Theme_Runaway_Popup);
        this.activity = activity;
        this.db = AppDatabase.getDbInstance(activity);
        this.athlete = athlete;
        this.parentDialog = parentDialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_view);

        UpdateAdapters.updateRecordAdapter(this, athlete);
        System.out.println(athlete.records);

        findViewById(R.id.record_view_back).setOnClickListener(this);
        findViewById(R.id.record_view_edit).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_view_back:
                parentDialog.show();
                dismiss();
                break;
            case R.id.record_view_edit:
                RecordEditDialog dialog = new RecordEditDialog(activity, athlete, new AthleteEditDialog(activity, athlete));
                dialog.show();
                dismiss();
                break;
        }
    }
}
