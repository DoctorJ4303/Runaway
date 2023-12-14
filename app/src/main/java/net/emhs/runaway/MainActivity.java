package net.emhs.runaway;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.dialogs.QuickMenuDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static DisplayMetrics display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setScreenSize(this);

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());

        List<Athlete> athletes = db.athleteDao().getAllAthletes();
        /*for (Athlete a : athletes) {
            try {
                a.addRecord(5000, new Time("17:22.65"));
                a.addRecord(800, new Time("2:01.38"));
                db.athleteDao().updateRecords(a.records, a.uid);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }*/

        QuickMenuDialog dialog = new QuickMenuDialog(this);

        findViewById(R.id.main_hamburger).setOnClickListener(v -> dialog.show());


        //Click listener for going to athlete list activity
        findViewById(R.id.main_navigation_athlete_list).setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), AthleteListActivity.class));
            overridePendingTransition(R.anim.snap, R.anim.snap);
        });
        //findViewById(R.id.main_workout_list).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), WorkoutListActivity.class)));
    }

    public static void setScreenSize (Activity activity) {
        display = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(display);
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
        }
        return super.dispatchTouchEvent( event );
    }
}