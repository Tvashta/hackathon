package com.example.firetopology;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Arrays;

public class details extends DialogFragment {
    TextView mac,ip;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.details, container, false);
        Integer i = Integer.valueOf(getArguments().getString("row"));
        String[] s=MainActivity.getNode(i).split(",");
        Log.d("Str", s[0]);
        mac=(TextView) view.findViewById(R.id.mac_text);
        ip=(TextView) view.findViewById(R.id.ipText);
        mac.setText(s[0]);
        ip.setText(s[6]);

        return view;
    }
}