package wbeck.guildwars2buddy;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Locale;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Index on 10/11/2017.
 */

public class Storage {


    public static void writeTxtFile(String filename,String data, Context context)
    {
        FileOutputStream outputStream;
        File fileStreamPath = context.getFileStreamPath(filename);
        if(fileStreamPath == null || !fileStreamPath.exists())
        {
            try {
                outputStream = context.openFileOutput(filename,MODE_PRIVATE);
                outputStream.write(data.getBytes());
                outputStream.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
        else if(fileStreamPath.exists())
        {
            try{
                outputStream = context.openFileOutput(filename, MODE_APPEND);
                outputStream.write(data.getBytes());
                outputStream.close();
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }


    }

    public static void writeImageFile(File file, Bitmap data, Context context)
    {

        File fileStreamPath = file;
        if(fileStreamPath.exists())
        {
            try{
                OutputStream fOutStream = new FileOutputStream(file);

                //example of scaling a bitmap
                //Bitmap thumbnail = Bitmap.createScaledBitmap(originalBitmap, width, height, false)
                data.compress(Bitmap.CompressFormat.JPEG,100,fOutStream);

                fOutStream.flush();
                fOutStream.close();

                MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), file.getName());
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }


    }

    public static String openTxtFile(String filename,Context context)
    {
        StringBuilder sb = new StringBuilder("");
        try{
            FileInputStream fInS = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fInS);
            BufferedReader buffreader = new BufferedReader(isr);
            String readString = buffreader.readLine();
            while(readString != null) {
                sb.append(readString);
                readString = buffreader.readLine();
            }


            isr.close();//close Stream;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();

    }


    public static Bitmap openmImageFile(File file,Context context)
    {

        Bitmap thumbnail = null;

        // Look for the file on the external storage
        try {
            if (isExternalReadable() == true) {
                thumbnail = BitmapFactory.decodeFile(file.getAbsolutePath());
            }
        } catch (Exception e) {
            //Log.e("getThumbnail() on external storage", e.getMessage());
        }

        // If no file on external storage, look in internal storage
        if (thumbnail == null) {
            try {
                File filePath = context.getFileStreamPath(file.getName());
                FileInputStream fi = new FileInputStream(filePath);
                thumbnail = BitmapFactory.decodeStream(fi);
            } catch (Exception ex) {
              //  Log.e("getThumbnail() on internal storage", ex.getMessage());
            }
        }
        return thumbnail;

    }

    private static boolean isExternalReadable()
    {

        boolean storageAvailable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state) ) {
            // We can read and write the media
            storageAvailable = true;
        }
        else {
            //problem happened
            storageAvailable = false;
        }

        return storageAvailable;

    }


}
