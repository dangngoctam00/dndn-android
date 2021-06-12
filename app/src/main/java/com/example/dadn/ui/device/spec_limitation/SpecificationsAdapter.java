package com.example.dadn.ui.device.spec_limitation;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dadn.R;
import com.example.dadn.data.dto.SpecificationResponse;

import java.util.List;

public class SpecificationsAdapter extends RecyclerView.Adapter<SpecificationsAdapter.ViewHolder> {

    private static final String TAG = "SpecificationsAdapter: ";
    private List<SpecificationResponse> specs;

    public void setSpecs(List<SpecificationResponse> specs) {
        this.specs = specs;
        notifyDataSetChanged();
    }

    public List<SpecificationResponse> getSpecs() {
        return this.specs;
    }

    public SpecificationsAdapter(List<SpecificationResponse> specs) {
        this.specs = specs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View specView = inflater.inflate(R.layout.item_spec, parent, false);

        ViewHolder viewHolder = new ViewHolder(specView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data model based on position
        SpecificationResponse type = specs.get(position);

        // Set item views based on your views and data model
        holder.spec_name.setText(type.getType());
        Log.d(TAG, "onBindViewHolder()");
    }

    @Override
    public int getItemCount() {
        return specs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView spec_name;

        public ViewHolder(View itemView) {
            super(itemView);
            spec_name = (TextView) itemView.findViewById(R.id.spec_name);
        }
    }
}
