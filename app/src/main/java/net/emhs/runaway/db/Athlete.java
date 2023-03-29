package net.emhs.runaway.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import net.emhs.runaway.util.MapConverter;
import net.emhs.runaway.util.Time;

import java.util.HashMap;
import java.util.Map;


@Entity(tableName = "athletes")
public class Athlete {

    public Athlete() {
        records = MapConverter.fromMap(new HashMap<>());
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="uid")
    public int uid;

    @ColumnInfo(name="name")
    public String name;
    @ColumnInfo(name="records")
    public String records;

    public void addRecord(int distanceInput, @NonNull Time time) {
        Map<Integer, Time> map = MapConverter.fromString(records);
        map.put(distanceInput, time);
        records = MapConverter.fromMap(map);
    }
}
