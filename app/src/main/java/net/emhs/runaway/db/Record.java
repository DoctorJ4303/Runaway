package net.emhs.runaway.db;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.ParseException;
import java.util.ArrayList;

public class Record {

    @JsonProperty("Pace")
    public String pace;
    @JsonProperty("Distance")
    public int distance;

    public Record(int distance, String pace) throws ParseException {
        this.distance = distance;
        try {
            new Time(pace);
            this.pace = pace;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Record () { }

    public Record getRecord(ArrayList<Record> list, int d) {
        Record record;
        for (Record r : list)
            if (r.distance==d) return r;
        return null;
    }

    private class Time {
        private int minutes;
        private int seconds;
        private int milliseconds;

        public Time (String timeIn) throws ParseException {
            switch (formatChecker(timeIn)) { // Checks format
                case 1: // Includes minute
                    this.minutes = Integer.parseInt(timeIn.split(":")[0]);
                    this.seconds = Integer.parseInt(timeIn.split(":")[1].split("\\.")[0]);
                    this.milliseconds = Integer.parseInt(timeIn.split("\\.")[1]);
                    break;
                case 2: // Only seconds
                    this.minutes = 0;
                    this.seconds = Integer.parseInt(timeIn.split("\\.")[0]);
                    this.milliseconds = Integer.parseInt(timeIn.split("\\.")[1]);
                    break;
                case -1: // No format
                    throw new ParseException("Good luck man...", 0);
            }
        }

        private int formatChecker(String format) {
            return format.contains(".") && format.contains(":") ? 1 : format.contains(".") ? 2 : -1;
        }


        @NonNull
        @Override
        public String toString() { // Converts Time to String so it's readable
            String stringMinutes = String.valueOf(this.minutes);
            String stringSeconds = this.seconds<10 ? "0" + this.seconds : String.valueOf(this.seconds);
            String stringMillis = this.milliseconds<10 ? "0" + this.milliseconds : String.valueOf(this.milliseconds);

            return  stringMinutes + ":" + stringSeconds + "." + stringMillis;
        }
    }

}
