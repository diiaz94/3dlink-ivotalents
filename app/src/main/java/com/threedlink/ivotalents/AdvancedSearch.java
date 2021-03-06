package com.threedlink.ivotalents;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AdvancedSearch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AdvancedSearch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdvancedSearch extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static int marginTop = 165;
    private static int marginTop2 = 100;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private IvoTalentsApp mApp;
    private int idxLayoutActual;
    private OnFragmentInteractionListener mListener;
    private static int LimitFilters=5;
    private LinearLayout nextFilter;
    private LinearLayout backFilter;
    private LinearLayout current;
    private LinearLayout next;
    private LinearLayout[] filtersLayouts = new LinearLayout[LimitFilters];
    private LinearLayout layoutBuscar;

    private TextView selectEtnia;
    private String[] selectEtniaArray = {"Blanco","Negro"};
    private TextView selectEyeColor;
    private String[] selectEyeColorArray = {"Verde","Azul"};
    private TextView selectHairColor;
    private String[] selectHairColorArray = {"Castaño","Negro"};
    private TextView selectCountry;
    private String[] selectCountryArray = {"Venezuela","Panama","Colombia"};
    private TextView selectCity;
    private String[] selectCityArray = {"Caracas","Bogota","Panama"};

    private LinearLayout searchList;
    private LinearLayout searchListRoot;
    private TextView searchListRowTemplateText;
    private LinearLayout searchListRowTemplate;

    private LinearLayout searchList2;
    private LinearLayout searchListRoot2;
    private TextView searchListRowTemplateText2;
    private LinearLayout searchListRowTemplate2;

    private boolean isSelectOpened;
    TextView select = null;
    TextView select2 = null;
    private boolean isSelectOpened2;

    public AdvancedSearch() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AdvancedSearch.
     */
    // TODO: Rename and change types and number of parameters
    public static AdvancedSearch newInstance(String param1, String param2) {
        AdvancedSearch fragment = new AdvancedSearch();
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
        isSelectOpened = false;
        isSelectOpened2 = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_advanced_search, container, false);
        RelativeLayout myLayout = (RelativeLayout) view.findViewById(R.id.fragment_advanced_search);
        mApp.setFontsOnRelative(myLayout);
        idxLayoutActual=0;
        nextFilter = (LinearLayout) view.findViewById(R.id.nextFilter);
        nextFilter.setOnClickListener(this);
        backFilter = (LinearLayout) view.findViewById(R.id.backFilter);
        backFilter.setOnClickListener(this);
        layoutBuscar = (LinearLayout) view.findViewById(R.id.layoutBuscar);

        for (int i=0;i<this.filtersLayouts.length;i++) {

            filtersLayouts[i] = (LinearLayout) view.findViewById(mApp.getResourcebyname("filter_"+String.valueOf(i)));

        }

        selectEtnia = (TextView) view.findViewById(R.id.select_etnia);
        selectEtnia.setOnClickListener(this);
        selectEyeColor = (TextView) view.findViewById(R.id.select_eye_color);
        selectEyeColor.setOnClickListener(this);
        selectHairColor = (TextView) view.findViewById(R.id.select_hair_color);
        selectHairColor.setOnClickListener(this);

        selectCountry= (TextView) view.findViewById(R.id.select_pais);
        selectCountry.setOnClickListener(this);
        selectCity = (TextView) view.findViewById(R.id.select_ciudad);
        selectCity.setOnClickListener(this);

        searchList = (LinearLayout) view.findViewById(R.id.search_list);
        searchListRoot = (LinearLayout) view.findViewById(R.id.search_list_root);
        searchListRowTemplate = (LinearLayout) view.findViewById(R.id.search_list_row_template);
        searchListRowTemplateText = (TextView) view.findViewById(R.id.search_list_row_template_text);

        searchList2 = (LinearLayout) view.findViewById(R.id.search_list_2);
        searchListRoot2 = (LinearLayout) view.findViewById(R.id.search_list_root_2);
        searchListRowTemplate2 = (LinearLayout) view.findViewById(R.id.search_list_row_template_2);
        searchListRowTemplateText2 = (TextView) view.findViewById(R.id.search_list_row_template_text_2);

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
        /*if(isSelectOpened){
            closeSelect(v);
           isSelectOpened=false;
        }
        if(isSelectOpened2){
            closeSelect2(v);
            isSelectOpened2=false;
        }*/
        switch (id) {
            case R.id.nextFilter:
                nextFilter(v);
                break;
            case R.id.backFilter:
                backFilter(v);
                break;
            case R.id.select_etnia:
                showSelect(v,"Etnia");
                break;
            case R.id.select_eye_color:
                showSelect(v,"EyeColor");
                break;
            case R.id.select_hair_color:
                showSelect(v,"HairColor");
                break;
            case R.id.select_pais:
                showSelect2(v,"Pais");
                break;
            case R.id.select_ciudad:
                showSelect2(v,"Ciudad");
                break;

        }
    }

    private void showSelect2(View v, String type) {
        int offset = 145;

        if(!isSelectOpened2){
            isSelectOpened2 = true;
            String[] array = null;
            int margin = marginTop2;
            switch (type){
                case "Pais":
                    array = this.selectCountryArray;
                    select2 = this.selectCountry;
                    break;
                case "Ciudad":
                    array = this.selectCityArray;
                    margin = margin + offset;
                    select2 = this.selectCity;
                    break;

            }

            ViewGroup.LayoutParams paramsLayout = searchListRowTemplate2.getLayoutParams();
            ViewGroup.LayoutParams paramsTextView = searchListRowTemplateText2.getLayoutParams();
            for (int i = 0; i<array.length;i++){
                TextView text = new TextView(getActivity().getApplicationContext());
                text.setLayoutParams(paramsTextView);
                text.setText(array[i]);
                text.setTypeface(mApp.getFont());
                LinearLayout row = new LinearLayout(getActivity().getApplicationContext());
                row.setLayoutParams(paramsLayout);
                row.addView(text);
                row.setVisibility(View.VISIBLE);
                row.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        LinearLayout l = (LinearLayout) v;
                        TextView t = (TextView) l.getChildAt(0);
                        select2.setText(t.getText());
                        select2.setTypeface(mApp.getFontBold());
                        closeSelect2(v.getRootView());
                    }
                });
                searchListRoot2.addView(row);
            }
            RelativeLayout.LayoutParams  params = (RelativeLayout.LayoutParams) searchList2.getLayoutParams();
            params.setMargins(0,margin,0,0);
            searchList2.setLayoutParams(params);
            searchList2.setVisibility(View.VISIBLE);
        }else{
            closeSelect2(v);
        }

    }

    private void closeSelect2(View v) {
        searchListRoot2.removeAllViews();
        searchList2.setVisibility(View.GONE);
        isSelectOpened2 =false;
    }

    private void closeSelect(View v) {
        searchListRoot.removeAllViews();
        searchList.setVisibility(View.GONE);
        isSelectOpened =false;
    }

    private void showSelect(View v,String type) {
    int offset = 75;

        if(!isSelectOpened){
            isSelectOpened = true;
            String[] array = null;
            int margin = marginTop;
            switch (type){
                case "Etnia":
                    array = this.selectEtniaArray;
                    select = this.selectEtnia;
                    break;
                case "EyeColor":
                    array = this.selectEyeColorArray;
                    margin = margin + offset;
                    select = this.selectEyeColor;
                    break;
                case "HairColor":
                    array = this.selectHairColorArray;
                    margin = margin + offset*2;
                    select = this.selectHairColor;
                    break;
            }

            ViewGroup.LayoutParams paramsLayout = searchListRowTemplate.getLayoutParams();
            ViewGroup.LayoutParams paramsTextView = searchListRowTemplateText.getLayoutParams();
            for (int i = 0; i<array.length;i++){
                TextView text = new TextView(getActivity().getApplicationContext());
                text.setLayoutParams(paramsTextView);
                text.setText(array[i]);
                text.setTypeface(mApp.getFont());
                LinearLayout row = new LinearLayout(getActivity().getApplicationContext());
                row.setLayoutParams(paramsLayout);
                row.addView(text);
                row.setVisibility(View.VISIBLE);

                row.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        LinearLayout l = (LinearLayout) v;
                        TextView t = (TextView) l.getChildAt(0);
                        select.setText(t.getText());
                        select.setTypeface(mApp.getFontBold());
                        closeSelect(v.getRootView());
                    }
                });
                searchListRoot.addView(row);
            }
            RelativeLayout.LayoutParams  params = (RelativeLayout.LayoutParams) searchList.getLayoutParams();
            params.setMargins(0,margin,0,0);
            searchList.setLayoutParams(params);
            searchList.setVisibility(View.VISIBLE);
        }else{
            closeSelect(v);
        }



    }

    private void nextFilter(View v) {
        if(idxLayoutActual+1<LimitFilters){
            filtersLayouts[idxLayoutActual].setVisibility(View.GONE);
            filtersLayouts[idxLayoutActual+1].setVisibility(View.VISIBLE);
            idxLayoutActual++;
            nextFilter.setVisibility(idxLayoutActual<LimitFilters-1?View.VISIBLE:View.INVISIBLE);
            backFilter.setVisibility(idxLayoutActual>0?View.VISIBLE:View.INVISIBLE);
            layoutBuscar.setVisibility(idxLayoutActual==LimitFilters-1?View.VISIBLE:View.GONE);
        }
    }
    private void backFilter(View v) {
        if(idxLayoutActual>0){
            filtersLayouts[idxLayoutActual].setVisibility(View.GONE);
            filtersLayouts[idxLayoutActual-1].setVisibility(View.VISIBLE);
            idxLayoutActual--;
            nextFilter.setVisibility(idxLayoutActual<LimitFilters-1?View.VISIBLE:View.INVISIBLE);
            backFilter.setVisibility(idxLayoutActual>0?View.VISIBLE:View.INVISIBLE);
            layoutBuscar.setVisibility(idxLayoutActual==LimitFilters-1?View.VISIBLE:View.GONE);
        }
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
