package com.example.firetopology;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NodeAdapter extends RecyclerView.Adapter<NodeAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mac, version, modeType, nupa, nupb, usba, usbb;
        public Button up, down, left, right, divleft, divright;
        public ViewHolder(View itemView) {
            super(itemView);
            mac = (TextView) itemView.findViewById(R.id.macadd);
            version = itemView.findViewById(R.id.version);
            modeType = itemView.findViewById(R.id.type);
            nupa = itemView.findViewById(R.id.nupa);
            nupb = itemView.findViewById(R.id.nupb);
            usba = itemView.findViewById(R.id.usba);
            usbb = itemView.findViewById(R.id.usbb);
            up = itemView.findViewById(R.id.upbtn);
            down = itemView.findViewById(R.id.downbtn);
            left = itemView.findViewById(R.id.leftbtn);
            right = itemView.findViewById(R.id.rightbtn);
            divleft = itemView.findViewById(R.id.divleft);
            divright = itemView.findViewById(R.id.divright);
        }

        @Override
        public void onClick(View view) {

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View nodeView = inflater.inflate(R.layout.fragment_boxthing, parent, false);
        ViewHolder viewHolder = new ViewHolder(nodeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NodeAdapter.ViewHolder holder, int position) {
        Node node = nodes.get(position);
        TextView textView = holder.mac;
        textView.setText(node.getMAC());
        TextView appVersion = holder.version;
        if (Double.parseDouble(node.getApplicationVersion()) < 30) {
            appVersion.setText("A");
            appVersion.setBackgroundResource(R.drawable.circle_texta);
        } else {
            appVersion.setText("B");
            appVersion.setBackgroundResource(R.drawable.circle_textb);
        }
        TextView type = holder.modeType;
        String modeA = node.getModePortA();
        String modeB = node.getModePortB();
        String flag = "";
        if (modeA.equalsIgnoreCase("Fiber"))
            flag += "1";
        else
            flag += "0";
        if (modeB.equalsIgnoreCase("Fiber"))
            flag += "1";
        else
            flag += "0";


        if (flag.equalsIgnoreCase("00")) {
            type.setText("V");
            type.setBackgroundResource(R.drawable.circle_textv);
        } else if (flag.equalsIgnoreCase("01") || flag.equalsIgnoreCase("10")) {
            type.setText("M");
            appVersion.setBackgroundResource(R.drawable.circle_textm);
        } else {
            type.setText("F");
            appVersion.setBackgroundResource(R.drawable.circle_textf);
        }

        TextView NUPA = holder.nupa;
        TextView NUPB = holder.nupb;
        TextView USBA = holder.usba;
        TextView USBB = holder.usbb;
        NUPA.setText(node.getNUPA());
        NUPB.setText(node.getNUPB());
        USBA.setText(node.getUSBA());
        USBB.setText(node.getUSBB());

        if (position % 3 == 0) {
            holder.left.setVisibility(View.INVISIBLE);
            holder.down.setVisibility(View.INVISIBLE);
            holder.divleft.setVisibility(View.INVISIBLE);
            holder.right.setVisibility(View.VISIBLE);
            holder.up.setVisibility(View.VISIBLE);
            holder.divright.setVisibility(View.VISIBLE);
            Log.d("Holder", String.valueOf(MainActivity.loops));
            if(MainActivity.loops.contains(position)){
                if(!MainActivity.loop.contains(position))
                    holder.up.setVisibility(View.INVISIBLE);
            }
            else if(MainActivity.loops.contains(position+3)){
                holder.divright.setVisibility(View.INVISIBLE);

            }
            if(MainActivity.loops.contains(position+1)){
                holder.right.setVisibility(View.INVISIBLE);
            }

        } else if (position % 3 == 1) {
            holder.up.setVisibility(View.INVISIBLE);
            holder.down.setVisibility(View.GONE);
            holder.right.setVisibility(View.VISIBLE);
            holder.left.setVisibility(View.VISIBLE);
            holder.divright.setVisibility(View.VISIBLE);
            holder.divleft.setVisibility(View.VISIBLE);
            if(MainActivity.loops.contains(position+2)){
                holder.divright.setVisibility(View.INVISIBLE);
                holder.divleft.setVisibility(View.INVISIBLE);
            }
            else if(MainActivity.loops.contains(position)){
                if(MainActivity.loop.contains(position))
                    holder.up.setVisibility(View.VISIBLE);
                holder.left.setVisibility(View.INVISIBLE);
            }
            if(MainActivity.loops.contains(position+1)){
                holder.right.setVisibility(View.INVISIBLE);
            }
        } else {
            holder.right.setVisibility(View.GONE);
            holder.up.setVisibility(View.GONE);
            holder.divright.setVisibility(View.GONE);
            holder.left.setVisibility(View.VISIBLE);
            holder.down.setVisibility(View.VISIBLE);
            holder.divleft.setVisibility(View.VISIBLE);
            if(MainActivity.loops.contains(position+1)){
                holder.divright.setVisibility(View.INVISIBLE);
                holder.divleft.setVisibility(View.INVISIBLE);
                holder.down.setVisibility(View.INVISIBLE);
            }
            else if(MainActivity.loops.contains(position)){
                if(MainActivity.loop.contains(position))
                    holder.up.setVisibility(View.VISIBLE);
                holder.left.setVisibility(View.INVISIBLE);

            }
        }
        if(position==nodes.size()-1)
            holder.right.setVisibility(View.INVISIBLE);

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
