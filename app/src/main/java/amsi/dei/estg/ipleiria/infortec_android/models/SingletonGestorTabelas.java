package amsi.dei.estg.ipleiria.infortec_android.models;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amsi.dei.estg.ipleiria.infortec_android.listeners.ApiCallBack;
import amsi.dei.estg.ipleiria.infortec_android.utils.ProdutoJsonParser;
import amsi.dei.estg.ipleiria.infortec_android.utils.UserJsonParser;

public class SingletonGestorTabelas extends Application implements ApiCallBack {

    private ArrayList<Produto> produtos;
    private static RequestQueue volleyQueue = null;
    private BDHelper bdHelper;
    private static String mUrlApiProdutos = "http://188.81.8.49/Infortec/infortec_site/frontend/web/api/produto";
    private static String mUrlApiUsers = "http://188.81.8.49/Infortec/infortec_site/frontend/web/api/user";
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
        if (!isConnected) {
            produtos = getProdutosBD();
            Toast toast = Toast.makeText(context, "No Internet Available", Toast.LENGTH_LONG);
            toast.show();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlApiProdutos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        Toast toast = Toast.makeText(context, "Products Refreshed", Toast.LENGTH_LONG);
                        toast.show();
                        produtos = ProdutoJsonParser.ProdutoJsonParser(response, context);

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



    public void getUserAPI(final Context context, boolean isConnected, String username, String password) {
        final String user = username;
        final String pass = password;

        if (!isConnected) {
            produtos = getProdutosBD();
            Toast toast = Toast.makeText(context, "No Internet Available", Toast.LENGTH_LONG);
            toast.show();
        } else {
            JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, mUrlApiUsers, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Toast toast = Toast.makeText(context, "Login Efetuado", Toast.LENGTH_LONG);
                    toast.show();
                    if (!response.equals(null)) {
                        System.out.println("Your Array Response: " + response);
                    } else {
                        System.out.println("Your Array Response: " + "Data Null");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("ERRRRO: " + error);
                    Toast toast = Toast.makeText(context, "Nome ou Palavra-Pass Incorretos", Toast.LENGTH_LONG);
                    toast.show();

                }
            }){    @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                String credentials = user + ":" + pass;
                String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Authorization", "Basic " + base64EncodedCredentials);
                return headers;
            }
            };volleyQueue.add(req);
        }
    }



    public ArrayList<Produto> getProdutosBD() {
        produtos = bdHelper.getAllProdutosDB();

        return produtos;
    }

    public ArrayList<Produto> getProdutosPromocoesBD() {
        ArrayList<Produto> produtosPromocoes = new ArrayList<>();
        produtos = bdHelper.getAllProdutosDB();

        for (Produto produto: produtos) {
            if(produto.getValorDesconto() > 0)
            {
                produtosPromocoes.add(produto);
            }
        }
        return produtosPromocoes;
    }


    public void getAllProdutosPromocoesAPI(final Context context, boolean isConnected) {
        if (!isConnected) {
            produtos = getProdutosPromocoesBD();
        } else {
            JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlApiProdutos, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        produtos = ProdutoJsonParser.ProdutoJsonParser(response, context);

                        adicionarProdutosBD(produtos);

                        if (listener != null) {
                            listener.onRefreshProdutos(getProdutosPromocoesBD());
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


    public void setListener(ApiCallBack listener) {
        this.listener = listener;
    }

    @Override
    public void onRefreshProdutos(ArrayList<Produto> produtos) {

    }

    public void adicionarUser(User user){
        bdHelper.adicionarUserBD(user);
        System.out.println("---> Depois do Sn: ");
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

    public User getUserByUsername(String username){
        System.out.println("---> a: " + username);
       ArrayList<User> users = bdHelper.getUser();
        System.out.println("---> b: " + users);
       for (User user : users){
           if (user.getUsername().equals(username)){
               System.out.println("---> c " + user);
               return user;
           }
       }
        System.out.println("---> abc: ");
        return null;

    }

    //User
    public void adicionarUserAPI (final User user, final Context context)
    {
        StringRequest req = new StringRequest(Request.Method.POST, mUrlApiUsers+"/registar", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("--> RESPOSTA ADD POST: " + response);
                if(listener != null)
                {
                    try {
                        User auxUser= null;


                        auxUser = UserJsonParser.parserJsonUser(response, context);
                        System.out.println("--> Sai do parser: " + auxUser);
                        adicionarUser(auxUser);
                        System.out.println("--> Sai do listener: " + auxUser);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("--> ERRO ADD User: " + error.getMessage());
            }
        }){protected Map<String, String> getParams(){
            Map<String, String> params = new HashMap<>();
            params.put("nome", user.getNome());
            params.put("username", user.getUsername());
            params.put("email", user.getEmail());
            params.put("morada", "" + user.getMorada());
            params.put("nif", user.getNif());
            params.put("password", user.getPassword_hash());

            return params;
        }
        };
        volleyQueue.add(req);
    }


}
