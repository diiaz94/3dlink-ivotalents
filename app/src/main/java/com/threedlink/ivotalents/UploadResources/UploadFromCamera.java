package com.threedlink.ivotalents.UploadResources;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.camerafragment.CameraFragment;
import com.github.florent37.camerafragment.PreviewActivity;
import com.github.florent37.camerafragment.configuration.Configuration;
import com.github.florent37.camerafragment.CameraFragmentApi;
import com.github.florent37.camerafragment.listeners.CameraFragmentControlsListener;
import com.github.florent37.camerafragment.listeners.CameraFragmentResultListener;
import com.github.florent37.camerafragment.listeners.CameraFragmentStateListener;
import com.github.florent37.camerafragment.listeners.CameraFragmentVideoRecordTextListener;
import com.github.florent37.camerafragment.widgets.CameraSettingsView;
import com.github.florent37.camerafragment.widgets.CameraSwitchView;
import com.github.florent37.camerafragment.widgets.FlashSwitchView;
import com.github.florent37.camerafragment.widgets.MediaActionSwitchView;
import com.github.florent37.camerafragment.widgets.RecordButton;



import com.threedlink.ivotalents.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UploadFromCamera.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadFromCamera#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadFromCamera extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int REQUEST_PREVIEW_CODE = 1001;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Bind(R.id.settings_view)
    CameraSettingsView settingsView;
    @Bind(R.id.flash_switch_view)
    FlashSwitchView flashSwitchView;
    @Bind(R.id.front_back_camera_switcher)
    CameraSwitchView cameraSwitchView;
    @Bind(R.id.record_button)
    RecordButton recordButton;
    @Bind(R.id.photo_video_camera_switcher)
    MediaActionSwitchView mediaActionSwitchView;

    @Bind(R.id.record_duration_text)
    TextView recordDurationText;
    @Bind(R.id.record_size_mb_text)
    TextView recordSizeText;

    @Bind(R.id.cameraLayout)
    View cameraLayout;

    private OnFragmentInteractionListener mListener;

    public UploadFromCamera() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadFromCamera.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadFromCamera newInstance(String param1, String param2) {
        UploadFromCamera fragment = new UploadFromCamera();
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
        View v =  inflater.inflate(R.layout.fragment_upload_from_camera, container, false);

        ButterKnife.bind(this, v);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            cameraLayout.setVisibility(View.VISIBLE);
            final CameraFragment cameraFragment = CameraFragment.newInstance(new Configuration.Builder()
                    .setCamera(Configuration.CAMERA_FACE_REAR).build());
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(v.findViewById(R.id.content).getId(), cameraFragment, this.getClass().getSimpleName())
                    .commitAllowingStateLoss();

            if(cameraFragment!=null){
                cameraFragment.setStateListener(new CameraFragmentStateListener() {

                    @Override
                    public void onCurrentCameraBack() {
                        cameraSwitchView.displayBackCamera();
                    }

                    @Override
                    public void onCurrentCameraFront() {
                        cameraSwitchView.displayFrontCamera();
                    }

                    @Override
                    public void onFlashAuto() {
                        flashSwitchView.displayFlashAuto();
                    }

                    @Override
                    public void onFlashOn() {
                        flashSwitchView.displayFlashOn();
                    }

                    @Override
                    public void onFlashOff() {
                        flashSwitchView.displayFlashOff();
                    }

                    @Override
                    public void onCameraSetupForPhoto() {
                        mediaActionSwitchView.displayActionWillSwitchVideo();

                        recordButton.displayPhotoState();
                        flashSwitchView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onCameraSetupForVideo() {
                        mediaActionSwitchView.displayActionWillSwitchPhoto();

                        recordButton.displayVideoRecordStateReady();
                        flashSwitchView.setVisibility(View.GONE);
                    }

                    @Override
                    public void shouldRotateControls(int degrees) {
                        ViewCompat.setRotation(cameraSwitchView, degrees);
                        ViewCompat.setRotation(mediaActionSwitchView, degrees);
                        ViewCompat.setRotation(flashSwitchView, degrees);
                        ViewCompat.setRotation(recordDurationText, degrees);
                        ViewCompat.setRotation(recordSizeText, degrees);
                    }

                    @Override
                    public void onRecordStateVideoReadyForRecord() {
                        recordButton.displayVideoRecordStateReady();
                    }

                    @Override
                    public void onRecordStateVideoInProgress() {
                        recordButton.displayVideoRecordStateInProgress();
                    }

                    @Override
                    public void onRecordStatePhoto() {
                        recordButton.displayPhotoState();
                    }

                    @Override
                    public void onStopVideoRecord() {
                        recordSizeText.setVisibility(View.GONE);
                        //cameraSwitchView.setVisibility(View.VISIBLE);
                        settingsView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onStartVideoRecord(File outputFile) {
                    }
                });

                cameraFragment.setControlsListener(new CameraFragmentControlsListener() {
                    @Override
                    public void lockControls() {
                        cameraSwitchView.setEnabled(false);
                        recordButton.setEnabled(false);
                        settingsView.setEnabled(false);
                        flashSwitchView.setEnabled(false);
                    }

                    @Override
                    public void unLockControls() {
                        cameraSwitchView.setEnabled(true);
                        recordButton.setEnabled(true);
                        settingsView.setEnabled(true);
                        flashSwitchView.setEnabled(true);
                    }

                    @Override
                    public void allowCameraSwitching(boolean allow) {
                        cameraSwitchView.setVisibility(allow ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void allowRecord(boolean allow) {
                        recordButton.setEnabled(allow);
                    }

                    @Override
                    public void setMediaActionSwitchVisible(boolean visible) {
                        mediaActionSwitchView.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
                    }
                });

                cameraFragment.setTextListener(new CameraFragmentVideoRecordTextListener() {
                    @Override
                    public void setRecordSizeText(long size, String text) {
                        recordSizeText.setText(text);
                    }

                    @Override
                    public void setRecordSizeTextVisible(boolean visible) {
                        recordSizeText.setVisibility(visible ? View.VISIBLE : View.GONE);
                    }

                    @Override
                    public void setRecordDurationText(String text) {
                        recordDurationText.setText(text);
                    }

                    @Override
                    public void setRecordDurationTextVisible(boolean visible) {
                        recordDurationText.setVisibility(visible ? View.VISIBLE : View.GONE);
                    }
                });
            }
        }
        return v;
    }

    @OnClick(R.id.flash_switch_view)
    public void onFlashSwitcClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.toggleFlashMode();
        }
    }

    @OnClick(R.id.front_back_camera_switcher)
    public void onSwitchCameraClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.switchCameraTypeFrontBack();
        }
    }

    @OnClick(R.id.record_button)
    public void onRecordButtonClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.takePhotoOrCaptureVideo(new CameraFragmentResultListener() {
                                                       @Override
                                                       public void onVideoRecorded(String filePath) {

                                                           Toast.makeText(getActivity().getBaseContext(), "onVideoRecorded " + filePath, Toast.LENGTH_SHORT).show();
                                                           Intent intent = PreviewActivity.newIntentVideo(getActivity(), filePath);
                                                            startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                                                       }

                                                       @Override
                                                       public void onPhotoTaken(byte[] bytes, String filePath) {
                                                           Toast.makeText(getActivity().getBaseContext(), "onPhotoTaken " + filePath, Toast.LENGTH_SHORT).show();
                                                           Intent intent = PreviewActivity.newIntentPhoto(getActivity(), filePath);
                                                            startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                                                       }
                                                   },
                    Environment.getExternalStorageDirectory().toString(),
                    "photo0");
        }
    }

    @OnClick(R.id.settings_view)
    public void onSettingsClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.openSettingDialog();
        }
    }

    @OnClick(R.id.photo_video_camera_switcher)
    public void onMediaActionSwitchClicked() {
        final CameraFragmentApi cameraFragment = getCameraFragment();
        if (cameraFragment != null) {
            cameraFragment.switchActionPhotoVideo();
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
     * <p/>
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
