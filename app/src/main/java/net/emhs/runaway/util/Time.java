package net.emhs.runaway.util;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.util.Arrays;

public class Time {

    private int minutes;
    private int seconds;
    private int milliseconds;

    public Time (String timeIn) throws ParseException{
        switch (formatChecker(timeIn)) {
            case 1:
                this.minutes = Integer.parseInt(timeIn.split(":")[0]);
                this.seconds = Integer.parseInt(timeIn.split(":")[1].split("\\.")[0]);
                this.milliseconds = Integer.parseInt(timeIn.split("\\.")[1]);
                break;
            case 2:
                this.minutes = 0;
                this.seconds = Integer.parseInt(timeIn.split("\\.")[0]);
                this.milliseconds = Integer.parseInt(timeIn.split("\\.")[1]);
                break;
            case -1:
                throw new ParseException("Good luck man...", 0);
        }
    }

    private int formatChecker(String format) {

        System.out.println(Arrays.toString(format.split("\\.")));
        if (format.contains(":") && format.contains(".")) {
            try {
                String ms = format.split("\\.")[1];
                String s = format.split(":")[1].split("\\.")[0];
                String m = format.split(":")[0];
                return 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (format.contains(".")) {
            try {
                String ms = format.split("\\.")[1];
                String s = format.split("\\.")[0];
                String m = "0";
                return 2;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }


    @NonNull
    @Override
    public String toString() {
        String stringMinutes = String.valueOf(this.minutes);
        String stringSeconds = this.seconds<10 ? "0" + this.seconds : String.valueOf(this.seconds);
        String stringMillis = this.milliseconds<10 ? "0" + this.milliseconds : String.valueOf(this.milliseconds);

        return  stringMinutes + ":" + stringSeconds + "." + stringMillis;
    }
}
