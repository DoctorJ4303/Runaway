package net.emhs.runaway.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.emhs.runaway.R;
import net.emhs.runaway.db.Workout;

import java.util.List;

public class WorkoutListAdapter extends RecyclerView.Adapter<WorkoutListAdapter.MyViewHolder> {

    private final Context context;
    private List<Workout> workouts;

    public WorkoutListAdapter(Context context) {
        this.context = context;
    }

    public void setWorkoutList(List<Workout> workouts) {
        this.workouts = workouts;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(this.workouts.get(position).name);
        holder.pfp.setImageResource(R.drawable.profile_picture);
    }

    @Override
    public int getItemCount() {
        return this.workouts == null ? 0 : this.workouts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView pfp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.workout_list_name);
            pfp = itemView.findViewById(R.id.workout_list_pfp);
        }
    }
}
