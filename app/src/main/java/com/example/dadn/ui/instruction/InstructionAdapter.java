package com.example.dadn.ui.instruction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dadn.R;

import java.util.ArrayList;

public class InstructionAdapter extends RecyclerView.Adapter<InstructionAdapter.InstructionViewHolder> {
    private Context context;

    public InstructionAdapter(Context context) {
        this.context = context;
    }

    private ArrayList<InstructionItem> instructionList;

    public void setData(ArrayList<InstructionItem> instructionList){
        this.instructionList = instructionList;
    }
    @Override
    public InstructionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.instructions_items, parent, false);
        InstructionViewHolder viewHolder = new InstructionViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InstructionAdapter.InstructionViewHolder holder, int position) {
        InstructionItem currentItem = instructionList.get(position);
        holder.mImgView.setImageResource(currentItem.getmImgResource());
        holder.mTitle.setText(currentItem.getTitle());
        holder.mDetail.setText(currentItem.getDetails());
    }

    @Override
    public int getItemCount() {
        return instructionList.size();
    }

    public class InstructionViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImgView;
        public TextView mTitle;
        public TextView mDetail;
        public InstructionViewHolder(@NonNull View itemView) {
            super(itemView);
            mImgView = itemView.findViewById(R.id.iv_instruction_item);
            mTitle = itemView.findViewById(R.id.tv_title_instruction);
            mDetail = itemView.findViewById(R.id.tv_details_instruction);
        }
    }
}
