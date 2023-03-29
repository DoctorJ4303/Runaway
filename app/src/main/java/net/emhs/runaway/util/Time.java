package net.emhs.runaway.util;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.util.Arrays;

public class Time {

    // Global variables
    private int minutes;
    private int seconds;
    private int milliseconds;

    public Time (String timeIn) throws ParseException{
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

    // Checks format (Not the best system but it works)
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
