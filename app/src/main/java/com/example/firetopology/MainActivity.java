package com.example.firetopology;

import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Math.max;


public class MainActivity extends AppCompatActivity {
    Button btn1;
    static ArrayList<String> nodes = new ArrayList<>();
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
    }

    static ArrayList<Integer> order = new ArrayList<>();
    static String getNode(int row){
        return nodes.get(row);
    }
    static void dfs(boolean[] visited, int v, ArrayList<ArrayList<Integer>> graph) {
        visited[v] = true;
        Log.d("Node", String.valueOf(v));
        order.add(v);
        for (Integer i : graph.get(v)) {
            if (i != null && !visited[i])
                dfs(visited, i, graph);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn1=findViewById(R.id.button);
        try {
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
            dfs(visited, start, graph);
            for (int i = 0; i < order.size(); i++)
                Log.d("MAC", map.getKey(order.get(i)).substring(9));
            btn1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    details dialogFragment=new details();
                    Bundle bundle = new Bundle();
                    bundle.putString("row", "0");
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(getSupportFragmentManager(),"details");
                }
            });
        } catch (Exception e) {
            Log.d("Error", e.toString());
            e.printStackTrace();
        }

    }


}
