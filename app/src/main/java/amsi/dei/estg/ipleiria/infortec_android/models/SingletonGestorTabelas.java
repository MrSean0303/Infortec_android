package amsi.dei.estg.ipleiria.infortec_android.models;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class SingletonGestorTabelas extends Application{

    private ArrayList<Produto> produtos;
    private static RequestQueue volleyQueue = null;
    private static String mUrlAPILivros = "http://amsi.dei.estg.ipleiria.pt/api/livros";
    private String tokenAPI = "AMSI-TOKEN";

    private static SingletonGestorTabelas INSTANCE = null;

    public static synchronized SingletonGestorTabelas getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SingletonGestorTabelas(context);
            volleyQueue = Volley.newRequestQueue(context);
        }
        return INSTANCE;
    }

    private SingletonGestorTabelas(Context context) {
        produtos = new ArrayList<>();

    }

    public void getAllProdutosAPI(final Context context, boolean isConnected)
    {
        Toast.makeText(context, "IS CONNECTED: " + isConnected, Toast.LENGTH_SHORT);

        if(!isConnected)
        {
            produtos = livroBDHelper.getAllKivrosDB();
        }
        else{
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlAPILivros, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        produtos = LivroJsonParser.parserJsonLivros(response, context);
                        System.out.println("---> RESPOSTA: " + livros);

                        adicionarLivrosBD(livros);

                        if(livrosListener != null)
                        {
                            livrosListener.onRefreshListaLivros(livros);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("---> ERRO: GETALLLIVROSAPI: " + error.getMessage());
                }
            });
            volleyQueue.add(req);
        }
    }
}
