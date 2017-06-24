package com.threedlink.ivotalents.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.adapters.CustomFollowingViewAdapter;
import com.threedlink.ivotalents.adapters.MyUserListRecyclerViewAdapter;
import com.threedlink.ivotalents.asynctasks.FontApplyTask;
import com.threedlink.ivotalents.common.domain.ServiceGenerator;
import com.threedlink.ivotalents.common.services.IvoService;
import com.threedlink.ivotalents.custom.CustomRetrofitCallback;
import com.threedlink.ivotalents.dtos.RolEntity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsersList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UsersList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsersList extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private DataSource mDataSource;
    private String mTitle;

    private OnFragmentInteractionListener mListener;


    @Bind(R.id.entities_recycler_view)
    RecyclerView entitiesRecyclerView;
    @Bind(R.id.progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.container_list)
    LinearLayout containerList;

    @Bind(R.id.list_title)
    TextView listTitle;

    public UsersList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsersList.
     */
    // TODO: Rename and change types and number of parameters
    public static UsersList newInstance(DataSource param1, String param2) {
        UsersList fragment = new UsersList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1.toString());
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mDataSource = DataSource.valueOf(getArguments().getString(ARG_PARAM1));
            mTitle = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_users_list, container, false);
        ButterKnife.bind(this,v);
        entitiesRecyclerView.setLayoutManager(new GridLayoutManager(v.getContext(), 2));
        listTitle.setText(mTitle);
        listTitle.setTypeface(FontApplyTask.getFontBold(getActivity()));
        switch (mDataSource){
            case FOLLOWERS:
                loadFollowers();
                break;
            case FOLLOWEDS:
                loadFolloweds();
                break;
        }

        return v;
    }

    private void loadFollowers() {

        progressBar.setVisibility(View.VISIBLE);

        ServiceGenerator.getService(IvoService.class)
                .followers().enqueue(new CustomRetrofitCallback<ArrayList<RolEntity>>() {
            @Override
            public void handleSuccess(Response response) {
                ArrayList<RolEntity> list = ( ArrayList<RolEntity> ) response.body();
                entitiesRecyclerView.setAdapter(new MyUserListRecyclerViewAdapter(getActivity(),list));
                progressBar.setVisibility(View.GONE);
                containerList.setVisibility(View.VISIBLE);

            }

            @Override
            public void handleError(Response response) {

            }
        });
    }

    private void loadFolloweds() {
        progressBar.setVisibility(View.VISIBLE);

        ServiceGenerator.getService(IvoService.class)
                .followers().enqueue(new CustomRetrofitCallback<ArrayList<RolEntity>>() {
            @Override
            public void handleSuccess(Response response) {
                ArrayList<RolEntity> list = ( ArrayList<RolEntity> ) response.body();
                entitiesRecyclerView.setAdapter(new MyUserListRecyclerViewAdapter(getActivity(),list));
                progressBar.setVisibility(View.GONE);
                containerList.setVisibility(View.VISIBLE);

            }

            @Override
            public void handleError(Response response) {

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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    enum DataSource{
        FOLLOWERS,
        FOLLOWEDS
    }
}
