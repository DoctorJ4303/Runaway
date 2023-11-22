package net.emhs.runaway.db;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;

import net.emhs.runaway.util.MapConverter;
import net.emhs.runaway.util.Time;

public class Element {

    public int distance;
    public int pace;

    public Element(int distance, int pace) {
        this.distance = distance;
        this.pace = pace;
    }
}
