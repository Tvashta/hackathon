package com.example.firetopology;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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

    static String getNode(int row) {
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

    ArrayList<Node> nodesList = new ArrayList<Node>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);


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
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this,
                    recyclerView, new ClickListener() {
                @Override
                public void onClick(View view, final int position) {
                    details dialogFragment = new details();
                    Bundle bundle = new Bundle();
                    bundle.putString("row", String.valueOf(position));
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(getSupportFragmentManager(), "details");
                    //Values are passing to activity & to fragment as well
                    Toast.makeText(MainActivity.this, "Single Click on position        :" + position,
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onLongClick(View view, int position) {
                    Toast.makeText(MainActivity.this, "Long press on position :" + position,
                            Toast.LENGTH_LONG).show();
                }
            }));

        } catch (Exception e) {
            Log.d("Error", e.toString());
            e.printStackTrace();
        }

    }

    public static interface ClickListener {
        public void onClick(View view, int position);

        public void onLongClick(View view, int position);
    }

    class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private ClickListener clicklistener;
        private GestureDetector gestureDetector;

        public RecyclerTouchListener(Context context, final RecyclerView recycleView, final ClickListener clicklistener) {

            this.clicklistener = clicklistener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recycleView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clicklistener != null) {
                        clicklistener.onLongClick(child, recycleView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clicklistener != null && gestureDetector.onTouchEvent(e)) {
                clicklistener.onClick(child, rv.getChildAdapterPosition(child));
            }

            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}


