package net.emhs.runaway;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Workout;
import net.emhs.runaway.util.MapConverter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Click listener for going to athlete list activity
        findViewById(R.id.main_athlete_list).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AthleteListActivity.class)));
        findViewById(R.id.main_workout_list).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), WorkoutListActivity.class)));
    }
}