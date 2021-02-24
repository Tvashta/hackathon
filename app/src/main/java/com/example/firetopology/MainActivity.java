package com.example.firetopology;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import com.opencsv.CSVReader;

public class MainActivity extends AppCompatActivity{
    static void printGraph(ArrayList<ArrayList<Integer>> adj) {
        for(int i=0;i<adj.size();i++) {
            Log.d("Vertex", Integer.toString(i));
            for(int j=0; j < adj.get(i).size();j++) {
                Log.d("Vertices","->"+adj.get(i).get(j));
            }
    }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("EAF","AFdv");
        try {
            Log.d("2","xyz2");
            InputStream is = getResources().openRawResource(R.raw.book1);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String line = br.readLine();
            ArrayList<String> nodes = new ArrayList<>();
            int v = 0;
            while ((line = br.readLine()) != null) {
                nodes.add(line);
                v++;
            }
            ArrayList<ArrayList<Integer>> graph = new ArrayList<>(v);
            HashMap<String,Integer> map = new HashMap<>();
            for(int i=0; i<v; i++) {
                graph.add(new ArrayList<Integer>());
                map.put(nodes.get(i).split(",")[0], i);
            }

            for(int i=0; i<v;i++) {
                String[] array = nodes.get(i).split(",");
//                for (int ii =0; ii<array.length;ii++) {
//                    Log.d("err2",array[ii]);
//                }

                graph.get(map.get(array[0])).add(map.get(array[7]));
                graph.get(map.get(array[0])).add(map.get(array[8]));
            }
            printGraph(graph);
        }
        catch (Exception e) {
            Log.d("err",e.toString());
        }

    }




}
