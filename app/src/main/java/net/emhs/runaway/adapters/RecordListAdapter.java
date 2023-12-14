package net.emhs.runaway.adapters;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.db.Record;
import net.emhs.runaway.util.Converter;
import net.emhs.runaway.db.Time;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RecordListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    public enum recordType {
        VIEW,
        EDIT
    }
    private final AppDatabase db;
    private final recordType type;
    private final Context context;
    private ArrayList<Record> records;
    private Athlete athlete;

    public RecordListAdapter(Context context, Athlete athlete, recordType type) {
        this.db = AppDatabase.getDbInstance(context.getApplicationContext());
        this.context = context;
        this.athlete = athlete;
        this.records = Converter.toRecordList(athlete.records);
        this.type = type;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.record_item_view, parent, false);
        DisplayMetrics display = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(display);

        switch (type) {
            case VIEW:
                view = LayoutInflater.from(context).inflate(R.layout.record_item_view, parent, false);
                view.getLayoutParams().height = (int) (display.heightPixels * (26.0 / 752.0));
                return new RecordViewViewHolder(view);
            case EDIT:
                view = LayoutInflater.from(context).inflate(R.layout.record_item_edit, parent, false);
                view.getLayoutParams().height = (int) (display.heightPixels * (26.0 / 752.0));
                return new RecordEditViewHolder(view);
        }
        return new RecordViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ArrayList<Record> recordList = Converter.toRecordList(athlete.records);
        switch (type) {
            case VIEW:
                RecordViewViewHolder recordViewHolder = (RecordViewViewHolder) holder;
                recordViewHolder.distance.setText(String.valueOf(recordList.get(position).distance));
                recordViewHolder.time.setText(recordList.get(position).pace);
                break;
            case EDIT:
                RecordEditViewHolder recordEditHolder = (RecordEditViewHolder) holder;
                recordEditHolder.distance.setText(String.valueOf(recordList.get(position).distance));
                recordEditHolder.time.setText(recordList.get(position).pace);
                break;
        }
    }

    public void addNew() {
        try {
            records.add(0, new Record(testDistances(), "0:00.00"));
            athlete.records = Converter.toString(records);
            db.athleteDao().updateRecords(athlete.records, athlete.uid);
            notifyDataSetChanged();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private int testDistances () {
        int[] distances = {55, 100, 200, 300, 400, 500, 800, 1000, 1600, 3200, 5000};
        int maxDistance = 55;
        ArrayList<Record> temp = new ArrayList<>(records);
        for (int i = 0; i < distances.length-1; i++) {
            for (Record r : records) {
                if (r.distance==distances[i]) {
                    maxDistance = distances[i+1];
                    break;
                }
            }
        }
        return maxDistance;
    }

    @Override
    public int getItemCount() {
        return records==null ? 0 : this.records.size(); // Returns item count
    }

    public static class RecordViewViewHolder extends RecyclerView.ViewHolder {

        TextView distance;
        TextView time;

        public RecordViewViewHolder(@NonNull View itemView) {
            super(itemView);
            this.distance = itemView.findViewById(R.id.record_item_view_distance); // Finds distance text in inflated list
            this.time = itemView.findViewById(R.id.record_item_view_time); // Finds time text
        }
    }

    public static class RecordEditViewHolder extends RecyclerView.ViewHolder {

        TextView distance;
        TextView time;

        public RecordEditViewHolder(@NonNull View itemView) {
            super(itemView);
            this.distance = itemView.findViewById(R.id.record_item_view_distance); // Finds distance text in inflated list
            this.time = itemView.findViewById(R.id.record_item_view_time); // Finds time text
        }
    }
}
