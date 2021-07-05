package asklepios.thinkit.models;

import java.io.Serializable;
import java.util.List;

public class Thinkiter implements Serializable {
    private String name;
    private List<List<Integer>> availability;
    private String offset;

    public String getName() {
        return name;
    }

    public List<List<Integer>> getAvailability() {
        return availability;
    }
    
    public void setAvailability(List<List<Integer>> availability){
        this.availability = availability;
    }

    public String getOffset() {
        return offset;
    }
    public void setOffset(String offset){
        this.offset = offset;
    }

    @Override
    public String toString() {
        return "Thinkiter{" + "name=" + name + '\'' + ", availability=" + availability + '\'' + ", offset=" + offset
                + '\'' + '}';
    }
}
