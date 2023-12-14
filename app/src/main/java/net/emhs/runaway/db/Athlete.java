package net.emhs.runaway.db;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import net.emhs.runaway.util.Converter;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity(tableName = "athletes")
public class Athlete {

    public Athlete() {
        records = Converter.toString(new HashMap<>());
        this.name = "Athlete";
        this.description = "Description";
        this.records = "{}";
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="uid")
    public int uid;

    @ColumnInfo(name="name")
    public String name;
    @ColumnInfo(name="description")
    public String description;
    @ColumnInfo(name="records")
    public String records;

    public void addRecord(int distanceInput, @NonNull String time) throws ParseException {
        List<Record> recordList = Converter.toRecordList(records);
        recordList.add(new Record(distanceInput, time));
        records = Converter.toString(recordList);
    }
}
