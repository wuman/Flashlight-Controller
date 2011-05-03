package com.flashlight.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FlashlightController {

    private static FlashlightController sFlashController = null;

    private static final String LED_OFF = "0";
    private static final String LED_ON_DEFAULT = "125";

    private String mLedLevel;
    private boolean mPowerOn;

    private FlashlightController() {
        mPowerOn = false;
        mLedLevel = LED_ON_DEFAULT;
    }

    public static FlashlightController getInstance() {
        if ( sFlashController == null ) {
            sFlashController = new FlashlightController();
        }

        return sFlashController;
    }

    public void setLevel(String level) {
        mLedLevel = level;
    }

    public String getLevel() {
        return mLedLevel;
    }

    public boolean isPowerOn() {
        return mPowerOn;
    }

    public void setPower(boolean on) {
        mPowerOn = on;
        if ( on ) {
            setLedLevel(mLedLevel);
        } else {
            setLedLevel(LED_OFF);
        }
    }

    private void setLedLevel(String Level) {
        File file = new File("/sys/class/leds/flashlight/brightness");
        if ( file == null || !file.exists() ) {
            return;
        }

        try {
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            try {
                output.write(Level);
            } finally {
                output.close();
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

}
