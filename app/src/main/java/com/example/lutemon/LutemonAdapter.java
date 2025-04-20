package com.example.lutemon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class LutemonAdapter extends RecyclerView.Adapter<LutemonAdapter.ViewHolder> {

    private ArrayList<Integer> ids;
    private HashMap<Integer, Lutemon> sourceMap;
    private boolean showTransferButtons = true;
    private boolean showReturnButton = false;

    public LutemonAdapter(ArrayList<Integer> ids, HashMap<Integer, Lutemon> sourceMap) {
        this.ids = ids;
        this.sourceMap = sourceMap;
    }

    public void setShowTransferButtons(boolean value) {
        showTransferButtons = value;
    }

    public void setShowReturnButton(boolean value) {
        showReturnButton = value;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName, txtStats, txtExp;
        public ImageView imageView;
        public Button btnToTraining, btnToBattle, btnReturn;

        public ViewHolder(View view) {
            super(view);
            txtName = view.findViewById(R.id.txtName);
            txtStats = view.findViewById(R.id.txtStats);
            txtExp = view.findViewById(R.id.txtExp);
            imageView = view.findViewById(R.id.imageView);
            btnToTraining = view.findViewById(R.id.btnToTraining);
            btnToBattle = view.findViewById(R.id.btnToBattle);
            btnReturn = view.findViewById(R.id.btnReturn);
        }
    }

    @Override
    public LutemonAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lutemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int id = ids.get(position);
        Lutemon lutemon = sourceMap.get(id);
        if (lutemon == null) return;

        holder.txtName.setText(lutemon.getName() + " (" + lutemon.getColorName() + ")");
        holder.txtStats.setText("Attack: " + lutemon.getTotalAttack() +
                ", Defense: " + lutemon.getDefense() +
                ", HP: " + lutemon.getCurrentHealth());
        holder.txtExp.setText("Experience: " + lutemon.getExperience());

        switch (lutemon.getColorName().toLowerCase()) {
            case "white": holder.imageView.setImageResource(R.drawable.lutemon_white); break;
            case "green": holder.imageView.setImageResource(R.drawable.lutemon_green); break;
            case "pink": holder.imageView.setImageResource(R.drawable.lutemon_pink); break;
            case "orange": holder.imageView.setImageResource(R.drawable.lutemon_orange); break;
            case "black": holder.imageView.setImageResource(R.drawable.lutemon_black); break;
        }

        // Show/hide control buttons
        holder.btnToTraining.setVisibility(showTransferButtons ? View.VISIBLE : View.GONE);
        holder.btnToBattle.setVisibility(showTransferButtons ? View.VISIBLE : View.GONE);
        holder.btnReturn.setVisibility(showReturnButton ? View.VISIBLE : View.GONE);

        holder.btnToTraining.setOnClickListener(v -> {
            Storage.getInstance().moveToTraining(id);
            removeItem(position);
            notifyItemRangeChanged(position, ids.size()); // Prevent misaligned clicks
        });

        holder.btnToBattle.setOnClickListener(v -> {
            Storage.getInstance().moveToBattle(id);
            removeItem(position);
            notifyItemRangeChanged(position, ids.size());
        });

        holder.btnReturn.setOnClickListener(v -> {
            Storage.getInstance().returnToHome(id);
            removeItem(position);
            notifyItemRangeChanged(position, ids.size());
        });
    }

    @Override
    public int getItemCount() {
        return ids.size();
    }

    private void removeItem(int position) {
        ids.remove(position);
        notifyItemRemoved(position);
    }
}
