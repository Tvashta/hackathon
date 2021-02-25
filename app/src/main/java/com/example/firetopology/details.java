package com.example.firetopology;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class details extends DialogFragment {
    TextView mac, a,b,av,pa,pb,nupa,nupb,usba,usbb,ip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details, container, false);
        Integer i = Integer.valueOf(getArguments().getString("row"));
        Node s = MainActivity.getNode(i);
        mac = (TextView) view.findViewById(R.id.mac_text);
        a = (TextView) view.findViewById(R.id.maca);
        b=(TextView)view.findViewById(R.id.macb);
        av = (TextView) view.findViewById(R.id.applnv);
        pa = (TextView) view.findViewById(R.id.modeA);
        pb=(TextView)view.findViewById(R.id.modeB);
        nupa = (TextView) view.findViewById(R.id.nupA);
        nupb = (TextView) view.findViewById(R.id.nupB);
        usba=(TextView)view.findViewById(R.id.usbA);
        usbb = (TextView) view.findViewById(R.id.usbB);
        ip = (TextView) view.findViewById(R.id.iptext);
        mac.setText(s.getMAC());
        a.setText(s.getMAC_Neighbour_A());
        b.setText(s.getMAC_Neighbour_B());
        av.setText(s.getApplicationVersion());
        pa.setText(s.getModePortA());
        pb.setText(s.getModePortB());
        nupa.setText(s.getNUPA());
        nupb.setText(s.getNUPB());
        usba.setText(s.getUSBA());
        usbb.setText(s.getUSBB());
        ip.setText(s.getIP());
        return view;
    }
}