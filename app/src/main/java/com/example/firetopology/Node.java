package com.example.firetopology;

public class Node {
    private String MAC;
    private String IP;
    private String MAC_Neighbour_A;
    private String MAC_Neighbour_B;

    public Node(String mac, String ip, String neighbour_a, String neighbour_b) {
        this.MAC = mac;
        this.IP = ip;
        this.MAC_Neighbour_A = neighbour_a;
        this.MAC_Neighbour_B = neighbour_b;
    }

    public String getMAC() {
        return this.MAC;
    }
}
