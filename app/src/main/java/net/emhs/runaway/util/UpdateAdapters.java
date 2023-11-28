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
import net.emhs.runaway.adapters.WorkoutListAdapter;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.dialogs.RecordAddDialog;
import net.emhs.runaway.dialogs.RecordEditDialog;
import net.emhs.runaway.dialogs.RecordViewDialog;

public class UpdateAdapters {

    public static void updateRecordAdapter(Dialog dialog, Athlete athlete) {
        RecordListAdapter adapter = new RecordListAdapter(dialog.getContext()); // New RecordListAdapter
        RecyclerView recordListView = null; // Initializes new RecyclerView

        // Sets dialog view based on class (RecordEditDialog or RecordAddDialog -> record_edit_recycler_view / RecordViewDialog -> record_view_recycler_view)
        if (dialog instanceof RecordEditDialog || dialog instanceof RecordAddDialog) {
            recordListView = dialog.findViewById(R.id.record_edit_recycler_view);
        } else if (dialog instanceof RecordViewDialog) {
            recordListView = dialog.findViewById(R.id.record_view_recycler_view);
        }

        assert recordListView != null; // Checks if RecyclerView is inflated
        recordListView.setLayoutManager(new LinearLayoutManager(dialog.getOwnerActivity())); // Sets layout manager
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(dialog.getContext(), DividerItemDecoration.VERTICAL); // Creates new divider
        dividerItemDecoration.setDrawable(new ColorDrawable()); // Sets divider
        recordListView.addItemDecoration(dividerItemDecoration); // Adds divider
        recordListView.setAdapter(adapter); // Sets RecyclerView's adapter

        adapter.setRecordMap(MapConverter.fromString(athlete.records)); // Sets map to adapter
    }

    public static RecyclerView updateAthleteAdapter(Activity activity) {
        RecyclerView athleteListView = null; // Sets dialog
        AthleteListAdapter adapter = new AthleteListAdapter(activity.getApplicationContext(), 0); /// New adapter
        AppDatabase db = AppDatabase.getDbInstance(activity.getApplicationContext()); // Sets AppDatabase instance

        athleteListView.setLayoutManager(new LinearLayoutManager(activity)); // Sets layout manager
        athleteListView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)); // Creates a new divider
        athleteListView.setAdapter(adapter); // Applies divider

        adapter.setAthleteList(db.athleteDao().getAllAthletes()); // Sets list to athlete list from database

        return athleteListView;
    }

    public static RecyclerView updateWorkoutAdapter(Activity activity) {
        RecyclerView workoutListView = activity.findViewById(R.id.workout_list_recycler_view);
        WorkoutListAdapter adapter = new WorkoutListAdapter(activity.getApplicationContext());
        AppDatabase db = AppDatabase.getDbInstance(activity.getApplicationContext());

        workoutListView.setLayoutManager(new LinearLayoutManager(activity));
        workoutListView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
        workoutListView.setAdapter(adapter);

        adapter.setWorkoutList(db.workoutDoa().getAllWorkouts());

        return workoutListView;
    }

}
