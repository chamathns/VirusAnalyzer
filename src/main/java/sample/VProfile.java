package sample;

public class VProfile {
    private String virusName;
    private String vxMD5;
    private String engine;
    private int awareness;
    private String type;

    public VProfile(String virusName, String vxMD5, String engine, int awareness, String type){
        this.virusName = virusName;
        this.vxMD5 = vxMD5;
        this.engine = engine;
        this.awareness = awareness;
        this.type = type;
    }

    public String getVirusName() {
        return virusName;
    }

    public void setVirusName(String virusName) {
        this.virusName = virusName;
    }

    public String getVxMD5() {
        return vxMD5;
    }

    public void setVxMD5(String vxMD5) {
        this.vxMD5 = vxMD5;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getAwareness() {
        return awareness;
    }

    public void setAwareness(int awareness) {
        this.awareness = awareness;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VProfile(){

    }
}
