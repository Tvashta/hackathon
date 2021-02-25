package com.example.firetopology;

public class Node {
    private String MAC;
    private String IP;
    private String MAC_Neighbour_A;
    private String MAC_Neighbour_B;
    private String applicationVersion;
    private String modePortA;
    private String modePortB;
    private String NUPA;
    private String NUPB;
    private String USBA;
    private String USBB;

    public Node(String mac,String ip, String neighbour_A, String neighbour_B, String application_version, String modeA, String modeB, String nupa, String nupb, String usba,  String usbb) {
        this.MAC = mac;
        this.IP = ip;
        this.MAC_Neighbour_A = neighbour_A;
        this.MAC_Neighbour_B = neighbour_B;
        this.applicationVersion = application_version.split(" ")[0];
        this.modePortA = modeA;
        this.modePortB = modeB;
        this.NUPA = nupa;
        this.NUPB = nupb;
        this.USBA = usba;
        this.USBB = usbb;
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
    public String getNUPA(){
        return this.NUPA;
    }
    public String getNUPB(){
        return this.NUPB;
    }
    public String getUSBA() {
        return this.USBA;
    }
    public String getUSBB() {
        return this.USBB;
    }
}
