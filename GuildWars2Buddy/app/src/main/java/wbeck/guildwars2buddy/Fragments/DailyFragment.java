package wbeck.guildwars2buddy.Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import wbeck.guildwars2buddy.Gw2_API.Gw2DailyJsonParse;
import wbeck.guildwars2buddy.Gw2_API.Gw2WorldJsonParse;
import wbeck.guildwars2buddy.R;
import wbeck.guildwars2buddy.Structures.DailyLists;
import wbeck.guildwars2buddy.UserData;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DailyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DailyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DailyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match


    private OnFragmentInteractionListener mListener;

    public DailyLists dailyLists;

    public DailyFragment() {
        // Required empty public constructor
        dailyLists = new DailyLists();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment DailyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DailyFragment newInstance() {
        DailyFragment fragment = new DailyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_daily, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            ListView pveList = (ListView) getView().findViewById(R.id.DailyListPvE);

            new Gw2DailyJsonParse(getContext(),dailyLists,pveList).execute("https://api.guildwars2.com/v2/achievements/daily");
        }
        else
        {
            String msg = getResources().getString(R.string.noConnection);
            Toast.makeText(getContext(),msg,Toast.LENGTH_LONG).show();
        }



    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //throw new RuntimeException(context.toString()
            //        + " must implement OnFragmentInteractionListener");
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
