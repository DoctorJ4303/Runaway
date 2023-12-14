package net.emhs.runaway.adapters;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class AthleteLayoutManager extends LinearLayoutManager {

    private boolean scrollable;

    public AthleteLayoutManager(Context context) {
        super(context);
        this.scrollable = true;
    }

    public void setScrollable(boolean scrollable) {
        this.scrollable = scrollable;
    }

    @Override
    public boolean canScrollVertically() {
        return super.canScrollVertically() && scrollable;
    }
}
