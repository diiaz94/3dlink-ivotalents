package com.threedlink.ivotalents.viewholders;

import android.view.View;
import android.widget.TextView;

import com.threedlink.ivotalents.R;

/**
 * Created by vp50343 on 13/02/2017.
 */
public class MessageViewChatHolder {

    TextView txtMessage;

    public TextView getTxtMessage() {
        return txtMessage;
    }

    public void setTxtMessage(TextView txtMessage) {
        this.txtMessage = txtMessage;
    }

    public TextView getTxtDate() {
        return txtDate;
    }

    public void setTxtDate(TextView txtDate) {
        this.txtDate = txtDate;
    }

    TextView txtDate;

    public MessageViewChatHolder(View v) {
        this.txtMessage = (TextView) v.findViewById(R.id.message_chat_item_text);
        this.txtDate = (TextView) v.findViewById(R.id.message_chat_item_date);
    }
}
