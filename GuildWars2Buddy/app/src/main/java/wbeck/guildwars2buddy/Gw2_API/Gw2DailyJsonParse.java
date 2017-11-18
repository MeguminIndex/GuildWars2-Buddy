package wbeck.guildwars2buddy.Gw2_API;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wbeck.guildwars2buddy.Structures.DailyListItem;
import wbeck.guildwars2buddy.UserData;
import wbeck.guildwars2buddy.httpConnect;

/**
 * Created by user on 18/11/2017.
 */

public class Gw2DailyJsonParse extends AsyncTask<String, String, String>
    {

        final String TAG = "JsonParser.java";

        @Override
        // this method is used for set up (UI)
        protected void onPreExecute() {}

        DailyLists dailyLists;

        Gw2DailyJsonParse(DailyLists listsIn)
        {
            dailyLists = listsIn;
        }

        private String authPrefix ="?access_token=";

        @Override
        // this method is used for the main bulk task to be done in background
        protected String doInBackground(String... arg0)  {

        String apiCall = arg0[0];
        try {
            //HttpURLConnection con = (HttpsURLConnection) new URL(apiCall+authPrefix+UserData.apiKey)
            //      .openConnection();

            httpConnect jParser = new httpConnect();
            String json = jParser.getJSONFromUrl(apiCall);

            JSONObject daily = new JSONObject(json);

            JSONArray pveJArray = daily.getJSONArray("pve");

            dailyLists.pve = JsonDailyArrayToDailyList(pveJArray);


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

        private List<DailyListItem> JsonDailyArrayToDailyList(JSONArray jArray)
        {
            List<DailyListItem> tmpList = new ArrayList<DailyListItem>();




            return tmpList;
        }


    protected void onPostExecute(String result) {
        //textView.setText(UserData.name.toString());
    }



}
