package com.example.dadn.utils.mqtt;

import android.content.Context;
import android.util.Log;

import com.example.dadn.utils.Constants;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.DisconnectedBufferOptions;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MqttService {
    public MqttAndroidClient mqttAndroidClient3;
    public MqttAndroidClient mqttAndroidClient1;

    final String serverUri = "tcp://io.adafruit.com:1883";

    final String clientId2 = "dnt";
    final String clientId1 = "QIUIQoqqw";

    final String TAG = "MqttAndroidClient TAG";

    public MqttService(Context context){
        mqttAndroidClient3 = new MqttAndroidClient(context, serverUri, clientId2);
        mqttAndroidClient3.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.w("mqtt", s);
            }

            @Override
            public void connectionLost(Throwable throwable) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
                Log.w(TAG, mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

            }
        });
        connect();
    }

    public MqttService(Context context, MqttCallbackExtended callbackExtended) {
        mqttAndroidClient3 = new MqttAndroidClient(context, serverUri, clientId2);
        mqttAndroidClient3.setCallback(callbackExtended);
        mqttAndroidClient1 = new MqttAndroidClient(context, serverUri, clientId1);
        mqttAndroidClient1.setCallback(callbackExtended);
        connect();
    }

    public MqttService(Context context, MqttCallbackExtended callbackExtended, String[] topics) {
        this.mqttAndroidClient3 = new MqttAndroidClient(context, serverUri, clientId2);
        this.mqttAndroidClient3.setCallback(callbackExtended);
        this.mqttAndroidClient1 = new MqttAndroidClient(context, serverUri, clientId1);
        this.mqttAndroidClient1.setCallback(callbackExtended);
        connect(topics);
    }

    public MqttService(Context context, MqttCallbackExtended callbackExtended, boolean isSubscribe) {
        if (!isSubscribe) {
            mqttAndroidClient3 = new MqttAndroidClient(context, serverUri, clientId2);
            mqttAndroidClient3.setCallback(callbackExtended);
            mqttAndroidClient1 = new MqttAndroidClient(context, serverUri, clientId1);
            mqttAndroidClient1.setCallback(callbackExtended);
            connectWithoutSubscribe();
        }
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient3.setCallback(callback);
    }

    public void connectWithoutSubscribe() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);

        mqttConnectOptions.setUserName(Constants.USERNAME);
        mqttConnectOptions.setPassword(Constants.PASSWORD.toCharArray());

        if (!mqttAndroidClient3.isConnected()) {
            try {
                mqttAndroidClient3.connect(mqttConnectOptions, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w(TAG, "Connect to: " + serverUri + " successful");
                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                        disconnectedBufferOptions.setBufferEnabled(true);
                        disconnectedBufferOptions.setBufferSize(100);
                        disconnectedBufferOptions.setPersistBuffer(false);
                        disconnectedBufferOptions.setDeleteOldestMessages(false);
                        mqttAndroidClient3.setBufferOpts(disconnectedBufferOptions);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w(TAG, "Failed to connect to: " + serverUri + ": " + exception.getMessage()
                                        + "/ " + Constants.USERNAME);
                    }
                });
            } catch (MqttException ex) {
                ex.printStackTrace();
            }
        }

        MqttConnectOptions mqttConnectOptions1 = new MqttConnectOptions();
        mqttConnectOptions1.setAutomaticReconnect(true);
        mqttConnectOptions1.setCleanSession(true);

        mqttConnectOptions1.setUserName(Constants.USERNAME1);
        mqttConnectOptions1.setPassword(Constants.PASSWORD1.toCharArray());

        if (!mqttAndroidClient1.isConnected()) {
            try {
                mqttAndroidClient1.connect(mqttConnectOptions1, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w(TAG, "Connect to: " + serverUri + " successful");
                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                        disconnectedBufferOptions.setBufferEnabled(true);
                        disconnectedBufferOptions.setBufferSize(100);
                        disconnectedBufferOptions.setPersistBuffer(false);
                        disconnectedBufferOptions.setDeleteOldestMessages(false);
                        mqttAndroidClient1.setBufferOpts(disconnectedBufferOptions);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w(TAG, "Failed to connect to: " + serverUri + ": " + exception.getMessage() + "/ " + Constants.USERNAME1);
                    }
                });
            } catch (MqttException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void connect() {
        MqttConnectOptions mqttConnectOptions10 = new MqttConnectOptions();
        mqttConnectOptions10.setAutomaticReconnect(false);
        mqttConnectOptions10.setCleanSession(true);

        mqttConnectOptions10.setUserName(Constants.USERNAME);
        mqttConnectOptions10.setPassword(Constants.PASSWORD.toCharArray());

        if (!mqttAndroidClient3.isConnected()) {
            try {
                mqttAndroidClient3.connect(mqttConnectOptions10, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w(TAG, "Connect to: " + serverUri + " successful");
                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                        disconnectedBufferOptions.setBufferEnabled(true);
                        disconnectedBufferOptions.setBufferSize(100);
                        disconnectedBufferOptions.setPersistBuffer(false);
                        disconnectedBufferOptions.setDeleteOldestMessages(false);
                        mqttAndroidClient3.setBufferOpts(disconnectedBufferOptions);
                        subscribeToTopic();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w(TAG, "Failed to connect to: " + serverUri + ": " + exception.getMessage() + "/ " + Constants.USERNAME);
                    }
                });


            } catch (MqttException ex) {
                ex.printStackTrace();
            }
        }
        MqttConnectOptions mqttConnectOptions1 = new MqttConnectOptions();
        mqttConnectOptions1.setAutomaticReconnect(true);
        mqttConnectOptions1.setCleanSession(true);

        mqttConnectOptions1.setUserName(Constants.USERNAME1);
        mqttConnectOptions1.setPassword(Constants.PASSWORD1.toCharArray());
        if (!mqttAndroidClient1.isConnected()) {
            try {
                mqttAndroidClient1.connect(mqttConnectOptions1, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w(TAG, "Connect to: " + serverUri + " successful");
                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                        disconnectedBufferOptions.setBufferEnabled(true);
                        disconnectedBufferOptions.setBufferSize(100);
                        disconnectedBufferOptions.setPersistBuffer(false);
                        disconnectedBufferOptions.setDeleteOldestMessages(false);
                        mqttAndroidClient1.setBufferOpts(disconnectedBufferOptions);
                        subscribeToTopic();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w(TAG, "Failed to connect to: " + serverUri + ": " + exception.getMessage() + "/ " + Constants.USERNAME1);
                    }
                });
            } catch (MqttException ex) {
                ex.printStackTrace();
            }
        }

    }


    public void connect(String[] topics){
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);

        mqttConnectOptions.setUserName(Constants.USERNAME);
        mqttConnectOptions.setPassword(Constants.PASSWORD.toCharArray());


        MqttConnectOptions mqttConnectOptions1 = new MqttConnectOptions();
        mqttConnectOptions1.setAutomaticReconnect(true);
        mqttConnectOptions1.setCleanSession(true);

        mqttConnectOptions1.setUserName(Constants.USERNAME1);
        mqttConnectOptions1.setPassword(Constants.PASSWORD1.toCharArray());

        if (!mqttAndroidClient3.isConnected()) {
            try {
                mqttAndroidClient3.connect(mqttConnectOptions, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w(TAG, "Connect to: " + serverUri + " successful");
                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                        disconnectedBufferOptions.setBufferEnabled(true);
                        disconnectedBufferOptions.setBufferSize(100);
                        disconnectedBufferOptions.setPersistBuffer(false);
                        disconnectedBufferOptions.setDeleteOldestMessages(false);
                        mqttAndroidClient3.setBufferOpts(disconnectedBufferOptions);
                        subscribeToTopic(topics);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w(TAG, "Failed to connect to: " + serverUri + ": " + exception.getMessage() + "/ " + Constants.USERNAME);
                    }
                });


            } catch (MqttException ex){
                ex.printStackTrace();
            }
        }

        if (!mqttAndroidClient1.isConnected()) {
            try {

                mqttAndroidClient1.connect(mqttConnectOptions1, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w(TAG, "Connect to: " + serverUri + " successful");
                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                        disconnectedBufferOptions.setBufferEnabled(true);
                        disconnectedBufferOptions.setBufferSize(100);
                        disconnectedBufferOptions.setPersistBuffer(false);
                        disconnectedBufferOptions.setDeleteOldestMessages(false);
                        mqttAndroidClient1.setBufferOpts(disconnectedBufferOptions);
                        subscribeToTopic(topics);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w(TAG, "Failed to connect to: " + serverUri + ": " + exception.getMessage() + "/ " + Constants.USERNAME1);
                    }
                });


            } catch (MqttException ex){
                ex.printStackTrace();
            }
        }
    }

    private void subscribeToTopic(String[] device_topics) {
        Log.d(TAG, "subscribeToTopic with parameter: " + device_topics);
        try {
            for (String topic : device_topics) {
                if (topic.split("/")[0].equals(Constants.USERNAME)) {
                    if (mqttAndroidClient3.isConnected()) {
                        mqttAndroidClient3.subscribe(topic, 0, null, new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.w(TAG,"Subscribed / " + topic);
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Log.w("Mqtt", "Subscribed fail!" + exception + " / " + topic);
                            }
                        });
                    }
                }
                else {
                    if (mqttAndroidClient1 != null && mqttAndroidClient1.isConnected()) {
                        mqttAndroidClient1.subscribe(topic, 0, null, new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.w(TAG,"Subscribed / " + topic);
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Log.w("Mqtt", "Subscribed fail!" + exception + " / " + topic);
                            }
                        });
                    }
                }
                // get retained message from Adafruit IO
                publishDummyMessageToTopic(topic);
            }

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }


    private void subscribeToTopic() {
        Log.d(TAG, "subscribeToTopic with no parameter: ");
        try {
            for (String topic : Constants.TOPICS) {
                if (topic.split("/")[0].equals(Constants.USERNAME)) {
                    if (mqttAndroidClient3 != null && mqttAndroidClient3.isConnected()) {
                        mqttAndroidClient3.subscribe(topic, 0, null, new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.w(TAG,"Subscribed / " + topic);
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Log.w(TAG, "Subscribed fail!" + exception);
                            }
                        });
                    }
                }
                else {
                    if (mqttAndroidClient1 != null && mqttAndroidClient1.isConnected()) {
                        mqttAndroidClient1.subscribe(topic, 0, null, new IMqttActionListener() {
                            @Override
                            public void onSuccess(IMqttToken asyncActionToken) {
                                Log.w(TAG,"Subscribed / " + topic);
                            }

                            @Override
                            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                                Log.w(TAG, "Subscribed fail!" + exception + " : " + topic);
                            }
                        });
                    }
                }
                // get retained message from Adafruit IO
                publishDummyMessageToTopic(topic);
            }

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    private void publishDummyMessageToTopic(String topic) throws MqttException {
        if (topic.split("/")[0].equals(Constants.USERNAME)) {
            if (mqttAndroidClient3 != null && mqttAndroidClient3.isConnected()) {
                mqttAndroidClient3.publish(topic + "/get", new MqttMessage());
            }
        }
        else {
            if (mqttAndroidClient1 != null && mqttAndroidClient1.isConnected()) {
                mqttAndroidClient1.publish(topic + "/get", new MqttMessage());
            }
        }
    }
}
