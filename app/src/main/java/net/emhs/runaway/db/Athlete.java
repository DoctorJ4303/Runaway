package net.emhs.runaway.db;

import android.provider.ContactsContract;
import android.text.format.DateFormat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import net.emhs.runaway.util.MapConverter;
import net.emhs.runaway.util.Time;

import java.text.ParseException;
import java.util.Map;


@Entity(tableName = "athletes")
public class Athlete {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="uid")
    public int uid;

    @ColumnInfo(name="name")
    public String name;
    @ColumnInfo(name="records")
    public String records;

    public void addRecord(int distanceInput, Time time) throws ParseException, NumberFormatException {
        Map<Integer, Time> map = MapConverter.fromString(records);

        if (time != null) {
            map.put(distanceInput, time);
            records = MapConverter.fromMap(map);
        } else {
            throw new ParseException("Doesn't match any format", 0);
        }
    }

}
