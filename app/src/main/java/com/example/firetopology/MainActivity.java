package com.example.firetopology;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    static void printGraph(ArrayList<ArrayList<Integer> > adj)
    {
        for (int i = 0; i < adj.size(); i++) {
            System.out.println("\nVertex" + i);
            for (int j = 0; j < adj.get(i).size(); j++) {
                System.out.print(" -> "+adj.get(i).get(j));
            }
            System.out.println();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\adith\\eclipse-workspace\\Lab 1\\src\\not_cd\\excelshit.csv"));
            String line=br.readLine();
            ArrayList<String> nodes = new ArrayList<>();

            int v=0;
            while((line = br.readLine()) != null)
            {
                nodes.add(line);
                v+=1;

            }
            ArrayList <ArrayList <Integer>> graph = new ArrayList<>(v);
            HashMap<String, Integer> map = new HashMap<>();
            for(int i=0;i<v;i++)
            {
                graph.add(new ArrayList<Integer>());
                map.put(nodes.get(i).split(",")[0],i);
            }
            for(int i=0;i<v;i++)
            {
                String[] array = nodes.get(i).split(",");
                graph.get(map.get(array[0])).add(map.get(array[1]));
                graph.get(map.get(array[0])).add(map.get(array[2]));
            }
            printGraph(graph);

        }
        catch(Exception e) {

        }

    }
}