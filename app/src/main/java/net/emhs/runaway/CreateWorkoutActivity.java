package net.emhs.runaway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.emhs.runaway.adapters.ElementListAdapter;
import net.emhs.runaway.adapters.TypeDropdownAdapter;

import java.util.ArrayList;

public class CreateWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        Spinner dropdown = findViewById(R.id.spinner);
        ExpandableListView listView = findViewById(R.id.create_workout_list);

        ArrayList<String> list = new ArrayList<>();
        list.add("Select...");
        list.add("Item 1");

        ArrayList<Integer> listInt = new ArrayList<>();
        listInt.add(0);
        listInt.add(1);
        listInt.add(2);


        dropdown.setAdapter(new TypeDropdownAdapter(this, R.layout.type_list, R.id.type_list_text, list));
        listView.setAdapter(new ElementListAdapter(this, listInt));

        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                System.out.println("HERE!!");
                return false;
            }
        });
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(), WorkoutListActivity.class));
    }
}
