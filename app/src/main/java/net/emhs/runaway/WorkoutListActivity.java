package net.emhs.runaway;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.pdfa.PdfADocument;

import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.db.Element;
import net.emhs.runaway.db.Workout;
import net.emhs.runaway.util.Time;
import net.emhs.runaway.util.UpdateAdapters;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;

public class WorkoutListActivity extends AppCompatActivity{

    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_list);

        this.db = AppDatabase.getDbInstance(getApplicationContext());
        UpdateAdapters.updateWorkoutAdapter(this);
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            if (i < 2)
                i = 0;
            else if (i > 18 && i < 22)
                i = 20;
            else if (i > 38 && i < 42)
                i = 40;
            else if (i > 58 && i < 62)
                i = 60;
            else if (i > 78 && i < 82)
                i = 80;
            else if (i > 98)
                i = 100;

            seekBar.setProgress(i);

            int progress = (int)((((i-(20*(Math.floor((double)(i-1)/20))))*5)+100)*Math.pow(2,Math.floor((double) (i-1)/20)));
        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    public void createPDF () throws IOException, ParseException {
        //getFilesDir() + "/workout_pdfs/test.pdf"
        /*File pdfFile = new File(getFilesDir() + "workout_pdfs/test.pdf");
        PdfWriter writer = new PdfWriter(pdfFile);
        PdfDocument pdf = new PdfDocument(writer);
        pdf.setDefaultPageSize(PageSize.A4.rotate());
        Document document = new Document(pdf);

        AppDatabase db = AppDatabase.getDbInstance(this.getApplicationContext());

        Element[] elements = new Element[]{new Element(300, 1600), new Element(300, 1600), new Element(300, 1600), new Element(300, 1600), new Element(300, 1600), new Element(300, 1600)};

        Workout workout = new Workout("Test Title", db.athleteDao().getAllAthletes().toArray(new Athlete[0]), elements);
        document.add(workout.toTable());

        document.close();*/


    }

    public double calculatePace(Athlete athlete) {
        return 0.0;
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void addWorkout(View view) {
        startActivity(new Intent(getApplicationContext(), CreateWorkoutActivity.class));
    }


}