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
    public MqttAndroidClient mqttAndroidClient;

    final String serverUri = "tcp://io.adafruit.com:1883";

    final String clientId = "ExampleAndroidClient";

    final String TAG = "MqttAndroidClient TAG";

    public MqttService(Context context){
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(new MqttCallbackExtended() {
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
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(callbackExtended);
        connect();
    }

    public MqttService(Context context, MqttCallbackExtended callbackExtended, String[] topics) {
        mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
        mqttAndroidClient.setCallback(callbackExtended);
        connect(topics);
    }

    public MqttService(Context context, MqttCallbackExtended callbackExtended, boolean isSubscribe) {
        if (!isSubscribe) {
            mqttAndroidClient = new MqttAndroidClient(context, serverUri, clientId);
            mqttAndroidClient.setCallback(callbackExtended);
            connectWithoutSubscribe();
        }
    }

    public void setCallback(MqttCallbackExtended callback) {
        mqttAndroidClient.setCallback(callback);
    }

    public void connectWithoutSubscribe() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);

        mqttConnectOptions.setUserName(Constants.USERNAME);
        mqttConnectOptions.setPassword(Constants.PASSWORD.toCharArray());
        if (!mqttAndroidClient.isConnected()) {
            try {
                mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w(TAG, "Connect to: " + serverUri + " successful");
                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                        disconnectedBufferOptions.setBufferEnabled(true);
                        disconnectedBufferOptions.setBufferSize(100);
                        disconnectedBufferOptions.setPersistBuffer(false);
                        disconnectedBufferOptions.setDeleteOldestMessages(false);
                        mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w(TAG, "Failed to connect to: " + serverUri + ": " + exception.getMessage());
                    }
                });


            } catch (MqttException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void connect() {
        MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
        mqttConnectOptions.setAutomaticReconnect(true);
        mqttConnectOptions.setCleanSession(true);

        mqttConnectOptions.setUserName(Constants.USERNAME);
        mqttConnectOptions.setPassword(Constants.PASSWORD.toCharArray());
        if (!mqttAndroidClient.isConnected()) {
            try {
                mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w(TAG, "Connect to: " + serverUri + " successful");
                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                        disconnectedBufferOptions.setBufferEnabled(true);
                        disconnectedBufferOptions.setBufferSize(100);
                        disconnectedBufferOptions.setPersistBuffer(false);
                        disconnectedBufferOptions.setDeleteOldestMessages(false);
                        mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                        subscribeToTopic();
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w(TAG, "Failed to connect to: " + serverUri + ": " + exception.getMessage());
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
        if (!mqttAndroidClient.isConnected()) {
            try {

                mqttAndroidClient.connect(mqttConnectOptions, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w(TAG, "Connect to: " + serverUri + " successful");
                        DisconnectedBufferOptions disconnectedBufferOptions = new DisconnectedBufferOptions();
                        disconnectedBufferOptions.setBufferEnabled(true);
                        disconnectedBufferOptions.setBufferSize(100);
                        disconnectedBufferOptions.setPersistBuffer(false);
                        disconnectedBufferOptions.setDeleteOldestMessages(false);
                        mqttAndroidClient.setBufferOpts(disconnectedBufferOptions);
                        subscribeToTopic(topics);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w(TAG, "Failed to connect to: " + serverUri + ": " + exception.getMessage());
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
                mqttAndroidClient.subscribe(topic, 0, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w(TAG,"Subscribed / " + topic);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w("Mqtt", "Subscribed fail!" + exception.toString());
                    }
                });
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
                mqttAndroidClient.subscribe(topic, 0, null, new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.w(TAG,"Subscribed / " + topic);
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.w(TAG, "Subscribed fail!" + exception.toString());
                    }
                });
                // get retained message from Adafruit IO
                publishDummyMessageToTopic(topic);
            }

        } catch (MqttException ex) {
            System.err.println("Exception whilst subscribing");
            ex.printStackTrace();
        }
    }

    private void publishDummyMessageToTopic(String topic) throws MqttException {
        mqttAndroidClient.publish(topic + "/get", new MqttMessage());
    }
}
