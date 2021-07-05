package asklepios.thinkit.models;

import java.io.Serializable;
import java.util.List;


public class Request implements Serializable {
    private List<Integer> range;
    private String offset;
    private int participants;



    public void setRange(List<Integer> range){
        this.range = range;
    }
    public void setOffset(String offset){
        this.offset = offset;
    }
    public void setParticipants(int participants){
        this.participants = participants;
    }



    
    public List<Integer> getRange() {
        return range;
    }

    public String getOffset() {
        return offset;
    }

    public int getParticipants() {
        return participants;
    }

    @Override
    public String toString() {
        return "Request {" +
        "range=" + range +
        ", offset='" + offset + "'" +
        ", participants=" + participants +
        "}";

    }
}