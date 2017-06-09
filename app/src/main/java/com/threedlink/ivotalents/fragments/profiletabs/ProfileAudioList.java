package com.threedlink.ivotalents.fragments.profiletabs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.adapters.CustomAudiosListAdapter;
import com.threedlink.ivotalents.custom.CustomRetrofitCallback;
import com.threedlink.ivotalents.dtos.MediaResource;
import com.threedlink.ivotalents.utils.Util;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileAudioList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileAudioList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileAudioList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_EMAIL = "email";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mEmail;
    private String mParam2;
    
    private IvoTalentsApp mApp;
    private OnFragmentInteractionListener mListener;
    private Util util;

    @Bind(R.id.audios_container)
    LinearLayout audiosContainer;
    @Bind(R.id.audios_data_info)
    LinearLayout audiosDataInfo;
    @Bind(R.id.audios_list_view)
    ListView audiosListView;
    @Bind(R.id.audios_progress_info)
    LinearLayout audiosProgressInfo;
    @Bind(R.id.audios_text_info)
    TextView audiosTextInfo;
    @Bind(R.id.audios_spinner)
    ProgressBar audiosSpinner;
    public ProfileAudioList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileAudioList.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileAudioList newInstance(String param1, String param2) {
        ProfileAudioList fragment = new ProfileAudioList();
        Bundle args = new Bundle();
        args.putString(ARG_EMAIL, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mEmail = getArguments().getString(ARG_EMAIL);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        mApp = ((IvoTalentsApp) getActivity().getApplicationContext());
        util = new Util();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_audio_list, container, false);
        ButterKnife.bind(this, v);

        boolean isMyOwnProfile = mApp.getEntitySession().getEmail().equalsIgnoreCase(mEmail);
        //uploadImageToProfile.setVisibility(isMyOwnProfile?View.VISIBLE:View.GONE);
        loadListViewData();
        return v;
    }

    private void loadListViewData() {
        audiosContainer.setVisibility(View.VISIBLE);//container
        audiosDataInfo.setVisibility(View.GONE);//data
        audiosProgressInfo.setVisibility(View.VISIBLE);//progress
        audiosTextInfo.setText(getString(R.string.load_participations_started_text));//progress text;


        Call<ArrayList<MediaResource>> mediaResourcesCall = mApp.getApiServiceIntance().mediaResources("","");
        mediaResourcesCall.enqueue(new CustomRetrofitCallback<ArrayList<MediaResource>>(getActivity()) {
            @Override
            protected void handleSuccess(Response response) {
                ArrayList<MediaResource> list = (ArrayList<MediaResource>) response.body();
                Iterator<MediaResource> it = list.iterator();
                while (it.hasNext()) {
                    if (it.next().getType() != MediaResource.MediaResourceType.AUDIO) {
                        it.remove();
                    }
                }
                audiosListView.setAdapter(new CustomAudiosListAdapter(getActivity().getApplicationContext(), list,R.drawable.btn_play,R.drawable.btn_pause_orange));
                audiosDataInfo.setVisibility(View.VISIBLE);
                audiosProgressInfo.setVisibility(View.GONE);
            }

            @Override
            public void failure(Throwable error) {
                audiosTextInfo.setText(getString(R.string.error_load_recents_castings_text));
                audiosSpinner.setVisibility(View.GONE);
            }

        });
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
