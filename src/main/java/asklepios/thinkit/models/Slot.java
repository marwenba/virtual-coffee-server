package asklepios.thinkit.models;

import java.io.Serializable;
import java.util.List;

public class Slot implements Serializable{
    private String name;
    private List<List<Integer>> time;
    
    
    public void setName(String name){
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setCommonSlots(List<List<Integer>> time){
        this.time = time;
    }
    public List<List<Integer>> getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Slot {" +
        "name='" + name + "'" +
        ", time=" + time +
        "}";
    }
}