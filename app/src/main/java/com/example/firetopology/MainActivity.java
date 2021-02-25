package com.example.firetopology;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
    static ArrayList<Node> nodesList = new ArrayList<Node>();
    View pos1, pos2;
    int p1,p2;
    Button hops;
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

    static Node getNode(int row) {
        return nodesList.get(row);
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

    BiMap<String, Integer> map = new BiMap<>();
    RecyclerView recyclerView;
    static ArrayList<Pair<Integer,Integer>> locations = new ArrayList<Pair<Integer, Integer>>();
    PointF pointA = new PointF(100,600);
    PointF pointB = new PointF(500,70);
    private LineView mLineView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLineView = (LineView) findViewById(R.id.lineView);
        mLineView.setPointA(pointA);
        mLineView.setPointB(pointB);
        mLineView.draw();
        mLineView = (LineView) findViewById(R.id.lineView1);
        PointF pointA = new PointF(0,600);
        PointF pointB = new PointF(90,70);
        mLineView.setPointA(pointA);
        mLineView.setPointB(pointB);
        mLineView.draw();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setJustifyContent(JustifyContent.SPACE_AROUND);
        recyclerView.setLayoutManager(layoutManager);
        hops=findViewById(R.id.hops);
        hops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, p1+","+p2,
                        Toast.LENGTH_SHORT).show();
            }
        });
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
            for (int i = 0; i < order.size(); i++) {
                Log.d("MAC", map.getKey(order.get(i)).substring(9));
                Node n = new Node(map.getKey(order.get(i)).substring(9), nodes.get(order.get(i)).split(",")[6],nodes.get(order.get(i)).split(",")[7],nodes.get(order.get(i)).split(",")[8],nodes.get(order.get(i)).split(",")[13],nodes.get(order.get(i)).split(",")[18],nodes.get(order.get(i)).split(",")[19],nodes.get(order.get(i)).split(",")[1],nodes.get(order.get(i)).split(",")[2],nodes.get(order.get(i)).split(",")[3],nodes.get(order.get(i)).split(",")[4]);
                nodesList.add(n);
            }

            NodeAdapter nodeAdapter = new NodeAdapter(nodesList);
            recyclerView.setAdapter(nodeAdapter);

            new Handler(Looper.getMainLooper()).postDelayed(
                    new Runnable() {
                        public void run() {
                            connectNodes();
                        }
                    },
                    1000);


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
                    if(pos1==view)
                    {
                        pos1.setPadding(0,0,0,0);
                        if(pos2!=null)
                        {
                            pos1=pos2;
                            p1=p2;
                            pos2=null;
                            p2=-1;
                        }
                    }
                    else if(pos2==view){
                        pos2.setPadding(0,0,0,0);
                        pos2=null;
                        p2=-1;
                    }
                    else{
                        view.setPadding(10, 10, 10, 10);
                        if (pos1 == null)
                        {
                            pos1 = view;
                            p1=position;
                        }
                        else if (pos2 == null){
                            pos2 = view;
                            p2=position;
                        }
                        else {
                            pos1.setPadding(0, 0, 0, 0);
                            pos1 = pos2;
                            pos2 = view;
                            p1=p2;
                            p2=position;
                        }

                    }
                    if(pos1==pos2)
                    {
                        pos1.setPadding(0,0,0,0);
                        pos2.setPadding(0,0,0,0);
                        pos1=null;
                        pos2=null;
                        p1=p2=-1;
                    }
                    if(pos1!=null&&pos2!=null)
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

    private void connectNodes() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for(int i=0;i<locations.size();i++) {
            if(arrayList.contains(locations.get(i).first)) {
                break;
            }
            arrayList.add(locations.get(i).first);
        }
        int rowWidth = locations.get(1).second - locations.get(0).second;


        Log.e("size",nodesList.size()+"");
        for(int i=0; i<nodesList.size();i++) {
            String neighbourA = nodesList.get(i).getMAC_Neighbour_A();
            String neighbourB = nodesList.get(i).getMAC_Neighbour_B();
            Integer indexOfNa, indexOfNb;
            indexOfNa = map.get(neighbourA);
            indexOfNb = map.get(neighbourB);

            Paint myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            myPaint.setStrokeWidth(4);
            myPaint.setColor(Color.RED);   //color.RED
            Canvas canvas = new Canvas();
            //Log.e("ind",indexOfNa+"");
            int xStart, yStart, xEnd, yEnd;
            if(indexOfNa != null) {
                xStart = arrayList.get((i%(arrayList.size())));
                xEnd = arrayList.get(((indexOfNa.intValue())%(arrayList.size())));
                yStart = rowWidth*(1+((int)i/3));
                yEnd = rowWidth*(1+((int)(indexOfNa.intValue())/3));

                canvas.drawLine(xStart, yStart, xEnd,yEnd, myPaint);
            }

            if(indexOfNb != null) {
                xStart = arrayList.get((i%(arrayList.size())));
                xEnd = arrayList.get(((indexOfNb.intValue())%(arrayList.size())));
                yStart = rowWidth*(1+((int)i/3));
                yEnd = rowWidth*(1+((int)(indexOfNb.intValue())/3));

                canvas.drawLine(xStart, yStart, xEnd,yEnd, myPaint);
            }




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


