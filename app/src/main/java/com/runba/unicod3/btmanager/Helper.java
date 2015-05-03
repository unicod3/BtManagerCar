package com.runba.unicod3.btmanager;

/**
 * Created by unicod3 on 4/27/15.
 */
public class Helper {
    public static final String EXTRA_BLUETOOTH_NAME = "bluetooth_name";
    public static final String EXTRA_BLUETOOTH_ADDRESS = "bluetooth_address";

    // Intent request codes
    public static final int REQUEST_CONNECT_DEVICE = 1;

    public static String rPad(String data, int size, String what){
        String tmp = "";
        if (data == null) {
            tmp = "";
        } else {
            int csize = (data.trim()).getBytes().length;

            if (csize <= size) {
                int iter = size - csize;
                for (int i = 0; i < iter; i++) {
                    tmp = tmp + what;
                }
                tmp = data.trim() + tmp;
            } else {
                tmp = (data.trim()).substring(0, size);
            }
        }
        return tmp;
    }

    public static String makeTelnoShortFormat(String telno12) throws Exception{
        String telno = "";

        if( telno12 != null){
            telno12 = telno12.trim();
            int telnoLength = telno12.length();

            if (telnoLength == 12 && telno12.substring(3, 5).equals("00")) {
                telno = telno12.substring(0, 3) + telno12.substring(5, 12);
            } else if (telnoLength == 12 && telno12.substring(3, 4).equals("0")) {
                telno = telno12.substring(0, 3) + telno12.substring(4, 12);
            } else if (telnoLength == 11 && telno12.substring(3, 4).equals("0")) {
                telno = telno12.substring(0, 3) + telno12.substring(4, 11);
            } else {
                telno = telno12;
            }
        }

        return telno;
    }

    public static String nullToEmpty(String data) {
        if (data == null) {
            return "";
        }

        return data;
    }
}