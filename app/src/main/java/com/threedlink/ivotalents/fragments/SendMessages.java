package com.threedlink.ivotalents.fragments;

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

import com.threedlink.ivotalents.common.IvoTalentsApp;
import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.adapters.CustomMessageListAdapter;
import com.threedlink.ivotalents.dtos.Message;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SendMessages.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SendMessages#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SendMessages extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ListView sendMessagesList;
    private IvoTalentsApp mApp;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SendMessages() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SendMessages.
     */
    // TODO: Rename and change types and number of parameters
    public static SendMessages newInstance(String param1, String param2) {
        SendMessages fragment = new SendMessages();
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
        View view =  inflater.inflate(R.layout.fragment_send_messages, container, false);
        // Inflate the layout for this fragment


        initListViewReceiveMessages(view);

        return view;
    }
    private void initListViewReceiveMessages(View view) {
        Resources res = getActivity().getApplicationContext().getResources();

        String[] tempNameMessage = res.getStringArray(R.array.send_message_names);
        String[] tempResumeMessage = res.getStringArray(R.array.send_message_resumes);
        int[] imageAuthors = {R.drawable.circle_gray,R.drawable.circle_gray,R.drawable.circle_gray,R.drawable.circle_gray,R.drawable.circle_gray,R.drawable.circle_gray};
        ArrayList<Message> listMessages = new ArrayList<Message>();
        for (int i=0; i<6;i++){
            Message message = new Message(tempNameMessage[i],tempResumeMessage[i],imageAuthors[i]);
            listMessages.add(message);
        }
        sendMessagesList = (ListView) view.findViewById(R.id.listViewSendMessages);
        sendMessagesList.setAdapter(new CustomMessageListAdapter(getActivity().getApplicationContext(),listMessages));
        sendMessagesList.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mApp.loadFragment(Chat.newInstance(i,"param2"));
                //Toast.makeText(getActivity(),"You selected : " + i,Toast.LENGTH_SHORT).show();
            }

        });
        //View item = sendMessagesList.getAdapter().getView(0, null, sendMessagesList);
        // item.measure(0, 0);
        //android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (3.5 * item.getMeasuredHeight()));
        //sendMessagesList.setLayoutParams(params);

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
