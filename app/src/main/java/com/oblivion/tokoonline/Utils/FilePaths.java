package com.oblivion.tokoonline.Utils;

import android.os.Environment;

public class FilePaths {

    private String ROOT_DIR = Environment.getExternalStorageDirectory().getPath();

    public String PICTURES = ROOT_DIR + "/Pictures";
    public String FOLDER_PICTURES = ROOT_DIR + "/Pictures";
    public String CAMERA = ROOT_DIR + "/DCIM/Camera";
    public String WA = ROOT_DIR + "/WhatsApp/Media/WhatsApp Images";
    public String Screenshoot = ROOT_DIR + "/DCIM/Screenshots";
    public String Bluetooth = ROOT_DIR + "/bluetooth";
}
