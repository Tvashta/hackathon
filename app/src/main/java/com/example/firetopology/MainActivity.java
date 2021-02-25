package com.example.firetopology;

<<<<<<< HEAD
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.max;


public class MainActivity extends AppCompatActivity {

    public static class BiMap<K, V> {
        HashMap<K, V> map = new HashMap<>();
        HashMap<V, K> inversedMap = new HashMap<>();

        void put(K k, V v) {
            map.put(k, v);
            inversedMap.put(v, k);
        }

        V get(K k) {
            return map.get(k);
        }

        K getKey(V v) {
            return inversedMap.get(v);
        }
=======
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
>>>>>>> 2a54c2ed35b1e0b9efd1dac10d1a1f5e9922ea8d
    }
    }

    ArrayList<Node> nodes = new ArrayList<Node>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
//        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);


        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        Node node1 = new Node("1","ip1","0","2");
        Node node2 = new Node("2","ip2","1","3");
        Node node3 = new Node("3","ip3","2","4");
        Node node4 = new Node("4","ip4","3","0");

        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node4);

        NodeAdapter nodeAdapter = new NodeAdapter(nodes);
        recyclerView.setAdapter(nodeAdapter);
//        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
//        layoutManager.setFlexDirection(FlexDirection.ROW);
//        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


=======
        Log.d("EAF","AFdv");
>>>>>>> 2a54c2ed35b1e0b9efd1dac10d1a1f5e9922ea8d
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
<<<<<<< HEAD
            start = max(start, 0);
            boolean[] visited = new boolean[v];
//            for(boolean i: visited)
//                Log.d("Check", String.valueOf(i));
            dfs(visited, start, graph);
            for (int i = 0; i < order.size(); i++) {

                Log.d("MAC", map.getKey(order.get(i)).substring(9));
                TextView textView = new TextView(this);
                textView.setText("dsfgvb");
                //flexboxLayout.addView(textView);
                recyclerView.addView(textView);

            }

        } catch (Exception e) {
            Log.d("Error", e.toString());
            e.printStackTrace();
=======
            printGraph(graph);
        }
        catch (Exception e) {
            Log.d("err",e.toString());
>>>>>>> 2a54c2ed35b1e0b9efd1dac10d1a1f5e9922ea8d
        }

    }




}


