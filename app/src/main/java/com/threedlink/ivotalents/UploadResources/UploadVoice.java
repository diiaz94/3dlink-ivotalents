package com.threedlink.ivotalents.uploadresources;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.threedlink.ivotalents.R;

import java.io.File;
import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UploadVoice.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadVoice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadVoice extends Fragment implements MediaPlayer.OnCompletionListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "UploadVoice";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    MediaRecorder recorder;
    MediaPlayer player;
    File file;
    @Bind(R.id.status_text)
    TextView statusText;
    @Bind(R.id.init_recording_audio_btn)
    Button initRecordingAudioBtn;
    @Bind(R.id.stop_recording_audio_btn)
    Button stopRecordingAudioBtn;
    @Bind(R.id.play_record_btn)
    Button playRecordBtn;
    private OnFragmentInteractionListener mListener;

    public UploadVoice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadVoice.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadVoice newInstance(String param1, String param2) {
        UploadVoice fragment = new UploadVoice();
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
        View v = inflater.inflate(R.layout.fragment_upload_voice, container, false);
        ButterKnife.bind(this, v);


        return v;
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
    public void onCompletion(MediaPlayer mediaPlayer) {

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

/*
    @OnTouch(R.id.init_recording_audio_btn)
    public void releaseRecording(View v, MotionEvent ev) {
        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // PRESSED

            case MotionEvent.ACTION_UP:
                // RELEASED

        }

    }*/

    @OnClick(R.id.init_recording_audio_btn)
    public void initRecording(View v) {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File path = new File(Environment.getExternalStorageDirectory()
                .getPath());
        try {
            file = File.createTempFile("temporal", ".3gp", path);
        } catch (IOException e) {
        }
        recorder.setOutputFile(file.getAbsolutePath());
        try {
            recorder.prepare();
        } catch (IOException e) {
        }
        recorder.start();
        statusText.setText("Grabando");
        initRecordingAudioBtn.setEnabled(false);
        stopRecordingAudioBtn.setEnabled(true);
        playRecordBtn.setEnabled(false);
    }

    @OnClick(R.id.stop_recording_audio_btn)
    public void stopRecording(View v) {
        recorder.stop();
        recorder.release();
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        try {
            player.setDataSource(file.getAbsolutePath());
        } catch (IOException e) {
            Log.e(TAG,e.getMessage());
        }
        try {
            player.prepare();
        } catch (IOException e) {
            Log.e(TAG,e.getMessage());
        }
        statusText.setText("Listo para reproducir");
        initRecordingAudioBtn.setEnabled(true);
        stopRecordingAudioBtn.setEnabled(false);
        playRecordBtn.setEnabled(true);
    }
    @OnClick(R.id.play_record_btn)
    public void play(View v) {
        player.start();

    }

}
