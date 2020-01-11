package amsi.dei.estg.ipleiria.infortec_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.listeners.MosquittoCallBack;
import amsi.dei.estg.ipleiria.infortec_android.models.Produto;
import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.utils.ProdutoJsonParser;

public class ProdutoActivity extends AppCompatActivity {
    private int id;
    private Produto produto;
    private SingletonGestorTabelas singletonGestorTabelas;
    private TextView txtPreco, txtTitulo;

    public ProdutoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtPreco = findViewById(R.id.txtPreco);

        //produtos = SingletonGestorTabelas.getInstance(getContext()).getProdutosBD();

        final MqttAndroidClient client;

        client = new MqttAndroidClient(this, "tcp://127.0.0.1:1883", MqttClient.generateClientId());

        if (ProdutoJsonParser.isConnectionInternet(this)) {
            try {
                client.connect().setActionCallback(new IMqttActionListener() {
                    @Override
                    public void onSuccess(IMqttToken asyncActionToken) {
                        Log.i("Mosquitto", "Connected");
                        try {
                            client.subscribe("INSERT", 0, new IMqttMessageListener() {
                                @Override
                                public void messageArrived(String topic, MqttMessage message) throws Exception {
                                    //enviar cenas favoritos para INSERT

                                }
                            });
                            client.subscribe("DELETE", 0, new IMqttMessageListener() {
                                @Override
                                public void messageArrived(String topic, MqttMessage message) throws Exception {
                                    //enviar cenas favoritos para DELETE

                                }
                            });
                        } catch (MqttException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                        Log.e("Mosquitto", "Error Connecting to Mosquitto");
                    }
                });
            }catch (MqttException e){
                e.printStackTrace();
                System.out.println("==> error: " + e);
            }


                /*client.setCallback(new MosquittoCallBack());
                client.connect();
                client.subscribe("INSERT", 0);
                MqttTopic noticiasTopic = client.getTopic("INSERT");

                MqttMessage message = new MqttMessage("abc".getBytes());
                message.setQos(0);
                message.setRetained(true);

                noticiasTopic.publish(message);*/
        //}


        id = getIntent().getIntExtra("ID_PRODUTO", 0);
        produto = SingletonGestorTabelas.getInstance(getApplicationContext()).getProdutoById(id);

        txtTitulo.setText(produto.getNome());
        System.out.println("getNome:" + produto.getNome());
        System.out.println("getId:" + produto.getId());
        System.out.println("getDescricao:" + produto.getDescricao());
        System.out.println("getQuantStock:" + produto.getQuantStock());
        System.out.println("getValorDesconto:" + produto.getValorDesconto());

        txtPreco.setText(String.valueOf(produto.getPreco()));
    }
}
