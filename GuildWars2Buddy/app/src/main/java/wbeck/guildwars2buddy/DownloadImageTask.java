package wbeck.guildwars2buddy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by Index on 23/10/2017.
 */


public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView imageV;

    public DownloadImageTask(ImageView imageview) {
        imageV = imageview;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap bitM = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            bitM = BitmapFactory.decodeStream(in);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitM;
    }

    protected void onPostExecute(Bitmap result) {
        imageV.setImageBitmap(result);
    }
}
