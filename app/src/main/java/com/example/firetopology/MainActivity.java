package com.example.firetopology;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
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
            return inversedMap.get(v);}
    }

    static ArrayList<Integer> order = new ArrayList<>();

    static void dfs(boolean[] visited, int v, ArrayList<ArrayList<Integer>> graph) {
        visited[v] = true;
        Log.d("Node", String.valueOf(v));
        order.add(v);
        for (Integer i : graph.get(v)) {
            if (i != null && !visited[i])
                dfs(visited, i, graph);
        }
    }

    ArrayList<Node> nodesList = new ArrayList<Node>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP_REVERSE);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);


        try {
            ArrayList<String> nodes = new ArrayList<String>();
            InputStream is = getResources().openRawResource(R.raw.book1);
            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line = br.readLine();
            int v = 0;
            while ((line = br.readLine()) != null) {
                nodes.add(line);
                v++;
            }
            ArrayList<ArrayList<Integer>> graph = new ArrayList<>(v);
            BiMap<String, Integer> map = new BiMap<>();
            for (int i = 0; i < v; i++) {
                graph.add(new ArrayList<>());
                map.put(nodes.get(i).split(",")[0], i);
            }
            int start = -1;
            for (int i = 0; i < v; i++) {
                String[] array = nodes.get(i).split(",");
                if ((map.get(array[7]) == null || (map.get(array[8]) == null)) && start == -1)
                    start = map.get(array[0]);
                graph.get(map.get(array[0])).add(map.get(array[7]));
                graph.get(map.get(array[0])).add(map.get(array[8]));
            }
            start = max(start, 0);
            boolean[] visited = new boolean[v];
//            for(boolean i: visited)
//                Log.d("Check", String.valueOf(i));
            dfs(visited, start, graph);
            for (int i = 0; i < order.size(); i++) {
                Log.d("MAC", map.getKey(order.get(i)).substring(9));
                Node n = new Node(map.getKey(order.get(i)).substring(9));
                nodesList.add(n);
            }

            NodeAdapter nodeAdapter = new NodeAdapter(nodesList);
            recyclerView.setAdapter(nodeAdapter);

        } catch (Exception e) {
            Log.d("Error", e.toString());
            e.printStackTrace();
        }

    }




}


