package amsi.dei.estg.ipleiria.infortec_android.listeners;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MosquittoCallBack implements MqttCallback {
    @Override
    public void connectionLost(Throwable throwable) {
        System.out.println("Connection Lost to Mosquitto");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message Received:\n\t"+ new String(mqttMessage.getPayload()) + "topico:"+s);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }
}
