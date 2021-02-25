package com.example.firetopology;

public class Node {
    private String MAC;
    private String IP;
    private String MAC_Neighbour_A;
    private String MAC_Neighbour_B;

    public Node(String mac) {
        this.MAC = mac;
    }

    public String getMAC() {
        return this.MAC;
    }
}
