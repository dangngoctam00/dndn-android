package com.example.dadn.utils;

public class Constants {
    public static final String BASE_URL = "http://192.168.1.16:3001/";
    public static final String TOKEN = "token";
    public static final String EMAIL = "email";
    public static final String USERNAME = "dadn201";
    public static final String PASSWORD = "aio_OTtq25sIyD1ME6A2wvhpxrkdZLPk";
    public static final String[] TOPICS = new String[] {USERNAME + "/feeds/bk-iot-soil",
            USERNAME + "/feeds/bk-iot-light",
            USERNAME + "/feeds/bk-iot-temp-humid",
            USERNAME + "/feeds/bk-iot-relay",
            USERNAME + "/feeds/bk-iot-led"};
    public static final String[] CONSTRAINT_TOPICS = new String[] {
            USERNAME + "/feeds/bk-iot-soil",
            USERNAME + "/feeds/bk-iot-light",
            USERNAME + "/feeds/bk-iot-temp-humid"
    };
    public static final String[] DEVICE_TOPICS = new String[] {
            USERNAME + "/feeds/bk-iot-relay",
            USERNAME + "/feeds/bk-iot-led"
    };
    public static final String LIMIT = "1";
    //username: dadn201
    //password: 123456
}
