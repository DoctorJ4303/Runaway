package net.emhs.runaway.util;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import net.emhs.runaway.db.Athlete;

public class AthleteItemLayout extends ConstraintLayout {
    public AthleteItemLayout(@NonNull Context context) {
        super(context);
    }

    public AthleteItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return true;

            case MotionEvent.ACTION_UP:
                performClick();
                return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        super.performClick();

        return true;
    }
}
