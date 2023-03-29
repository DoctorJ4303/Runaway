package net.emhs.runaway.util;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Map;

public class MapConverter {

    @TypeConverter // From String to Map
    public static Map<Integer, Time> fromString(String json) {
        Type mapType = new TypeToken<Map<Integer, Time>>(){}.getType();
        return new Gson().fromJson(json, mapType);
    }

    @TypeConverter // From Map to String
    public static String fromMap(Map<Integer, Time> map) {
        Gson gson = new Gson();
        return gson.toJson(map);
    }
}
