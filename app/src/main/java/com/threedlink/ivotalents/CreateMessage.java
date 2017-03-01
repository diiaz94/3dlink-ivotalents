package com.threedlink.ivotalents;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.threedlink.ivotalents.Adapters.CustomContactMessageListAdapter;
import com.threedlink.ivotalents.Adapters.CustomMessageListAdapter;
import com.threedlink.ivotalents.DTO.Contact;
import com.threedlink.ivotalents.DTO.Message;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateMessage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateMessage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateMessage extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IvoTalentsApp mApp;
    private EditText nameToMessage;
    private OnFragmentInteractionListener mListener;
    private LinearLayout autocompleteContact;
    private ListView autocompleteContactList;
    private LinearLayout sentMessage;
    private String[] tempNameMessage;


    public CreateMessage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateMessage.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateMessage newInstance(String param1, String param2) {
        CreateMessage fragment = new CreateMessage();
        Bundle args = new Bundle();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_create_message, container, false);

        sentMessage = (LinearLayout) view.findViewById(R.id.linear_sent_message);
        sentMessage.setOnClickListener(this);
        nameToMessage = (EditText) view.findViewById(R.id.nameToMessage);
        autocompleteContact = (LinearLayout) view.findViewById(R.id.autocompleteContact);
        nameToMessage.measure(0, 0);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)autocompleteContact.getLayoutParams();
        params.setMargins(params.leftMargin,params.topMargin+nameToMessage.getMeasuredHeight(),params.rightMargin,0);
        autocompleteContact.setLayoutParams(params);
        TextWatcher fieldValidatorTextWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (filterLongEnough()) {
                   populateList(s.toString());
                    autocompleteContact.setVisibility(View.VISIBLE);
                }else{
                    autocompleteContact.setVisibility(View.GONE);
                }
            }

            private boolean filterLongEnough() {
                return nameToMessage.getText().toString().trim().length() > 2;
            }
        };
        nameToMessage.addTextChangedListener(fieldValidatorTextWatcher);

        autocompleteContactList = (ListView) view.findViewById(R.id.autocompleteContactList);
        autocompleteContactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                nameToMessage.setText(tempNameMessage[position]);
                autocompleteContact.setVisibility(View.GONE);

            }
        });

        LinearLayout myLayout = (LinearLayout) view.findViewById(R.id.fragment_create_message);
        mApp.setFontsOnLinear(myLayout);

        return view;
    }

    private void populateList(String text) {
        Resources res = getActivity().getApplicationContext().getResources();
        tempNameMessage = res.getStringArray(R.array.contact_names);
        String[] tempResumeMessage = res.getStringArray(R.array.contact_sectors);
        int[] imageAuthors = {R.drawable.circle_gray,R.drawable.circle_gray,R.drawable.circle_gray,R.drawable.circle_gray,R.drawable.circle_gray,R.drawable.circle_gray};

        ArrayList<Contact> listMessages = new ArrayList<Contact>();

        for (int i=0; i<6;i++){
            Contact contact = new Contact(tempNameMessage[i],tempResumeMessage[i],imageAuthors[i]);
            listMessages.add(contact);
        }
        autocompleteContactList.setAdapter(new CustomContactMessageListAdapter(getActivity().getApplicationContext(),listMessages));

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
            case R.id.linear_sent_message:
                Fragment fragment = null;
                fragment = Chat.newInstance("param1","param2");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).addToBackStack( fragment.getClass().getSimpleName() ).commit();

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
