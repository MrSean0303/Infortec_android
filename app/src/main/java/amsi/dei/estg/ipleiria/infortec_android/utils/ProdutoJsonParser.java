package amsi.dei.estg.ipleiria.infortec_android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.models.Produto;

public class ProdutoJsonParser {

    public static ArrayList<Produto> ProdutoJsonParser(JSONArray response, Context context) throws JSONException
    {
        System.out.println("--> PARSER LISTA LIVROS: "+ response);
        ArrayList<Produto> tempListaProdutos = new ArrayList<Produto>();

        Produto auxProduto = null;

        for(int i = 0; i < response.length(); i++)
        {
            JSONObject produto = (JSONObject) response.get(i);

            int id = produto.getInt("id");
            String nome = produto.getString("nome");
            String descricaoGeral = produto.getString("descricaoGeral");
            String descricao = produto.getString("descricao");
            int quantStock = produto.getInt("quantStock");
            int pontos = produto.getInt("pontos");
            int subcategoria_id = produto.getInt("subcategoria_id");
            int iva_id = produto.getInt("iva_id");
            String fotoProduto = produto.getString("fotoProduto");
            double preco = produto.getDouble("preco");
            double valorDesconto = produto.getDouble("valorDesconto");

            auxProduto = new Produto(id, quantStock, pontos, subcategoria_id, iva_id, nome, fotoProduto, descricao, descricaoGeral, preco, valorDesconto);
            tempListaProdutos.add(auxProduto);
        }

        return tempListaProdutos;
    }

    public static Produto parserJsonProdutos(String response, Context context) throws JSONException {
        System.out.println("--> PARSER ADICIONAR: " + response);
        Produto auxProduto = null;

        JSONObject produto = new JSONObject(response);

        int id = produto.getInt("id");
        String nome = produto.getString("nome");
        String descricaoGeral = produto.getString("descricaoGeral");
        String descricao = produto.getString("descricao");
        int quantStock = produto.getInt("quantStock");
        int pontos = produto.getInt("pontos");
        int subcategoria_id = produto.getInt("subcategoria_id");
        int iva_id = produto.getInt("iva_id");
        String fotoProduto = produto.getString("fotoProduto");
        double preco = produto.getDouble("preco");
        double valorDesconto = produto.getDouble("valorDesconto");

        auxProduto = new Produto(id, quantStock, pontos, subcategoria_id, iva_id, nome, fotoProduto, descricao, descricaoGeral, preco, valorDesconto);
        return auxProduto;
    }

    public static boolean isConnectionInternet(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
