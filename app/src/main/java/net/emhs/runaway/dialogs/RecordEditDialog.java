package net.emhs.runaway.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.util.MapConverter;
import net.emhs.runaway.util.RecyclerItemClickListener;
import net.emhs.runaway.util.UpdateAdapters;

import java.util.ArrayList;

public class RecordEditDialog extends Dialog implements View.OnClickListener {

    private Activity activity;
    private Athlete athlete;
    private AppDatabase db;
    private AthleteEditDialog parentDialog;
    private String currentRecords;

    public RecordEditDialog(Activity activity, Athlete athlete, AthleteEditDialog parentDialog) {
        super(activity, R.style.Theme_Runaway_Popup);
        this.activity = activity;
        this.db = AppDatabase.getDbInstance(activity);
        this.athlete = athlete;
        this.parentDialog = parentDialog;
        this.currentRecords = athlete.records;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_record_edit);

        UpdateAdapters.updateRecordAdapter(this, athlete);

        findViewById(R.id.record_edit_add).setOnClickListener(this);
        findViewById(R.id.record_edit_back).setOnClickListener(this);

        RecyclerView recyclerView = findViewById(R.id.record_edit_recycler_view);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(activity, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println(new ArrayList<>(MapConverter.fromString(athlete.records).keySet()).get(position));
                int distance = new ArrayList<>(MapConverter.fromString(athlete.records).keySet()).get(position);
                RecordDataEditDialog dataEditDialog = new RecordDataEditDialog(activity, athlete, RecordEditDialog.this, distance);
                dataEditDialog.show();
            }
        }));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.record_edit_add:
                RecordDataAddDialog dialog = new RecordDataAddDialog(activity, athlete, this);
                dialog.show();
                break;
            case R.id.record_edit_back:
                if (!athlete.records.equals(currentRecords)) {
                    parentDialog.edited = true;
                }
                parentDialog.show();
                dismiss();
                break;
        }
    }
}
