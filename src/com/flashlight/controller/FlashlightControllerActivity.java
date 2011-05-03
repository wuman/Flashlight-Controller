package com.flashlight.controller;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FlashlightControllerActivity extends Activity {

    private Handler mHandler;
    private FlashlightController mController;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private Button mButton4;
    private EditText mLevel;
    private Button mLevelButton;

    private static final int MSG_FLASH = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if ( msg.what == MSG_FLASH ) {
                    handleFlash(msg.arg1, msg.arg2 == 1);
                }
            }

        };
        mController = FlashlightController.getInstance();
        mController.setPower(false);

        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);
        mButton4 = (Button) findViewById(R.id.button4);
        mLevel = (EditText) findViewById(R.id.level);
        mLevelButton = (Button) findViewById(R.id.levelButton);

        mLevel.setText(mController.getLevel());
        mLevelButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                mController.setLevel(mLevel.getText()
                    .toString());
            }

        });
    }

    @Override
    protected void onPause() {
        mHandler.removeMessages(MSG_FLASH);
        mController.setPower(false);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void handleFlash(int duration, boolean on) {
        mController.setPower(on);
        Message msg = mHandler.obtainMessage(MSG_FLASH, duration, on ? 0 : 1);
        mHandler.sendMessageDelayed(msg, duration / 2);
    }

    private void setButtonsEnabled(boolean b1, boolean b2, boolean b3,
        boolean b4) {
        mButton1.setEnabled(b1);
        mButton2.setEnabled(b2);
        mButton3.setEnabled(b3);
        mButton4.setEnabled(b4);
    }

    public void onClickHandler(View view) {
        mHandler.removeMessages(MSG_FLASH);
        switch ( view.getId() ) {
        case R.id.button1: {
            mController.setPower(false);
            setButtonsEnabled(false, true, true, true);
            break;
        }
        case R.id.button2: {
            setButtonsEnabled(true, false, true, true);
            mHandler.obtainMessage(MSG_FLASH, 1000, 1)
                .sendToTarget();
            break;
        }
        case R.id.button3: {
            setButtonsEnabled(true, true, false, true);
            mHandler.obtainMessage(MSG_FLASH, 1000 / 3, 1)
                .sendToTarget();
            break;
        }
        case R.id.button4: {
            setButtonsEnabled(true, true, true, false);
            mHandler.obtainMessage(MSG_FLASH, 1000 / 5, 1)
                .sendToTarget();
            break;
        }
        }
    }

}