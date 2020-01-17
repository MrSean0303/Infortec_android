package amsi.dei.estg.ipleiria.infortec_android.models;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amsi.dei.estg.ipleiria.infortec_android.listeners.ApiCallBack;
import amsi.dei.estg.ipleiria.infortec_android.utils.ProdutoJsonParser;

public class SingletonGestorTabelas extends Application implements ApiCallBack {

    private ArrayList<Produto> produtos;
    private static RequestQueue volleyQueue = null;
    private BDHelper bdHelper;
    private static String mUrlApiProdutos = "http://188.81.8.115/Infortec/infortec_site/frontend/web/api/produto";
    private static String mUrlApiUsers = "http://188.81.8.115/Infortec/infortec_site/frontend/web/api/user";
    private ApiCallBack listener;

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
        bdHelper = new BDHelper(context);
    }

    public void getAllProdutosAPI(final Context context, boolean isConnected) {
        Toast.makeText(context, "IS CONNECTED: " + isConnected, Toast.LENGTH_SHORT);
        System.out.println("---> IS CONNECTED: " + isConnected);
        if (!isConnected) {
            produtos = bdHelper.getAllProdutosDB();
            System.out.println("---> produtosREEE: " + produtos);
        } else {
            System.out.println("---> TEST");
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlApiProdutos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        produtos = ProdutoJsonParser.ProdutoJsonParser(response, context);
                        System.out.println("---> RESPOSTA: " + produtos);

                        adicionarProdutosBD(produtos);

                        if (listener != null) {
                            listener.onRefreshProdutos(produtos);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("---> ERRO: " + e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("---> ERRO: GetAllProdutosAPI: " + error.getMessage());
                }
            });
            volleyQueue.add(req);
        }
    }

    public ArrayList<Produto> getProdutosBD() {
        produtos = bdHelper.getAllProdutosDB();
        return produtos;
    }

    public void setListener(ApiCallBack listener) {
        this.listener = listener;
    }

    @Override
    public void onRefreshProdutos(ArrayList<Produto> produtos) {

    }

    public void adicionarProdutosBD(ArrayList<Produto> listaProdutos) {
        bdHelper.removerAllProdutosDB();
        for (Produto produto : listaProdutos) {
            bdHelper.adicionarProdutoBD(produto);
        }
    }

    public Produto getProdutoById(int id) {
        ArrayList<Produto> produtosLista = getProdutosBD();
        for (Produto produto : produtosLista) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    //User
    public void adicionarUserAPI (final User user,final Utilizador utilizador, final Context context)
    {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlApiUsers+"/registar", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("--> RESPOSTA ADD POST: " + response);
                if(listener != null)
                {
                    /*try {

                        listener.onUpdateListaLivrosBD(LivroJsonParser.parserJsonLivros(response, context), 1);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("--> ERRO ADD LIVRO: " + error.getMessage());
            }
        }){protected Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("nome", utilizador.getNome());
            params.put("username", user.getUsername());
            params.put("email", user.getEmail());
            params.put("morada", "" + utilizador.getMorada());
            params.put("nif", utilizador.getNif());
            params.put("password", user.getPassword_hash());

            return params;
        }
        };
        volleyQueue.add(req);
    }

    public void cheakUsername(final String username){

        StringRequest req = new StringRequest(Request.Method.POST, mUrlApiUsers+"/cheakusername", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){protected Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("username", username);

            return params;
        }
        };
        volleyQueue.add(req);

    }
}
