
package amsi.dei.estg.ipleiria.infortec_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amsi.dei.estg.ipleiria.infortec_android.models.Favorito;
import amsi.dei.estg.ipleiria.infortec_android.models.Produto;
import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.utils.FavoritosJsonParser;

public class ProdutoActivity extends AppCompatActivity  {
    private Produto produto;
    private String urlImg = "http://188.81.6.107/Infortec/infortec_site/frontend/web/imagens/";
    private FloatingActionButton fabFav;
    private Button buttonComprar;
    private DialogFragment dialogFragment;
    private boolean fav = false;
    private Context context = this;
    private EditText textView;
    private TextView txtPreco;

    private void showAlertDialog() {
        FragmentManager fm = getSupportFragmentManager();
        DialogFragment alertDialog = new DialogFragment();
        alertDialog.show(fm, "fragment_alert");
    }

    public ProdutoActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto);
        TextView txtTitulo = findViewById(R.id.txtTitulo);
        txtPreco = findViewById(R.id.txtPreco);
        ImageView ivFoto = findViewById(R.id.ivFotoProduto);
        TextView txtDescGeral = findViewById(R.id.txtDescGeral);
        TextView txtDescricao = findViewById(R.id.txtDesc);
        TextView txtValorDesconto = findViewById(R.id.txtValorDesconto);
        fabFav = findViewById(R.id.favFlb);
        buttonComprar = findViewById(R.id.btnCompra);

        buttonComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("---> Esta a funcionar ");
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogLayout = inflater.inflate(R.layout.fragment_compra, null);
                Button btnBuy =  dialogLayout.findViewById(R.id.btnBuy);
                textView = dialogLayout.findViewById(R.id.EtQuant);


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(dialogLayout);


                AlertDialog customAlertDialog = builder.create();
                customAlertDialog.show();

                btnBuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int quantidade = 0 ;
                        String textView = ProdutoActivity.this.textView.getText().toString();
                        System.out.println("---> textView " + textView);

                        if (textView.isEmpty()){
                            quantidade = 0;
                        }else {
                            quantidade = Integer.parseInt(textView);
                            if (quantidade < 1){
                                quantidade = 0;
                            }
                        }
                        System.out.println("---> quantidade " + quantidade);

                        SharedPreferences pref = SingletonGestorTabelas.getInstance(getApplicationContext()).readPreferences(getApplicationContext());
                        String username = pref.getString("username", null);
                        String password = pref.getString("password", null);

                        int id = getIntent().getIntExtra("ID_PRODUTO", 0);

                        Map<String, String> venda = new HashMap<>();
                        venda.put("username", username);
                        venda.put("password", password);
                        venda.put("total", txtPreco.getText().toString());
                        venda.put("quantidade", String.valueOf(quantidade));
                        venda.put("preco", txtPreco.getText().toString());
                        venda.put("produto_id",  String.valueOf(id));

                        try {
                            SingletonGestorTabelas.getInstance(getBaseContext()).adicionarVendaAPI(venda,context);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }
        });
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


        int id = getIntent().getIntExtra("ID_PRODUTO", 0);
        produto = SingletonGestorTabelas.getInstance(getApplicationContext()).getProdutoById(id);

        txtTitulo.setText(produto.getNome());
        if (produto.getValorDesconto() != 0)
            txtValorDesconto.setText("- " + produto.getValorDesconto() + "€");
        else
            txtValorDesconto.setText("");

        double preco = produto.getPreco() - produto.getValorDesconto();
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

            for (Favorito favorito : favoritos) {
                if (id == favorito.getProduto_id()) {
                    fav = true;
                    fabFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }
        } catch (Exception ignored) {
        }

        fabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = SingletonGestorTabelas.getInstance(getApplicationContext()).readPreferences(getApplicationContext());

                pref.getString("username", null);
                pref.getString("password", null);

                try {
                    if (fav) {
                        fabFav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        SingletonGestorTabelas.getInstance(getApplicationContext()).removerFavorito(getApplicationContext(), FavoritosJsonParser.isConnectionInternet(getApplicationContext()), produto.getId());
                        fav = false;
                    } else {
                        fabFav.setImageResource(R.drawable.ic_favorite_black_24dp);
                        SingletonGestorTabelas.getInstance(getApplicationContext()).postNovoFavorito(getApplicationContext(), FavoritosJsonParser.isConnectionInternet(getApplicationContext()), produto.getId());
                        fav = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
