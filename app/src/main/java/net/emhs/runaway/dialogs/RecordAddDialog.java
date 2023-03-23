package net.emhs.runaway.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.util.UpdateAdapters;


public class RecordAddDialog extends Dialog implements View.OnClickListener {

    private Athlete athlete;
    private AppDatabase db;
    private Activity activity;


    public RecordAddDialog(Activity activity, Athlete athlete) {
        super(activity, R.style.Theme_Runaway_Popup);
        this.activity = activity;
        this.athlete = athlete;
        this.db = AppDatabase.getDbInstance(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_edit);

        UpdateAdapters.updateRecordAdapter(this, athlete);

        findViewById(R.id.record_edit_add).setOnClickListener(this);
        findViewById(R.id.record_edit_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_edit_add:
                RecordDataAddDialog dialog = new RecordDataAddDialog(activity, athlete, this);
                dialog.show();
                break;
            case R.id.record_edit_back:
                dismiss();
                break;
        }
    }
}

