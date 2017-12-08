package wbeck.guildwars2buddy;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

/**
 * Created by user on 08/12/2017.
 */

public class addPermissions {


    public static void cameraPermission(Activity mainActivity)
    {

        ActivityCompat.requestPermissions(mainActivity, new String[]{Manifest.permission.CAMERA},1);


    }

    public static Boolean CheckCameraPermission(Context mainActivityContext)
    {
        if( ContextCompat.checkSelfPermission(mainActivityContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }

        return false;
    }




}
