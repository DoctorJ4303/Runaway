package net.emhs.runaway.db;

import android.view.View;

import net.emhs.runaway.util.Time;

public class Element {

    public int distance;
    public int pace;
    public int distanceProgress;
    public int paceProgress;

    public View view;

    public Element(int distanceProgress, int paceProgress) {
        this.distanceProgress = distanceProgress;
        this.paceProgress = paceProgress;
    }
}
