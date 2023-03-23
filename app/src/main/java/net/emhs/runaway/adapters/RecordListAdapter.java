package net.emhs.runaway.adapters;

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

    private Context context;
    private Map<Integer, Time> records;

    public RecordListAdapter(Context context) {
        this.context = context;
    }

    public void setRecordMap(Map<Integer, Time> records) {
        this.records = records;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.record_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.distance.setText(new ArrayList<>(this.records.keySet()).get(position) + "m");
        holder.time.setText(new ArrayList<>(this.records.values()).get(position).toString());
    }

    @Override
    public int getItemCount() {
        if (records == null) {
            return 0;
        }
        return this.records.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView distance;
        TextView time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            distance = itemView.findViewById(R.id.record_list_distance);
            time = itemView.findViewById(R.id.record_list_time);
        }
    }
}
