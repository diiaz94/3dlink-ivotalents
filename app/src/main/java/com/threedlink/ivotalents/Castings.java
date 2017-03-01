package com.threedlink.ivotalents;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.threedlink.ivotalents.Adapters.CustomRecentCastingsListAdapter;
import com.threedlink.ivotalents.DTO.Casting;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Castings.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Castings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Castings extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IvoTalentsApp mApp;

    private OnFragmentInteractionListener mListener;
    private ListView castingList;

    public Castings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Castings.
     */
    // TODO: Rename and change types and number of parameters
    public static Castings newInstance(String param1, String param2) {
        Castings fragment = new Castings();
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
        mApp = ((IvoTalentsApp) getActivity().getApplicationContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_castings, container, false);
        // Inflate the layout for this fragment
        initListViewCastings(view);

        return view;
    }
    private void initListViewCastings(View view) {
        Resources res = getActivity().getApplicationContext().getResources();
        String[] tempCastingCategories = res.getStringArray(R.array.casting_categories);
        String[] tempCastingDescriptions = res.getStringArray(R.array.casting_descriptions);
        String[] tempCastingExpirations = res.getStringArray(R.array.casting_expirations);
        int[] imageCastings = {R.drawable.talent_1, R.drawable.talent_2, R.drawable.juan_esteban, R.drawable.talent_1, R.drawable.talent_2, R.drawable.juan_esteban};
        ArrayList<Casting> listCastings = new ArrayList<Casting>();
        for (int i = 0; i < 3; i++) {
            Casting casting = new Casting(tempCastingCategories[i], tempCastingDescriptions[i], tempCastingExpirations[i], imageCastings[i]);
            listCastings.add(casting);
        }
        castingList = (ListView) view.findViewById(R.id.castingsList);
        castingList.setAdapter(new CustomParticipationsListAdapter(getActivity().getApplicationContext(), listCastings));
    }
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
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
