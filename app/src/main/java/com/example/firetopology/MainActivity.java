package com.example.firetopology;

import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
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
import java.util.LinkedList;

import static java.lang.Math.max;


public class MainActivity extends AppCompatActivity {
    static ArrayList<String> nodes = new ArrayList<>();
    static ArrayList<Node> nodesList = new ArrayList<Node>();
    View pos1, pos2;
    int p1, p2;
    Button hops;
    RecyclerView recyclerView;
    static ArrayList<Integer> order = new ArrayList<>();
    static ArrayList<Integer> loops = new ArrayList<>();
    static ArrayList<Integer> loop = new ArrayList<>();
    static ArrayList<Integer> lr = new ArrayList<>();
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


    static Node getNode(int row) {
        return nodesList.get(row);
    }

    static int bfs(int u, int v, int dir) {
        BiMap<String, Integer> map1 = new BiMap<>();
        for (int i = 0; i < nodesList.size(); i++)
            map1.put(nodesList.get(i).getMAC(), i);
        int count = 0;
        boolean visited[] = new boolean[nodesList.size()];
        LinkedList<Integer> queue = new LinkedList<Integer>();
        visited[u] = true;
        queue.add(u);
        while (queue.size() != 0) {
            u = queue.poll();
            count++;
            if (u == v) return count;
            Node node = nodesList.get(u);
            Log.d("LR", lr.get(u)+"");
            if (dir == 0) {
                String ma=node.getMAC_Neighbour_B();;
                if(lr.get(u)==0)
                    ma=node.getMAC_Neighbour_A();
                if(ma.length()>9)
                    ma=ma.substring(9);
                Integer n = map1.get(ma);
                if (n != null && !visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            } else {
                String ma=node.getMAC_Neighbour_A();
                if(lr.get(u)==0)
                    ma=node.getMAC_Neighbour_B();
                if(ma.length()>9)
                    ma=ma.substring(9);
                Integer n = map1.get(ma);
                if (n != null && !visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
        return 0;
    }

    static boolean dfs(boolean[] visited, int v, ArrayList<ArrayList<Integer>> graph, int p) {
        visited[v] = true;
        order.add(v);
        for (Integer i : graph.get(v)) {
            if (i != null && !visited[i])
            {
                if(dfs(visited, i, graph,v)) return true;
            }
            else if(i!=null&&i!=p)
                return true;
        }
        return false;
    }
    static int loopStart=-1;
    static void getStart(boolean[] visited, int v, ArrayList<ArrayList<Integer>> graph){
        visited[v]=true;
        for (Integer i : graph.get(v)) {
            if(i==null)loopStart=v;
            else{
                if(!visited[i])
                    getStart(visited,i,graph);
            }
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BiMap<String, Integer> map = new BiMap<>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        recyclerView.setLayoutManager(layoutManager);
        hops = findViewById(R.id.hops);
//        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.AlertDialogCustom));
        AlertDialog alertDialog =  new AlertDialog.Builder(
                new ContextThemeWrapper(this, R.style.AlertDialogCustom)).create();
        SpannableString Title = new SpannableString("Number Of Hops");
        Title.setSpan(
                new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0,
                Title.length(),
                0
        );
        alertDialog.setTitle(Title);

        hops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.setMessage("From " + nodesList.get(p1).getMAC() + " to " + nodesList.get(p2).getMAC() + "\n\t\tRight: " + (bfs(p1, p2, 0)-1) + "\n\t\tLeft: " + (bfs(p1, p2, 1) -1)+ "\nFrom " + nodesList.get(p2).getMAC() + " to " + nodesList.get(p1).getMAC() + "\n\t\tRight: " + (bfs(p2, p1, 0)-1) + "\n\t\tLeft: " + (bfs(p2, p1, 1)-1));
                alertDialog.show();
            }
        });
        try {
            if (nodesList.size() == 0) {
                InputStream is = getResources().openRawResource(R.raw.book1);
                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                String line = br.readLine();
                int v = 0;
                while ((line = br.readLine()) != null) {
                    nodes.add(line);
                    v++;
                }
                ArrayList<ArrayList<Integer>> graph = new ArrayList<>(v);

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
                if(dfs(visited, start, graph,-1)){
                    loop.add(0);
                    loops.add(0);
                }
                for(int i=0;i<v;i++){
                    if(!visited[i]){
                        int s=order.size();
                        loops.add(s);
                        loopStart=-1;
                        boolean vis1[]=visited.clone();
                        getStart(vis1,i,graph);
                        if(loopStart==-1)loopStart=i;
                        if(dfs(visited, loopStart, graph,-1))loop.add(s);
                    Log.d("DFS", String.valueOf(i));
                    }
                }

                for (int i = 0; i < order.size(); i++) {
                    Node n = new Node(map.getKey(order.get(i)).substring(9), nodes.get(order.get(i)).split(",")[6], nodes.get(order.get(i)).split(",")[7], nodes.get(order.get(i)).split(",")[8], nodes.get(order.get(i)).split(",")[13], nodes.get(order.get(i)).split(",")[18], nodes.get(order.get(i)).split(",")[19], nodes.get(order.get(i)).split(",")[1], nodes.get(order.get(i)).split(",")[2], nodes.get(order.get(i)).split(",")[3], nodes.get(order.get(i)).split(",")[4]);
                    nodesList.add(n);
                }
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
                }

                @Override
                public void onLongClick(View view, int position) {
                    view = view.findViewById(R.id.redbg);
                    if (pos1 == view) {
                        pos1.setPadding(0, 0, 0, 0);
                        if (pos2 != null) {
                            pos1 = pos2;
                            p1 = p2;
                            pos2 = null;
                            p2 = -1;
                        }
                    } else if (pos2 == view) {
                        pos2.setPadding(0, 0, 0, 0);
                        pos2 = null;
                        p2 = -1;
                    } else {
                        view.setPadding(10, 10, 10, 10);
                        if (pos1 == null) {
                            pos1 = view;
                            p1 = position;
                        } else if (pos2 == null) {
                            pos2 = view;
                            p2 = position;
                        } else {
                            pos1.setPadding(0, 0, 0, 0);
                            pos1 = pos2;
                            pos2 = view;
                            p1 = p2;
                            p2 = position;
                        }

                    }
                    if (pos1 == pos2) {
                        pos1.setPadding(0, 0, 0, 0);
                        pos2.setPadding(0, 0, 0, 0);
                        pos1 = null;
                        pos2 = null;
                        p1 = p2 = -1;
                    }
                    if (pos1 != null && pos2 != null)
                        hops.setVisibility(View.VISIBLE);
                    else
                        hops.setVisibility(View.INVISIBLE);
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


