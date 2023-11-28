package net.emhs.runaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.emhs.runaway.adapters.AthleteListAdapter;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.dialogs.AthleteAddDialog;
import net.emhs.runaway.dialogs.AthleteViewDialog;

public class AthleteListActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_list);

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());

        AthleteListAdapter adapter = new AthleteListAdapter(getApplicationContext(), 0);
        RecyclerView list = findViewById(R.id.athlete_list_recycler_view);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        list.setAdapter(adapter);

        findViewById(R.id.athlete_list_navigation_home).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.snap, R.anim.snap);
        });

        /*this.db = AppDatabase.getDbInstance(getApplicationContext()); // Database object
        RecyclerView athleteListView = UpdateAdapters.updateAthleteAdapter(this); // Initializes athlete list and sets it to a variable to add a click listener

        // Athlete list click listener. Checks if an athlete is clicked in the list
        athleteListView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), (view, position) -> viewAthlete(db.athleteDao().getAllAthletes().get(position))));*/

    }

    // Method for back arrow. Goes back to main activity
    public void back(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    // Method for creating an athlete
    public void addAthlete(View view) {
        AthleteAddDialog dialog = new AthleteAddDialog(AthleteListActivity.this);
        dialog.show();
    }

    // Method for viewing an athlete
    public void viewAthlete(Athlete athlete) {
        AthleteViewDialog dialog = new AthleteViewDialog(AthleteListActivity.this, athlete);
        dialog.show();
    }
}