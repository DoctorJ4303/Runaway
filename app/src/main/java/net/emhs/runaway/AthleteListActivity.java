package net.emhs.runaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.dialogs.AthleteAddDialog;
import net.emhs.runaway.dialogs.AthleteViewDialog;
import net.emhs.runaway.util.RecyclerItemClickListener;
import net.emhs.runaway.util.UpdateAdapters;

public class AthleteListActivity extends AppCompatActivity {

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_list);

        this.db = AppDatabase.getDbInstance(getApplicationContext()); // Database object
        RecyclerView athleteListView = UpdateAdapters.updateAthleteAdapter(this); // Initializes athlete list and sets it to a variable to add a click listener

        // Athlete list click listener. Checks if an athlete is clicked in the list
        athleteListView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), athleteListView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                viewAthlete(db.athleteDao().getAllAthletes().get(position));
            }
            }));

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