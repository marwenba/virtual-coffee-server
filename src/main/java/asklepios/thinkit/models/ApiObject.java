package asklepios.thinkit.models;

import java.io.Serializable;
import java.util.List;

public class ApiObject implements Serializable {
    private List<Thinkiter> guestList;

    public List<Thinkiter> getThinkiters() {
        return guestList;
    }
    public void setThinkiters(List<Thinkiter> availability){
        this.guestList = availability;
    }

    @Override
    public String toString() {
        return "ApiObject{" + "guests=" + guestList + '\'' + '}';
    }
}
