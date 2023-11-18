package net.emhs.runaway.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import net.emhs.runaway.R;

import java.util.List;

public class TypeDropdownAdapter extends ArrayAdapter<String> {

    private Context context;

    private LayoutInflater inflater;

    public TypeDropdownAdapter(@NonNull Context context, int resource, int textViewId, List<String> list) {
        super(context, resource, textViewId, list);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return rowview(convertView, position);
    }

    private View rowview(View convertView, int position) {
        getItem(position);
        ViewHolder holder;
        View rowView = convertView;

        if(rowView==null) {
            holder = new ViewHolder();
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.type_list, null, false);

            holder.text = rowView.findViewById(R.id.type_list_text);
            rowView.setTag(holder);
        } else {
            holder = (ViewHolder) rowView.getTag();
        }
        holder.text.setText(getItem(position));

        return rowView;
    }

    private static class ViewHolder {
        public TextView text;
    }
}
