package net.emhs.runaway.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;

import java.util.List;

public class AthleteListAdapter extends RecyclerView.Adapter<AthleteListAdapter.MyViewHolder> {

    private final Context context;
    private List<Athlete> athletes;
    private int type; // 0 = small; 1 = view; 2 = edit

    public AthleteListAdapter(Context context, int type) {
        this.context = context;
        this.type = type;
        this.athletes = AppDatabase.getDbInstance(context.getApplicationContext()).athleteDao().getAllAthletes();
        System.out.println(athletes);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setAthleteList(List<Athlete> athletes) {
        this.athletes = athletes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.athlete_item_small, parent, false);
        // Inflates athlete_list.xml
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return this.athletes == null ? 0 : this.athletes.size(); // Returns length of list
    }

    //View Holder for AthleteListAdapter
    public static class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
