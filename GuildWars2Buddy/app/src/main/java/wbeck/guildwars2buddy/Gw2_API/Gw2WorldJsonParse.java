package wbeck.guildwars2buddy.Gw2_API;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wbeck.guildwars2buddy.UserData;
import wbeck.guildwars2buddy.httpConnect;

/**
 * Created by user on 24/11/2017.
 */

public class Gw2WorldJsonParse extends AsyncTask<String, String, String> {

    final String TAG = "JsonParser.java";

    @Override
    // this method is used for set up (UI)
    protected void onPreExecute() {}

    TextView textView;

    public Gw2WorldJsonParse(TextView view)
    {
        textView = view;
    }


    @Override
    // this method is used for the main bulk task to be done in background
    protected String doInBackground(String... arg0)  {

        String apiCall = arg0[0];
        try {
            //HttpURLConnection con = (HttpsURLConnection) new URL(apiCall+authPrefix+UserData.apiKey)
            //      .openConnection();

            httpConnect jParser = new httpConnect();
            String json = jParser.getJSONFromUrl(apiCall);

            JSONObject world = new JSONObject(json);

            UserData.world_name = world.getString("name");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String[] JsonArrayToStringArray(JSONArray array)
    {
        String[] tmpArry = new String[array.length()];
        //loop through json array and add each tweet to item in arrayList
        for (int i = 0; i < array.length()-1; i++) {
            tmpArry[i] = array.optString(i);
        }

        return tmpArry;
    }

    protected void onPostExecute(String result) {
        textView.setText(UserData.world_name.toString());
    }


}