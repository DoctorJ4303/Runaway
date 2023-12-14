package net.emhs.runaway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import net.emhs.runaway.adapters.AthleteLayoutManager;
import net.emhs.runaway.adapters.AthleteListAdapter;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.dialogs.AthleteAddDialog;
import net.emhs.runaway.dialogs.AthleteViewDialog;
import net.emhs.runaway.util.RecyclerItemClickListener;
import net.emhs.runaway.util.UpdateAdapters;

import java.util.Map;
import java.util.Objects;

public class AthleteListActivity extends AppCompatActivity {

    private AppDatabase db;

    private AthleteListAdapter adapter;
    private AthleteLayoutManager manager;

    @SuppressLint({"NotifyDataSetChanged", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_athlete_list);

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());

        RecyclerView list = findViewById(R.id.athlete_list_recycler_view);
        manager = new AthleteLayoutManager(this);
        adapter = new AthleteListAdapter(this, manager);
        list.setLayoutManager(manager);
        list.addItemDecoration(UpdateAdapters.createVerticalDivider(this, 15.0));
        list.setAdapter(adapter);


        findViewById(R.id.athlete_list_navigation_home).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            overridePendingTransition(R.anim.snap, R.anim.snap);
        });

        findViewById(R.id.athlete_list_add_new).setOnClickListener(view -> adapter.addNew());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            View v1 = manager.findViewByPosition(adapter.getCurrentlySelected());
            if (v1 != null) {
                Rect rect = new Rect();
                v1.getGlobalVisibleRect(rect);
                System.out.println(rect);
                if (!rect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    adapter.setType(adapter.getCurrentlySelected(), AthleteListAdapter.type.TYPE_SMALL);
                    adapter.notifyItemChanged(adapter.getCurrentlySelected());
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }
}