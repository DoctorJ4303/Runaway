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
import net.emhs.runaway.db.Athlete;

import java.util.List;

public class AthleteListAdapter extends RecyclerView.Adapter<AthleteListAdapter.MyViewHolder> {

    private final Context context;
    private List<Athlete> athletes;

    public AthleteListAdapter(Context context) {
        this.context = context;
    }

    public void setAthleteList(List<Athlete> athletes) {
        this.athletes = athletes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.athlete_list, parent, false); // Inflates athlete_list.xml
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(this.athletes.get(position).name); // Sets the name of the athlete in the list
        holder.pfp.setImageResource(R.drawable.profile_picture); // Sets image programmatically because it wasn't storing in the xml
    }

    @Override
    public int getItemCount() {
        return this.athletes == null ? 0 : this.athletes.size(); // Returns length of list
    }

    //View Holder for AthleteListAdapter
    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView pfp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.athlete_list_name);
            pfp = itemView.findViewById(R.id.athlete_list_pfp);
        }
    }

}
