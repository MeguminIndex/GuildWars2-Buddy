package wbeck.guildwars2buddy.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.AndroidException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import wbeck.guildwars2buddy.Gw2_API.Gw2WorldJsonParse;
import wbeck.guildwars2buddy.R;
import wbeck.guildwars2buddy.UserData;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        return inflater.inflate(R.layout.fragment_account, container, false);
    }




    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //update the apikey box with the apikey

        if(!UserData.name.isEmpty()) {
            TextView accountname = (TextView) getView().findViewById(R.id.acount_name_frag);
            accountname.setText(UserData.name);
        }
        TextView world = (TextView) getView().findViewById(R.id.account_world);
        if(UserData.world_name == null || !UserData.world_name.isEmpty()) {

            ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                new Gw2WorldJsonParse(world).execute("https://api.guildwars2.com/v2/worlds/" + String.valueOf(UserData.world));
            }
            else
            {
                String msg = getResources().getString(R.string.noConnection);
                Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
            }
        }
        else
        world.setText(String.valueOf(UserData.world_name));

            if(UserData.fractal_level != -1) {
                TextView fractalLvL = (TextView) getView().findViewById(R.id.fractal_level);
                fractalLvL.setText(String.valueOf(UserData.fractal_level));
            }
            if(UserData.wvw_rank != -1) {
                TextView wvwRank = (TextView) getView().findViewById(R.id.wvw_rank);
                wvwRank.setText(String.valueOf(UserData.wvw_rank));
            }

            if(UserData.monthly_ap != -1) {
                TextView monthlyAP = (TextView) getView().findViewById(R.id.monthly_ap);
                monthlyAP.setText(String.valueOf(UserData.monthly_ap));
            }
            if(UserData.daily_ap != -1) {
                TextView dailyAp = (TextView) getView().findViewById(R.id.daily_ap);
                dailyAp.setText(String.valueOf(UserData.daily_ap));
            }

//            if(UserData.access != null) {
//                ListView accessList = (ListView) getView().findViewById(R.id.accessContentList);
//                ArrayList<String> data = new ArrayList<String>(Arrays.asList(UserData.access));
//                ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, data);
//               // accessList.setAdapter(adapter);
//
//            }



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
           // throw new RuntimeException(context.toString()
                   // + " must implement OnFragmentInteractionListener");
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
}
