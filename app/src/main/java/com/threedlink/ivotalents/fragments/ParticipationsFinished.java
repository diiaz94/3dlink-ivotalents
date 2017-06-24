package com.threedlink.ivotalents.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.threedlink.ivotalents.custom.CustomRetrofitCallback;
import com.threedlink.ivotalents.dtos.Casting;
import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.utils.enums.Rol;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ParticipationsFinished.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ParticipationsFinished#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ParticipationsFinished extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IvoTalentsApp mApp;

    private OnFragmentInteractionListener mListener;

    @Bind(R.id.participations_finished_container)
    LinearLayout participationsFinishedContainer;
    @Bind(R.id.participations_finished_data_info)
    LinearLayout participationsFinishedDataInfo;
    @Bind(R.id.participations_finished_list_view)
    ListView participationsFinishedListView;
    @Bind(R.id.participations_finished_progress_info)
    LinearLayout participationsFinishedProgressInfo;
    @Bind(R.id.participations_finished_text_info)
    TextView participationsFinishedTextInfo;
    @Bind(R.id.participations_finished_spinner)
    ProgressBar participationsFinishedSpinner;

    private ArrayList<Casting> participationsFinishedList;
    private CustomParticipationsListAdapter participationsFinishedAdapter;

    public ParticipationsFinished() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ParticipationsFinished.
     */
    // TODO: Rename and change types and number of parameters
    public static ParticipationsFinished newInstance(String param1, String param2) {
        ParticipationsFinished fragment = new ParticipationsFinished();
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
        View v = inflater.inflate(R.layout.fragment_participations_finished, container, false);
        ButterKnife.bind(this, v);
        //initListViewAuditions(view);
        loadParticipationsFinished();
        return v;
    }

    private void loadParticipationsFinished() {
        participationsFinishedContainer.setVisibility(View.VISIBLE);//container
        participationsFinishedDataInfo.setVisibility(View.GONE);//data
        participationsFinishedProgressInfo.setVisibility(View.VISIBLE);//progress
        participationsFinishedTextInfo.setText(getString(R.string.load_participations_finished_text));//progress text;
        Log.i("TAG","paso1");
        participationsFinishedListView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(Rol.valueOf(mApp.getEntitySession().getRole()) == Rol.ARTISTA){
                    mApp.loadFragment(ArtistCasting.newInstance("","param2"));
                }
                if(Rol.valueOf(mApp.getEntitySession().getRole()) == Rol.INDUSTRIA){
                    mApp.loadFragment(IndustryCasting.newInstance("","param2"));
                }

                //Toast.makeText(getActivity(),"You selected : " + i,Toast.LENGTH_SHORT).show();
            }

        });
        Call<ArrayList<Casting>> castingCall = mApp.getApiServiceIntance().participations("");
        castingCall.enqueue(new CustomRetrofitCallback<ArrayList<Casting>>() {
            @Override
            public void handleSuccess(Response response) {
                participationsFinishedListView.setAdapter(new CustomParticipationsListAdapter(getActivity().getApplicationContext(), (ArrayList<Casting>) response.body()));
                participationsFinishedDataInfo.setVisibility(View.VISIBLE);
                participationsFinishedProgressInfo.setVisibility(View.GONE);
            }

            @Override
            public void handleError(Response response) {
                participationsFinishedTextInfo.setText(getString(R.string.error_load_recents_castings_text));
                participationsFinishedSpinner.setVisibility(View.GONE);
            }

        });
    }

    private void initListViewAuditions(View view) {
        Resources res = getActivity().getApplicationContext().getResources();
        String[] tempCastingCategories = res.getStringArray(R.array.casting_categories);
        String[] tempCastingDescriptions = res.getStringArray(R.array.casting_descriptions);
        String[] tempCastingExpirations = res.getStringArray(R.array.casting_expirations);
        int[] imageCastings = {R.drawable.talent_1, R.drawable.talent_2, R.drawable.juan_esteban, R.drawable.talent_1, R.drawable.talent_2, R.drawable.juan_esteban};
        ArrayList<com.threedlink.ivotalents.dtos.Casting> listCastings = new ArrayList<com.threedlink.ivotalents.dtos.Casting>();
        for (int i = 0; i < 3; i++) {
            com.threedlink.ivotalents.dtos.Casting casting = new com.threedlink.ivotalents.dtos.Casting(tempCastingCategories[i], tempCastingDescriptions[i], tempCastingExpirations[i], imageCastings[i]);
            listCastings.add(casting);
        }
        //auditionList = (ListView) view.findViewById(R.id.auditionsList);
        //auditionList.setAdapter(new CustomParticipationsListAdapter(getActivity().getApplicationContext(), listCastings));
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
