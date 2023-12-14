package net.emhs.runaway.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import net.emhs.runaway.R;
import net.emhs.runaway.db.AppDatabase;
import net.emhs.runaway.db.Athlete;
import net.emhs.runaway.db.Record;
import net.emhs.runaway.util.Converter;
import net.emhs.runaway.util.UpdateAdapters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AthleteListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public enum type {
        TYPE_SMALL,
        TYPE_VIEW,
        TYPE_EDIT
    }

    private final AppDatabase db;
    private static ArrayList<type> itemClickedState;
    private final Context context;
    private final List<Athlete> athletes;
    private final AthleteLayoutManager mLayoutManager;

    public AthleteListAdapter(Context context, AthleteLayoutManager layoutManager) {
        this.db = AppDatabase.getDbInstance(context.getApplicationContext());
        this.context = context;
        this.athletes = AppDatabase.getDbInstance(context.getApplicationContext()).athleteDao().getAllAthletes();
        this.mLayoutManager = layoutManager;

        itemClickedState = new ArrayList<>();
        for (int i = 0; i < athletes.size(); i++)
            itemClickedState.add(i, type.TYPE_SMALL);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        DisplayMetrics display = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(display);

        switch (viewType) {
            default: // The equivalent of case 0:
                view = LayoutInflater.from(context).inflate(R.layout.athlete_item_small, parent, false);
                view.getLayoutParams().height = (int) (display.heightPixels * (100.0 / 752.0));
                return new SmallAthleteViewHolder(view);
            case 1:
                view = LayoutInflater.from(context).inflate(R.layout.athlete_item_view, parent, false);
                view.getLayoutParams().height = (int) (display.heightPixels * (400.0 / 752.0));
                return new ViewAthleteViewHolder(view);
            case 2:
                view = LayoutInflater.from(context).inflate(R.layout.athlete_item_edit, parent, false);
                view.getLayoutParams().height = (int) (display.heightPixels * (400.0 / 752.0));
                return new EditAthleteViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        switch(itemClickedState.get(position)) {
            case TYPE_SMALL:
                SmallAthleteViewHolder smallViewHolder = (SmallAthleteViewHolder)holder;

                smallViewHolder.name.setText(athletes.get(position).name);
                smallViewHolder.description.setText(athletes.get(position).description);
                smallViewHolder.itemView.setOnClickListener(view -> {
                    mLayoutManager.scrollToPositionWithOffset(position, 0);
                    hideKeyboard(view);
                    setType(position, type.TYPE_VIEW);
                });
                break;
            case TYPE_VIEW:
                ViewAthleteViewHolder viewViewHolder = (ViewAthleteViewHolder)holder;

                viewViewHolder.record_list.setAdapter(new RecordListAdapter(context, athletes.get(position), RecordListAdapter.recordType.VIEW));
                viewViewHolder.name.setText(athletes.get(position).name);
                viewViewHolder.description.setText(athletes.get(position).description);
                viewViewHolder.close.setOnClickListener(view -> setType(position, type.TYPE_SMALL));
                viewViewHolder.edit.setOnClickListener(view -> setType(position, type.TYPE_EDIT));
                break;
            case TYPE_EDIT:
                EditAthleteViewHolder editViewHolder = (EditAthleteViewHolder)holder;
                RecordListAdapter adapter = new RecordListAdapter(context, athletes.get(position), RecordListAdapter.recordType.EDIT);
                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
                            @Override
                            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                                return false;
                            }

                            @Override
                            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                                ArrayList<Record> recordsList = Converter.toRecordList(athletes.get(getPosition()).records);
                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                builder.setMessage("Are you sure you want to remove this record?")
                                        .setCancelable(false)
                                        .setPositiveButton("Delete", (dialogInterface, i) -> removeRecord(recordsList.get(viewHolder.getLayoutPosition()).distance))
                                        .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                                        .create().show();
                            }

                            @Override
                            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                                super.onChildDraw(c, recyclerView, viewHolder, dX / 4, dY, actionState, isCurrentlyActive);
                            }

                            public void removeRecord(int i) {
                                Athlete athlete = athletes.get(getPosition());
                                ArrayList<Record> recordList = Converter.toRecordList(athlete.records);
                                recordList.remove(i);
                                athlete.records = Converter.toString(recordList);
                                AppDatabase.getDbInstance(context.getApplicationContext()).athleteDao().updateRecords(athlete.records, athlete.uid);
                                notifyItemChanged(getPosition());
                            }

                            public int getPosition() {
                                for (int j = 0; j < athletes.size(); j++)
                                    if (getItemViewType(j) == 2) {
                                        return j;
                                    }
                                return -1;
                            }
                        }).attachToRecyclerView(editViewHolder.record_list);
                editViewHolder.record_list.setAdapter(adapter);
                editViewHolder.record_add.setOnClickListener((view) -> adapter.addNew());

                editViewHolder.name.setText(athletes.get(position).name);
                editViewHolder.name.setOnFocusChangeListener((view, b) -> updateAthlete(editViewHolder.name.getText().toString(), editViewHolder.description.getText().toString()));
                editViewHolder.name.setOnEditorActionListener((view, id, event) -> {
                    if (id == EditorInfo.IME_ACTION_DONE) {
                        updateAthlete(editViewHolder.name.getText().toString(), editViewHolder.description.getText().toString());
                        editViewHolder.name.clearFocus();
                    }
                    return false;
                });

                editViewHolder.description.setText(athletes.get(position).description);
                editViewHolder.description.setOnFocusChangeListener((view, b) -> updateAthlete(editViewHolder.name.getText().toString(), editViewHolder.description.getText().toString()));
                editViewHolder.description.setOnEditorActionListener((view, id, event) -> {
                    if (id == EditorInfo.IME_ACTION_DONE) {
                        updateAthlete(editViewHolder.name.getText().toString(), editViewHolder.description.getText().toString());
                        editViewHolder.description.clearFocus();
                    }
                    return false;
                });

                editViewHolder.close.setOnClickListener(view -> setType(position, type.TYPE_SMALL));
                editViewHolder.view.setOnClickListener(view -> setType(position, type.TYPE_VIEW));
                editViewHolder.delete.setOnClickListener(view -> new AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to remove this athlete?")
                        .setCancelable(false)
                        .setPositiveButton("Delete", (dialogInterface, i) -> deleteCurrentlySelectedAthlete())
                        .setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.cancel())
                        .create().show());
        }
    }

    public void deleteCurrentlySelectedAthlete() {
        int pos = getCurrentlySelected();
        mLayoutManager.setScrollable(true);
        db.athleteDao().delete(athletes.get(pos));
        athletes.remove(pos);
        itemClickedState.remove(pos);
        notifyItemRemoved(pos);
    }

    public void updateAthlete(String name, String description) {
        Athlete athlete = athletes.get(getCurrentlySelected());
        athlete.name = name;
        athlete.description = description;
        db.athleteDao().updateName(name, athlete.uid);
        db.athleteDao().updateDescription(description, athlete.uid);
    }

    public void addNew() {
        mLayoutManager.setScrollable(true);
        mLayoutManager.scrollToPositionWithOffset(0, 0);
        athletes.add(0, new Athlete());
        itemClickedState.add(0, type.TYPE_SMALL);
        db.athleteDao().insertAthlete(athletes.get(0));
        setType(0, type.TYPE_EDIT);
        notifyItemChanged(0);
    }

    @Override
    public int getItemCount() {
        return this.athletes == null ? 0 : this.athletes.size(); // Returns length of list
    }

    @Override
    public int getItemViewType(int position) {
        switch (itemClickedState.get(position)) {
            case TYPE_SMALL: return 0;
            case TYPE_VIEW: return 1;
            case TYPE_EDIT: return 2;
            default: return -1;
        }
    }

    public int getCurrentlySelected () {
        int pos = -1;
        for (int j = 0; j < athletes.size(); j++)
            if (getItemViewType(j) != 0) {
                pos = j;
                break;
            }
        return pos;
    }

    public void setType(int position, type type) {
        for (int i = 0; i < itemClickedState.size(); i++)
            if (i != position) itemClickedState.set(i, AthleteListAdapter.type.TYPE_SMALL);
        itemClickedState.set(position, type);
        mLayoutManager.setScrollable(!itemClickedState.contains(AthleteListAdapter.type.TYPE_VIEW) && !itemClickedState.contains(AthleteListAdapter.type.TYPE_EDIT));
        notifyItemChanged(position);
    }

    public void hideKeyboard (View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    //View Holder for AthleteListAdapter
    public static class SmallAthleteViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView description;
        public SmallAthleteViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.athlete_item_small_name);
            description = itemView.findViewById(R.id.athlete_item_small_description);
        }
    }

    public static class ViewAthleteViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView name;
        TextView description;
        ConstraintLayout close;
        ConstraintLayout edit;
        RecyclerView record_list;
        public ViewAthleteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            name = itemView.findViewById(R.id.athlete_item_view_name);
            description = itemView.findViewById(R.id.athlete_item_view_description);
            close = itemView.findViewById(R.id.athlete_item_view_close);
            edit = itemView.findViewById(R.id.athlete_item_view_edit);
            record_list = itemView.findViewById(R.id.athlete_item_view_records);

            record_list.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            record_list.addItemDecoration(UpdateAdapters.createVerticalDivider(itemView.getContext(), 10.0));
        }
    }

    public static class EditAthleteViewHolder extends RecyclerView.ViewHolder {
        EditText name;
        EditText description;
        ConstraintLayout close;
        ConstraintLayout view;
        ConstraintLayout delete;
        RecyclerView record_list;
        ImageView record_add;
        public EditAthleteViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.athlete_item_edit_name);
            description = itemView.findViewById(R.id.athlete_item_edit_description);
            close = itemView.findViewById(R.id.athlete_item_edit_close);
            view = itemView.findViewById(R.id.athlete_item_edit_view);
            delete = itemView.findViewById(R.id.athlete_item_edit_delete);
            record_list = itemView.findViewById(R.id.athlete_item_edit_records);
            record_add = itemView.findViewById(R.id.athlete_item_edit_add_record);

            record_list.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
            record_list.addItemDecoration(UpdateAdapters.createVerticalDivider(itemView.getContext(), 10.0));
        }
    }
}
