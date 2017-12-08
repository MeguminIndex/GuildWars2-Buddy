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
import android.net.Uri;

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
        FileOutputStream outputStream;
        File fileStreamPath = file;
        if(fileStreamPath == null || !fileStreamPath.exists())
        {
            try {

               // OutputStream fOutStream = null;
              //  data.compress(Bitmap.CompressFormat.JPEG,100,fOutStream);

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



}
