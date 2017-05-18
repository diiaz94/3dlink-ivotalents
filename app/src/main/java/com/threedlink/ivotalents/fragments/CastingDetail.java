package com.threedlink.ivotalents.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.threedlink.ivotalents.IvoTalentsApp;
import com.threedlink.ivotalents.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CastingDetail.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CastingDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CastingDetail extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int step;
    private String mParam1;
    private String mParam2;
    private IvoTalentsApp mApp;

    private ImageButton backStep;
    private ImageButton nextStep;
    private OnFragmentInteractionListener mListener;
    private LinearLayout castingDetailStep2;
    private LinearLayout castingDetailStep3;

    public CastingDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CastingDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static CastingDetail newInstance(int step, String param1, String param2) {
        CastingDetail fragment = new CastingDetail();
        Bundle args = new Bundle();
        args.putInt("StepInit",step);
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApp = ((IvoTalentsApp) getActivity().getApplicationContext());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            step = getArguments().getInt("StepInit");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_detail_casting, container, false);

        backStep = (ImageButton) view.findViewById(R.id.backStepCasting);
        nextStep = (ImageButton) view.findViewById(R.id.nextStepCasting);
        backStep.setOnClickListener(this);
        nextStep.setOnClickListener(this);
        castingDetailStep2 = (LinearLayout) view.findViewById(R.id.casting_detail_step_2);
        castingDetailStep3 = (LinearLayout) view.findViewById(R.id.casting_detail_step_3);
        if (step==2)  castingDetailStep2.setVisibility(View.VISIBLE);
        if (step==3)  castingDetailStep3.setVisibility(View.VISIBLE);

        LinearLayout myLayout = (LinearLayout) view.findViewById(R.id.fragment_detail_casting);
        mApp.setFontsOnLinear(myLayout);
        // Inflate the layout for this fragment
        return view;
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

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.backStepCasting:
                if(step==2) {
                    getActivity().getSupportFragmentManager().popBackStack();
                }else if(step==3){
                    castingDetailStep3.setVisibility(View.GONE);
                    castingDetailStep2.setVisibility(View.VISIBLE);
                    step--;
                }
                    break;
            case R.id.nextStepCasting:
                if(step==2) {
                    castingDetailStep2.setVisibility(View.GONE);
                    castingDetailStep3.setVisibility(View.VISIBLE);
                    step++;

                }else if(step==3){
                    mApp.loadFragment(UploadResource.newInstance("param1", "param2"));
                }
                break;
        }
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
