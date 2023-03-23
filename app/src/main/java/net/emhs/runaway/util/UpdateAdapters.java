package net.emhs.runaway.util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.emhs.runaway.R;
import net.emhs.runaway.adapters.AthleteListAdapter;
import net.emhs.runaway.adapters.RecordListAdapter;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.dialogs.RecordAddDialog;
import net.emhs.runaway.dialogs.RecordEditDialog;
import net.emhs.runaway.dialogs.RecordViewDialog;

public class UpdateAdapters {

    public static void updateRecordAdapter(Dialog dialog, Athlete athlete) {
        RecordListAdapter adapter = new RecordListAdapter(dialog.getContext());
        RecyclerView recordListView = null;

        if (dialog instanceof RecordEditDialog || dialog instanceof RecordAddDialog) {
            recordListView = dialog.findViewById(R.id.record_edit_recycler_view);
        } else if (dialog instanceof RecordViewDialog) {
            recordListView = dialog.findViewById(R.id.record_view_recycler_view);
        }

        recordListView.setLayoutManager(new LinearLayoutManager(dialog.getOwnerActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dialog.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(new ColorDrawable());
        recordListView.addItemDecoration(dividerItemDecoration);
        recordListView.setAdapter(adapter);

        adapter.setRecordMap(MapConverter.fromString(athlete.records));
    }

    public static RecyclerView updateAthleteAdapter(Activity activity) {
        RecyclerView athleteListView = activity.findViewById(R.id.athlete_list_recycler_view);
        AthleteListAdapter adapter = new AthleteListAdapter(activity.getApplicationContext());
        AppDatabase db = AppDatabase.getDbInstance(activity.getApplicationContext());

        athleteListView.setLayoutManager(new LinearLayoutManager(activity));
        athleteListView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        athleteListView.setAdapter(adapter);

        adapter.setAthleteList(db.athleteDao().getAllAthletes());

        return athleteListView;
    }

}
