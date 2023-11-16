package net.emhs.runaway.db;

import android.sax.ElementListener;

import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import net.emhs.runaway.util.MapConverter;
import net.emhs.runaway.util.Time;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

public class Workout {

    private String title;
    private Athlete[] athletes;
    private Element[] elements;

    public Workout(String title, Athlete[] athletes, Element[] elements) {
        this.title = title;
        this.athletes = athletes;
        this.elements = elements;
    }

    public Paragraph toTable() throws ParseException {
        Paragraph paragraph = new Paragraph();
        Table titleTable = new Table(1);
        float tableHeight = 0f;
        titleTable.setFixedPosition(36f, 531f, 770f);
        Table table = new Table(elements.length+1);
        float averageWidth = 770f/elements.length+1;

        Cell titleCell = new Cell();
        titleCell.add(new Paragraph(title));
        titleCell.setWidth(824f).setHeight(20f);
        titleTable.addHeaderCell(titleCell);

        table.addCell(new Cell().add(new Paragraph(" ")).setWidth(averageWidth).setHeight(20f));
        for (Element e : elements) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(e.distance))).setWidth(averageWidth).setHeight(20f));
        }
        tableHeight += 24;

        String times[] = new String[athletes.length];
        for (Athlete a : athletes) {
            for (Element e : elements) {
                System.out.println(paceFromDistance(a, e.pace));
            }
        }

        for (Athlete a : athletes) {
            table.addCell(new Cell().add(new Paragraph(a.name + "\n" + "1:00.00")).setWidth(averageWidth).setHeight(40f));
            for (Element e : elements) {
                table.addCell(new Cell().add(new Paragraph(" ")).setWidth(averageWidth).setHeight(40f));
            }
            tableHeight += 44;
        }

        System.out.println(table.getNumberOfRows());

        table.setFixedPosition(36f,531f-tableHeight,770f);
        paragraph.add(titleTable);
        paragraph.add(table);

        return paragraph;
    }

    public Time paceFromDistance(Athlete athlete, int distance) throws ParseException {
        if (athlete.records.isEmpty()) {
            return null;
        }

        int minutes;
        int seconds;
        int milliseconds;

        ArrayList<Integer> lesser = new ArrayList<>();
        ArrayList<Integer> greater = new ArrayList<>();

        for (int i : MapConverter.fromString(athlete.records).keySet()) {
            if (i<distance)
                lesser.add(i);
            else
                greater.add(i);
        }

        Collections.sort(lesser);
        Collections.sort(greater);

        System.out.println(lesser);
        System.out.println(greater);

        return new Time("0:00.00");
    }
}
