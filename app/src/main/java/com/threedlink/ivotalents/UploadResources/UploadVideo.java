package com.threedlink.ivotalents.UploadResources;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.CameraFragmentApi;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.threedlink.ivotalents.R;

import butterknife.Bind;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UploadVideo.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadVideo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadVideo extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private View mView;
    public UploadVideo() {
        // Required empty public consViewtructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadVideo.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadVideo newInstance(String param1, String param2) {
        UploadVideo fragment = new UploadVideo();
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
        mView = inflater.inflate(R.layout.fragment_upload_video, container, false);



        return mView;
    }
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //you are visible to user now - so set whatever you need
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                initView();
            }
        }
        else {
            //you are no longer visible to the user so cleanup whatever you need
            clearView();
        }
    }


    @RequiresPermission(Manifest.permission.CAMERA)
    private void initView() {
        if(getActivity()!=null) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                final CameraFragment cameraFragment = CameraFragment.newInstance(new Configuration.Builder()
                        .setCamera(Configuration.CAMERA_FACE_REAR).build());
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(mView.findViewById(R.id.container_video).getId(), cameraFragment, this.getClass().getSimpleName())
                        .commitAllowingStateLoss();


            }
        }
    }
    private void clearView() {
        if(getActivity()!=null) {
            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(this.getClass().getSimpleName());
            if(fragment!=null)
                getActivity().getSupportFragmentManager().beginTransaction().remove(fragment);
        }
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

    private CameraFragmentApi getCameraFragment() {
        return (CameraFragmentApi) getActivity().getSupportFragmentManager().findFragmentByTag(this.getClass().getSimpleName());
    }
}
