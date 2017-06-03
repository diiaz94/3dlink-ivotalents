package com.threedlink.ivotalents.uploadresources;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.threedlink.ivotalents.R;
import com.threedlink.ivotalents.utils.Util;

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



    @Bind(R.id.equalizer)
    ImageView equalizer;


    @Bind(R.id.status_text)
    TextView statusText;
    @Bind(R.id.init_recording_audio_btn)
    Button initRecordingAudioBtn;
    @Bind(R.id.stop_recording_audio_btn)
    Button stopRecordingAudioBtn;


    @Bind(R.id.record_reproductor)
    RelativeLayout recordReproductor;

    @Bind(R.id.container_filled_reproduced)
    LinearLayout containerFilledReproduced;
    @Bind(R.id.filled_reproduced)
    LinearLayout filledReproduced;

    @Bind(R.id.play_record_btn)
    Button playRecordBtn;
    @Bind(R.id.pause_record_btn)
    Button pauseRecordBtn;


    @Bind(R.id.record_duration)
    TextView recordDuration;

    CountDownTimer mCountDownTimer;


    private OnFragmentInteractionListener mListener;
    private AnimationDrawable mFrameAnimation;

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
        recordReproductor.setVisibility(View.GONE);
        equalizer.setBackgroundResource(R.drawable.equalizer_gif);
        mFrameAnimation = (AnimationDrawable) equalizer.getBackground();

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

    public void setWidthFilledReproduced(int widthFilledReproduced) {
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(widthFilledReproduced,ViewGroup.LayoutParams.MATCH_PARENT);
        filledReproduced.setLayoutParams(param);
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
            recorder.setOutputFile(file.getAbsolutePath());
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {}

        statusText.setText("Grabando");
        initRecordingAudioBtn.setVisibility(View.GONE);
        stopRecordingAudioBtn.setVisibility(View.VISIBLE);
        recordReproductor.setVisibility(View.GONE);
        equalizer.setVisibility(View.GONE);
    }

    @OnClick(R.id.stop_recording_audio_btn)
    public void stopRecording(View v) {
        recorder.stop();
        recorder.release();
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        try {
            player.setDataSource(file.getAbsolutePath());
            player.prepare();
        } catch (IOException e) {
            Log.e(TAG,e.getMessage());
        }
        statusText.setText("Listo para reproducir");
        initRecordingAudioBtn.setVisibility(View.VISIBLE);
        stopRecordingAudioBtn.setVisibility(View.GONE);
        recordReproductor.setVisibility(View.VISIBLE);
        equalizer.setVisibility(View.VISIBLE);
        mFrameAnimation.stop();
        playRecordBtn.setVisibility(View.VISIBLE);
        pauseRecordBtn.setVisibility(View.GONE);

        recordDuration.setText(Util.formatDuration(player.getDuration()));


    }

    @OnClick(R.id.play_record_btn)
    public void play(View v) {
        player.start();
        //Toast.makeText(getActivity(), "duration: " + (player.getDuration()-player.getCurrentPosition()), Toast.LENGTH_LONG).show();

        initRecordingAudioBtn.setEnabled(false);
        mCountDownTimer = new CountDownTimer(player.getDuration()-player.getCurrentPosition(), 1000) {

            public void onTick(long millisUntilFinished) {
                long totalDuration = player.getDuration();
                long currentDuration = totalDuration - millisUntilFinished;
                long totalWidth = containerFilledReproduced.getWidth();
                int calculateWidth = (int) (currentDuration * totalWidth / totalDuration);
                recordDuration.setText(Util.formatDuration((int) (totalDuration-currentDuration)));
                setWidthFilledReproduced(calculateWidth);


            }

            public void onFinish() {
                setWidthFilledReproduced(containerFilledReproduced.getWidth());
                recordDuration.setText("00:00");
                initRecordingAudioBtn.setEnabled(true);

            }

        };
        mCountDownTimer.start();
        mFrameAnimation.start();

        //Toast.makeText(getActivity(), "duration: " + player.getCurrentPosition(), Toast.LENGTH_LONG).show();
        playRecordBtn.setVisibility(View.GONE);
        pauseRecordBtn.setVisibility(View.VISIBLE);
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                playRecordBtn.setVisibility(View.VISIBLE);
                pauseRecordBtn.setVisibility(View.GONE);
                setWidthFilledReproduced(0);
                recordDuration.setText(Util.formatDuration(player.getDuration()));
                mFrameAnimation.stop();
            }

        });

    }
    @OnClick(R.id.pause_record_btn)
    public void pause(View v) {
        player.pause();
        mCountDownTimer.cancel();
        mFrameAnimation.stop();
        playRecordBtn.setVisibility(View.VISIBLE);
        initRecordingAudioBtn.setEnabled(true);
        pauseRecordBtn.setVisibility(View.GONE);


    }

}
