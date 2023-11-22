package net.emhs.runaway.util;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.emhs.runaway.db.Element;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
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

    @TypeConverter
    public static String fromList(Object list) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String s = objectMapper.writeValueAsString(list);
            return s;
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }

    @TypeConverter
    public static ArrayList<Element> jsonToObject (String s) throws JsonProcessingException {
        Type mapType = new TypeToken<ArrayList<Element>>(){}.getType();
        return new Gson().fromJson(s, mapType);
    }

    public static String toJson(Object o) {
        Gson gson = new Gson();
        return gson.toJson(o);
    }
}
