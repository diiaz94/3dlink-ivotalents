package com.threedlink.ivotalents.viewholders;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.threedlink.ivotalents.R;

/**
 * Created by diiaz94 on 02-06-2017.
 */
public class AudiosViewHolder {
    RelativeLayout recordReproductor;
    Button playRecordBtn;
    Button pauseRecordBtn;
    TextView txtName;
    TextView txtDuration;

    public AudiosViewHolder(View v) {
        this.recordReproductor = (RelativeLayout) v.findViewById(R.id.record_reproductor);
        this.playRecordBtn = (Button) v.findViewById(R.id.play_record_btn);
        this.pauseRecordBtn = (Button) v.findViewById(R.id.pause_record_btn);
        this.txtName = (TextView) v.findViewById(R.id.record_name);
        this.txtDuration = (TextView) v.findViewById(R.id.record_duration);

    }

    public RelativeLayout getRecordReproductor() {
        return recordReproductor;
    }

    public Button getPlayRecordBtn() {
        return playRecordBtn;
    }

    public Button getPauseRecordBtn() {
        return pauseRecordBtn;
    }

    public TextView getTxtDuration() {
        return txtDuration;
    }

    public TextView getTxtName() {
        return txtName;
    }
}
