package net.emhs.runaway.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.emhs.runaway.R;
import net.emhs.runaway.util.Time;

import java.util.ArrayList;
import java.util.Map;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.MyViewHolder> {

    private final Context context;
    private Map<Integer, Time> records;

    public RecordListAdapter(Context context) {
        this.context = context;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setRecordMap(Map<Integer, Time> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.record_list, parent, false); // Inflates list

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Sets distance inside inflated list
        holder.distance.setText(context.getString(R.string.record_list_adapter_distance, new ArrayList<>(this.records.keySet()).get(position)));
        holder.time.setText(new ArrayList<>(this.records.values()).get(position).toString()); // Sets time
    }

    @Override
    public int getItemCount() {
        return records==null ? 0 : this.records.size(); // Returns item count
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView distance;
        TextView time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.distance = itemView.findViewById(R.id.record_list_distance); // Finds distance text in inflated list
            this.time = itemView.findViewById(R.id.record_list_time); // Finds time text
        }
    }
}
