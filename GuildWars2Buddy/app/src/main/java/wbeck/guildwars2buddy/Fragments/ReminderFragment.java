package wbeck.guildwars2buddy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import wbeck.guildwars2buddy.R;
import wbeck.guildwars2buddy.Storage;
import wbeck.guildwars2buddy.UserData;
import wbeck.guildwars2buddy.addPermissions;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReminderFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ReminderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReminderFragment extends Fragment {


    Bitmap reminderImage;

    static final int REQUEST_IMAGE_CAPTURE = 1;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ReminderFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ReminderFragment newInstance() {
        ReminderFragment fragment = new ReminderFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
           // mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reminder, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button cameraBtn = (Button) getView().findViewById(R.id.lunchCameraBtn);
        cameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(addPermissions.CheckCameraPermission(getContext()) == true)
                {
                    lunchCameraIntent();
                }
                else
                {
                    addPermissions.cameraPermission(getActivity());
                    if(addPermissions.CheckCameraPermission(getContext()) == true)
                    {
                        lunchCameraIntent();
                    }
                }


            }
        });


        Button saveReminderBtn = (Button) getView().findViewById(R.id.saveReminder) ;
        saveReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveReminder();
            }
        });

        EditText descView = (EditText) getView().findViewById(R.id.reminderText);
        ImageView image = (ImageView) getView().findViewById(R.id.imgPreview);

        SharedPreferences reminderData = getContext().getSharedPreferences("reminder", Context.MODE_PRIVATE);
        descView.setText(reminderData.getString("desc",""));
        String path = "",fileName ="";
        path = reminderData.getString("path", "");
        fileName = reminderData.getString("fileName","");

        image.setImageBitmap(Storage.openmImageFile(path,fileName,getContext()));

        //update the apikey box with the apikey
       // EditText edTxt = (EditText) getView().findViewById(R.id.reminderText);
       // edTxt.setText(UserData.apiKey);

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
             //       + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imgPreview = (ImageView) getView().findViewById(R.id.imgPreview);
            imgPreview.setImageBitmap(imageBitmap);
            reminderImage = imageBitmap;


            if(addPermissions.CheckExternalWritePermission(getContext()) == false) {
                addPermissions.externalWritePermission(getActivity());
            }
        }
    }


    private void lunchCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //make sure a application can handle this
        if (takePictureIntent.resolveActivity(getContext().getPackageManager()) != null) {


            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


    public void SaveReminder()
    {
        File photoFile = null;
        try {
            if(reminderImage != null)
            photoFile = createImageFile();
        }
        catch(Exception e)
        {

        }

        if(photoFile!=null) {
            if(addPermissions.CheckExternalWritePermission(getContext()) == true) {

                SaveImageAndPrefrences(photoFile,reminderImage);
            }
            else
            {
                addPermissions.externalWritePermission(getActivity());
                if(addPermissions.CheckExternalWritePermission(getContext()) == true) {

                    SaveImageAndPrefrences(photoFile,reminderImage);
                }


            }
        }
    }

    private void SaveImageAndPrefrences(File file, Bitmap imageBitmap)
    {
        Storage.writeImageFile(file, imageBitmap, getContext());

        EditText descView = (EditText) getView().findViewById(R.id.reminderText);
        SharedPreferences userData = getContext().getSharedPreferences("reminder", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = userData.edit();
        editor.putString("desc", descView.getText().toString());

        String path = file.getAbsolutePath().toString();
         path = path.substring(0,path.lastIndexOf(File.separator));

        editor.putString("path", path);
        editor.putString("fileName", file.getName().toString());
        editor.commit();
    }

    private File createImageFile()
    {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = "GW2_Buddy_" + timeStamp;

            File storageDir = getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File image = File.createTempFile(fileName, ".jpg", storageDir);

            return image;
        }
        catch (Exception e)
        {
            return null;
        }



    }

}
