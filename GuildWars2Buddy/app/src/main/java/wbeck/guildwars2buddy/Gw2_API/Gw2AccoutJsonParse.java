package wbeck.guildwars2buddy.Gw2_API;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import wbeck.guildwars2buddy.UserData;
import wbeck.guildwars2buddy.httpConnect;


/**
 * Created by Index on 11/11/2017.
 */

public class Gw2AccoutJsonParse extends AsyncTask<String, String, String>{

    final String TAG = "JsonParser.java";

    @Override
    // this method is used for set up (UI)
    protected void onPreExecute() {}

    TextView textView;

    public Gw2AccoutJsonParse(TextView view)
    {
        textView = view;
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
            try{
            String authCheck = jParser.getJSONFromUrl("https://api.guildwars2.com/v2/tokeninfo"+authPrefix + UserData.apiKey);
             if(authCheck != null) {
                 JSONObject authCheckOBJ = new JSONObject(authCheck);
                //could check what permissions the key has

             }
            }
            catch(Exception e)
            {

            }

            String json = jParser.getJSONFromUrl(apiCall+authPrefix+ UserData.apiKey);
            if(json != null) {
                JSONObject accout = new JSONObject(json);

                UserData.id = accout.getString("id");
                UserData.name = accout.getString("name");
                UserData.world = accout.getInt("world");

                JSONArray jaccessArray;
                jaccessArray = accout.getJSONArray("guilds");
                UserData.guilds = JsonArrayToStringArray(jaccessArray);

                jaccessArray = accout.getJSONArray("access");
                UserData.access = JsonArrayToStringArray(jaccessArray);

                UserData.fractal_level = accout.getInt("fractal_level");
                UserData.daily_ap = accout.getInt("daily_ap");
                UserData.monthly_ap = accout.getInt("monthly_ap");
                UserData.wvw_rank = accout.getInt("wvw_rank");
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

    protected void onPostExecute(String result) {

        if(!UserData.name.isEmpty())
        textView.setText(UserData.name.toString());
    }


}
