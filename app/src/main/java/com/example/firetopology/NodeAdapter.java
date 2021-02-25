package com.example.firetopology;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mac;
        public ViewHolder(View itemView) {
            super(itemView);
            mac = (TextView) itemView.findViewById(R.id.mac);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View nodeView = inflater.inflate(R.layout.node_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(nodeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NodeAdapter.ViewHolder holder, int position) {
        Node node = nodes.get(position);
        TextView textView = holder.mac;
        textView.setText(node.getMAC());
    }

    @Override
    public int getItemCount() {
        return nodes.size();
    }

    private ArrayList<Node> nodes;
    public NodeAdapter(ArrayList<Node> nodesList) {
        nodes = nodesList;
    }
}
