
package amsi.dei.estg.ipleiria.infortec_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.json.JSONException;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.listeners.MosquittoCallBack;
import amsi.dei.estg.ipleiria.infortec_android.models.Favorito;
import amsi.dei.estg.ipleiria.infortec_android.models.Produto;
import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.utils.FavoritosJsonParser;
import amsi.dei.estg.ipleiria.infortec_android.utils.ProdutoJsonParser;
import amsi.dei.estg.ipleiria.infortec_android.utils.UserJsonParser;

public class ProdutoActivity extends AppCompatActivity {
    private int id;
    private Produto produto;
    private SingletonGestorTabelas singletonGestorTabelas;
    private TextView txtPreco, txtTitulo, txtDescGeral, txtDescricao, txtValorDesconto;
    private ImageView ivFoto;
    private String urlImg = "http://188.81.6.107/Infortec/infortec_site/frontend/web/imagens/";
    private FloatingActionButton fabFav;
    private boolean fav = false;

    public ProdutoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtPreco = findViewById(R.id.txtPreco);
        ivFoto = findViewById(R.id.ivFotoProduto);
        txtDescGeral = findViewById(R.id.txtDescGeral);
        txtDescricao = findViewById(R.id.txtDesc);
        txtValorDesconto = findViewById(R.id.txtValorDesconto);
        fabFav = findViewById(R.id.favFlb);
        //produtos = SingletonGestorTabelas.getInstance(getContext()).getProdutosBD();
/*
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
        //}
        id = getIntent().getIntExtra("ID_PRODUTO", 0);
        produto = SingletonGestorTabelas.getInstance(getApplicationContext()).getProdutoById(id);

        txtTitulo.setText(produto.getNome());
        if(produto.getValorDesconto() != 0)
            txtValorDesconto.setText("- " + produto.getValorDesconto() + "€");
        else
            txtValorDesconto.setText("");

        double preco = produto.getPreco()-produto.getValorDesconto();
        String precoFinal = String.format("%.2f", preco);

        txtPreco.setText(precoFinal + "€");

        Glide.with(this)
                .load(urlImg + produto.getFotoProduto())
                .placeholder(R.drawable.imginfortec)
                .thumbnail(0f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivFoto);

        txtDescGeral.setText(produto.getDescricaoGeral());
        txtDescricao.setText(produto.getDescricao());

        ArrayList<Favorito> favoritos;
        try {
            favoritos = SingletonGestorTabelas.getInstance(getApplicationContext()).getFavoritosDB();

            for (Favorito favorito : favoritos)
            {
                if(id == favorito.getProduto_id())
                {
                    fav = true;
                    fabFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        }

        catch (Exception e)
        {

        }

        fabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = SingletonGestorTabelas.getInstance(getApplicationContext()).readPreferences(getApplicationContext());

                String username = pref.getString("username", null);
                String password = pref.getString("password", null);

                try {
                        if(fav)
                            SingletonGestorTabelas.getInstance(getApplicationContext()).removerFavorito(getApplicationContext(), FavoritosJsonParser.isConnectionInternet(getApplicationContext()), produto.getId());
                        else
                            SingletonGestorTabelas.getInstance(getApplicationContext()).postNovoFavorito(getApplicationContext(), FavoritosJsonParser.isConnectionInternet(getApplicationContext()), produto.getId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
