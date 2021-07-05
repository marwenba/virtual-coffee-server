package asklepios.thinkit.services;

import java.util.*;

import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import asklepios.thinkit.api.ScheduleApiController;
import asklepios.thinkit.models.*;

@Service
public class DataProcessingService {

    // Find Common Slots in a very Traditional Way since Max participants is 3
    public List<Slot> findCommonSlotsWithInput(List<Thinkiter> t, List<List<Integer>> input, int participants,
            String offset) {
        Collection<List<Integer>> commonslots = new ArrayList<List<Integer>>();
        List<Slot> aq = new ArrayList<Slot>();

        t = updateThinkers(t, offset);
        input = oneHourSegmentation(input);
        System.out.println(t);
        if (t.size() == participants) {
            System.out.println("mm here");
            if (participants == 3) {
                commonslots = intersect(input, intersect(t.get(0).getAvailability(),
                        intersect(t.get(1).getAvailability(), t.get(2).getAvailability())));
                List<List<Integer>> newList = new ArrayList<>(commonslots);
                if (newList.size() > 0) {

                    for (int j = 0; j < t.size(); j++) {
                        Slot available = new Slot();
                        available.setName(t.get(j).getName());
                        available.setCommonSlots(newList);
                        aq.add(available);
                    }
                }

            } else if (participants == 2) {
                commonslots = intersect(input, intersect(t.get(0).getAvailability(), t.get(1).getAvailability()));
                List<List<Integer>> newList = new ArrayList<>(commonslots);
                if (newList.size() > 0) {
                    if (t.size() == participants) {
                        for (int j = 0; j < t.size(); j++) {
                            Slot available = new Slot();
                            available.setName(t.get(j).getName());
                            available.setCommonSlots(newList);
                            aq.add(available);
                        }
                    }

                }

            } else {
                commonslots = intersect(input, t.get(0).getAvailability());
                List<List<Integer>> newList = new ArrayList<>(commonslots);
                if (newList.size() > 0) {
                    if (t.size() == participants) {
                        for (int j = 0; j < t.size(); j++) {
                            Slot available = new Slot();
                            available.setName(t.get(j).getName());
                            available.setCommonSlots(newList);
                            aq.add(available);
                        }
                    }

                }

            }
        }

        return aq;
    }

    // This Function will put all the people in the same timezone as input and will
    // use updateTimeToInputTimeZone() Function
    public List<Thinkiter> updateThinkers(List<Thinkiter> av, String offset) {
        for (int i = 0; i < av.size(); i++) {
            av.get(i).setOffset(offset);
            if (av.get(i).getAvailability().size() > 0) {
                av.get(i).setAvailability(oneHourSegmentation(
                        updateTimeToInputTimeZone(offset, av.get(i).getOffset(), av.get(i).getAvailability())));
            } else {
                av.remove(i);
            }

        }

        return av;
    }

    // Main Function that will communicate with the Client
    public String responsefromRequest(String requesty) {
        Gson g = new Gson();
        JSONObject jsonObject = new JSONObject();
        Request request = new Request();
        List<Slot> slots = new ArrayList<>();
        request = g.fromJson(requesty.toString(), Request.class);
        System.out.println(request);

        try {
            // This function will communicate with the Open API service provided by Think-it
            ApiObject p = ScheduleApiController.sendGet(request.getParticipants());
            System.out.println(p);
            List<List<Integer>> hourly = new ArrayList<>();
            JSONArray names = new JSONArray();
            JSONArray times = new JSONArray();
            hourly.add(request.getRange());

            // This function will calculate the common slots based on the input Offset
            slots = findCommonSlotsWithInput(p.getThinkiters(), hourly, request.getParticipants(), request.getOffset());
            times.add(slots.get(0).getTime());

            for (int i = 0; i < slots.size(); i++) {
                String name = slots.get(i).getName();
                names.add(name);
            }

            jsonObject.put("names", names);
            jsonObject.put("times", times);

             
        } catch (Exception e) {
            System.err.println("API CAll: " + e);
        }
        // We send a Json String through XML RPC
        return jsonObject.toString();

    }

    /*
     * This Function will set up the whole availability hours to same offset as the
     * timezone input
     */
    public static List<List<Integer>> updateTimeToInputTimeZone(String offsetInput, String offsetguest,
            List<List<Integer>> availability) {
        int offsetvalueinput = Integer.parseInt(offsetInput.replaceAll("[^0-9]", ""));
        int offsetvalue = Integer.parseInt(offsetguest.replaceAll("[^0-9]", ""));
        int offset = 0;

        if (offsetInput.indexOf("+") != -1 && offsetguest.indexOf("+") != -1
                || offsetInput.indexOf("-") != -1 && offsetguest.indexOf("-") != -1) {

            offset = offsetvalue - offsetvalueinput;

        } else if (offsetInput.indexOf("-") != -1 && offsetguest.indexOf("+") != -1) {
            offset = -(offsetvalue + offsetvalueinput);
        } else if (offsetInput.indexOf("+") != -1 && offsetguest.indexOf("-") != -1) {
            offset = offsetvalue + offsetvalueinput;
        }

        List<List<Integer>> intervalswithOffset = new ArrayList<List<Integer>>();
        if (!offsetInput.equals(offsetguest)) {
            for (int i = 0; i < availability.size(); i++) {
                List<Integer> element = availability.get(i);
                List<Integer> newelement = new ArrayList<Integer>();
                for (int j = 0; j < element.size(); j++) {
                    newelement.add(element.get(j) + offset);

                }
                intervalswithOffset.add(newelement);
            }
        } else {
            intervalswithOffset = availability;
        }
        return intervalswithOffset;

    }

    /*
     * This Function will find Us the common hour slots between admin and guests
     */
    public static <T> Collection<T> intersect(Collection<? extends T> a, Collection<? extends T> b) {
        Collection<T> result = new ArrayList<T>();

        for (T t : a) {
            if (b.remove(t))
                result.add(t);
        }

        return result;
    }

    /*
     * We are going to build with this function to segment the availability ranges
     * by one hour slots
     */

    public List<List<Integer>> oneHourSegmentation(List<List<Integer>> aval) {
        List<List<Integer>> hourlyslots = new ArrayList<List<Integer>>();
        if (aval.size() > 0) {
            for (int j = 0; j < aval.size(); j++) {
                List<Integer> elementer = new ArrayList<Integer>(aval.get(j));
                for (int i = elementer.get(0); i < (elementer.get(1)); i++) {
                    List<Integer> element = new ArrayList<Integer>();
                    element.add(i);
                    element.add(i + 1);
                    hourlyslots.add(element);
                }

            }

        }
        return hourlyslots;
    }

}
