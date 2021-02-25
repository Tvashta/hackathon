package com.example.firetopology;

public class Node {
    private String MAC;
    private String IP;
    private String MAC_Neighbour_A;
    private String MAC_Neighbour_B;
    private String applicationVersion;
    private String modePortA;
    private String modePortB;

    public Node(String mac,String ip, String neighbour_A, String neighbour_B, String application_version, String modeA, String modeB) {
        this.MAC = mac;
        this.IP = ip;
        this.MAC_Neighbour_A = neighbour_A;
        this.MAC_Neighbour_B = neighbour_B;
        this.applicationVersion = application_version.split(" ")[0];
        this.modePortA = modeA;
        this.modePortB = modeB;
    }

    public String getMAC() {
        return this.MAC;
    }
    public String getIP() {
        return this.IP;
    }
    public String getMAC_Neighbour_A() {
        return this.MAC_Neighbour_A;
    }
    public String getMAC_Neighbour_B() {
        return this.MAC_Neighbour_B;
    }
    public String getApplicationVersion() {
        return this.applicationVersion;
    }
    public String getModePortA() {
        return this.modePortA;
    }
    public String getModePortB() {
        return this.modePortB;
    }
}
