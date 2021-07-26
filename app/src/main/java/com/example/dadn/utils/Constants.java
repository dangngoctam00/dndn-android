package com.example.dadn.utils;

public class Constants {
    public static final String BASE_URL = "http://192.168.1.10:3001/";
    public static final String TOKEN = "token";
    public static final String EMAIL = "email";
//    public static final String USERNAME = "dadn201";
    public static final String USERNAME = "dnt00";
    public static final String PASSWORD = "aio_ALzQ68XDjPTHvKSSBM4o6czsJ1PH";
    public static final String USERNAME1 = "dadn201";
    public static final String PASSWORD1 = "aio_OTtq25sIyD1ME6A2wvhpxrkdZLPk";
    public static final String[] TOPICS = new String[] {
            USERNAME + "/feeds/bk-iot-soil",
            USERNAME1 + "/feeds/bk-iot-light",
            USERNAME + "/feeds/bk-iot-temp-humid",
            USERNAME1 + "/feeds/bk-iot-relay",
            USERNAME + "/feeds/bk-iot-led"};

//    public static final String[] TOPICS = new String[] {
//            USERNAME + "/feeds/bk-iot-soil",
//            USERNAME + "/feeds/bk-iot-temp-humid"};

    public static final String[] TOPICS_DEVICES = new String[] {
            USERNAME + "/feeds/bk-iot-led"
    };


    public static final String[] TOPICS1 = new String[]{
            USERNAME1 + "/feeds/bk-iot-light"
    };
    public static final String[] TOPICS1_DEVICES = new String[]{
            USERNAME1 + "/feeds/bk-iot-relay"
    };
    public static final String[] CONSTRAINT_TOPICS = new String[] {
            USERNAME + "/feeds/bk-iot-soil",
            USERNAME1 + "/feeds/bk-iot-light",
            USERNAME + "/feeds/bk-iot-temp-humid"
    };
    public static final String[] DEVICE_TOPICS = new String[] {
            USERNAME1 + "/feeds/bk-iot-relay",
            USERNAME + "/feeds/bk-iot-led"
    };
    public static final String LIMIT = "1";
    //username: dadn201
    //password: 123456
}
