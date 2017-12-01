package wbeck.guildwars2buddy.Gw2_API;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wbeck.guildwars2buddy.DailyItemArrayAdapter;
import wbeck.guildwars2buddy.Structures.DailyListItem;
import wbeck.guildwars2buddy.Structures.DailyLists;
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

        Context context;
        DailyLists dailyLists;

        ListView pve;

        public Gw2DailyJsonParse(Context context, DailyLists listsIn, ListView pveList)
        {
            this.context = context;
            dailyLists = listsIn;
            pve = pveList;
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

           for(int i = 0; i < dailyLists.pve.size(); i++)
           {

               DailyListItem item  = dailyLists.pve.get(i);

               String jsonTmp = jParser.getJSONFromUrl("https://api.guildwars2.com/v2/achievements/"+ String.valueOf(item.id));
               JSONObject dailyItem = new JSONObject(json);

               item.name = dailyItem.getString("name");
               item.description = dailyItem.getString("description");
               item.requirements = dailyItem.getString("requirement");

               dailyLists.pve.set(i,item);


           }

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

        private ArrayList<DailyListItem> JsonDailyArrayToDailyList(JSONArray jArray) throws JSONException {
            ArrayList<DailyListItem> tmpList = new ArrayList<DailyListItem>();

            for (int i = 0; i < jArray.length()-1; i++) {

                JSONObject item = jArray.getJSONObject(i);

                int id =  item.getInt("id");

                DailyListItem dailyItem = new DailyListItem();
                dailyItem.id = id;

                httpConnect jParser = new httpConnect();
                String jsonTmp = jParser.getJSONFromUrl("https://api.guildwars2.com/v2/achievements/"+ String.valueOf(dailyItem.id));
                JSONObject jObhDaily = new JSONObject(jsonTmp);

                dailyItem.name = jObhDaily.getString("name");
                dailyItem.description = jObhDaily.getString("description");
                dailyItem.requirements = jObhDaily.getString("requirement");


                tmpList.add(dailyItem);
            }



            return tmpList;
        }


    protected void onPostExecute(String result) {
        //textView.setText(UserData.name.toString());

        DailyItemArrayAdapter adapter = new DailyItemArrayAdapter(context,dailyLists.pve);
        pve.setAdapter(adapter);
    }



}
