package net.emhs.runaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import net.emhs.runaway.dialogs.QuickMenuDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QuickMenuDialog dialog = new QuickMenuDialog(this);

        findViewById(R.id.main_hamburger).setOnClickListener(v -> dialog.show());


        //Click listener for going to athlete list activity
        findViewById(R.id.main_navigation_athlete_list).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), AthleteListActivity.class));
            overridePendingTransition(R.anim.snap, R.anim.snap);
        });
        //findViewById(R.id.main_workout_list).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), WorkoutListActivity.class)));
    }
}