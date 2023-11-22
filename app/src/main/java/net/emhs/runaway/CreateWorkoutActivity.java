package net.emhs.runaway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.emhs.runaway.adapters.ElementListAdapter;
import net.emhs.runaway.adapters.TypeDropdownAdapter;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Element;
import net.emhs.runaway.db.Workout;
import net.emhs.runaway.util.MapConverter;

import java.util.ArrayList;

public class CreateWorkoutActivity extends AppCompatActivity {

    public ElementListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        ExpandableListView listView = findViewById(R.id.create_workout_list);

        ArrayList<String> list = new ArrayList<>();
        list.add("Select...");
        list.add("Item 1");

        ArrayList<Integer> listInt = new ArrayList<>();

        adapter = new ElementListAdapter(this, listInt);

        listView.setAdapter(adapter);
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(), WorkoutListActivity.class));
    }

    public void addSection(View view) {
        ExpandableListView listView = findViewById(R.id.create_workout_list);
        adapter.addChild(listView);
    }

    public void saveWorkout(View view) {
        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());
        EditText name = findViewById(R.id.create_workout_name);
        adapter.updateElements();
        db.workoutDoa().insertWorkout(new Workout(name.getText().toString(), MapConverter.fromList(adapter.getChildItem())));
        startActivity(new Intent(getApplicationContext(), WorkoutListActivity.class));
    }

}
