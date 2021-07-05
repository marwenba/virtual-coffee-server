package asklepios.thinkit.api;

import org.springframework.stereotype.Controller;

import asklepios.thinkit.models.ApiObject;
import okhttp3.*;

import java.io.IOException;

import com.google.gson.Gson;


@Controller
public class ScheduleApiController {

    // Define the URL where we will call the remote Open API and get its data
    private static String url = "https://z57yb0rrik.execute-api.eu-central-1.amazonaws.com/";
    private static String api = "dev/schedule";
    private static String quest = "?guests=";
    private static String xThinkitCustom = "dasee1213d";

    // HTTP Client that will call the remote API
    private static OkHttpClient httpClient = new OkHttpClient();



    // Main Function that will provide us the data from the API
    public static ApiObject sendGet(Integer guests) throws Exception {
        ApiObject p = new ApiObject();
        Request request = new Request.Builder().url(url + api + quest + guests)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .addHeader("x-thinkit-custom", xThinkitCustom) // Think IT Header
                .build();

        try (Response response = httpClient.newCall(request).execute()) {

            if (!response.isSuccessful())
                throw new IOException("Unexpected code " + response);
        
            // Converting the response to a defined Object using Gson
            Gson g = new Gson();
            String avGuests = response.body().string();
            if (avGuests != null) {
                p = g.fromJson(avGuests, ApiObject.class);
            }
            // Get response body
            return p;
        }

    }

}
