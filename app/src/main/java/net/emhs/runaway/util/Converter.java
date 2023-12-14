package net.emhs.runaway.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.emhs.runaway.db.Element;
import net.emhs.runaway.db.Record;
import net.emhs.runaway.db.Time;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Converter {

    public static String toString(Object o) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String s = objectMapper.writeValueAsString(o);
            return s;
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }

    public static String toString(ArrayList<?> list) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayList<String> temp = new ArrayList<>();
            for (Object o : list) {
                temp.add(objectMapper.writeValueAsString(o));
            }
            return objectMapper.writeValueAsString(temp);
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<Element> toElementList(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayList<String> list = (objectMapper.readValue(s, ArrayList.class));
            ArrayList<Element> temp = new ArrayList<>();
            for (String item : list) {
                temp.add(objectMapper.readValue(item, Element.class));
            }
            return temp;
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static Map<Integer, Time> toMap(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return (objectMapper.readValue(s, Map.class));
        } catch (Exception e){
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public static ArrayList<Record> toRecordList(String s) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            ArrayList<String> list = !s.equals("{}") ? (objectMapper.readValue(s, (new ArrayList<String>()).getClass())) : new ArrayList<>();
            ArrayList<Record> temp = new ArrayList<>();
            for (String item : list) {
                temp.add(objectMapper.readValue(item, Record.class));
            }
            return temp;
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
