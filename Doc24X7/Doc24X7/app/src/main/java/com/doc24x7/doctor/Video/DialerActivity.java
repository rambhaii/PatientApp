package com.doc24x7.doctor.Video;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.doc24x7.doctor.R;
import com.doc24x7.doctor.Video.conventional.DialerLayout;

import io.agora.rtm.RtmChannelMember;
import io.agora.rtm.RtmClient;
import io.agora.rtm.RtmFileMessage;
import io.agora.rtm.RtmImageMessage;

public class DialerActivity extends BaseCallActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);
        initUI();
    }

    private void initUI() {
        TextView identifier = findViewById(R.id.identifier_text);
//        identifier.setText(String.format(getResources().
//                getString(R.string.about_me), config().getUserId()));
    }

    @Override
    protected void onGlobalLayoutCompleted() {
        DialerLayout dialerLayout = findViewById(R.id.dialer_layout);
        dialerLayout.setActivity(this);
        dialerLayout.adjustScreenWidth(displayMetrics.widthPixels);

        int dialerHeight = dialerLayout.getHeight();
        int dialerRemainHeight = displayMetrics.heightPixels - statusBarHeight - dialerHeight;
        RelativeLayout.LayoutParams params =
                (RelativeLayout.LayoutParams) dialerLayout.getLayoutParams();
        params.topMargin = dialerRemainHeight / 4 + statusBarHeight;
        dialerLayout.setLayoutParams(params);
    }


    @Override
    public void onImageMessageReceived(RtmImageMessage rtmImageMessage, RtmChannelMember rtmChannelMember) {

    }

    @Override
    public void onFileMessageReceived(RtmFileMessage rtmFileMessage, RtmChannelMember rtmChannelMember) {

    }

}
