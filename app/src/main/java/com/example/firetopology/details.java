package com.example.firetopology;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class details extends DialogFragment {
    TextView mac, ip;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.details, container, false);
        Integer i = Integer.valueOf(getArguments().getString("row"));
        Node s = MainActivity.getNode(i);
        mac = (TextView) view.findViewById(R.id.mac_text);
        ip = (TextView) view.findViewById(R.id.ipText);
        mac.setText(s.getMAC());
        ip.setText(s.getIP());

        return view;
    }
}