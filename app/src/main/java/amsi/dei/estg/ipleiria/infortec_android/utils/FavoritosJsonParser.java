package amsi.dei.estg.ipleiria.infortec_android.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.models.Favorito;

public class FavoritosJsonParser {

    public static ArrayList<Favorito> ProdutoJsonParser(JSONArray response, Context context) throws JSONException
    {
        System.out.println("--> PARSER LISTA PRODUTOS: "+ response);
        ArrayList<Favorito> tempListaProdutos = new ArrayList<Favorito>();

        Favorito auxFavorito = null;

        for(int i = 0; i < response.length(); i++)
        {
            JSONObject favorito = response.getJSONObject(i);

            int produto_id = favorito.getInt("produto_id");
            int idFavorito = favorito.getInt("idFavorito");
            int utilizador_id = favorito.getInt("utilizador_id");

            auxFavorito = new Favorito(idFavorito, produto_id, utilizador_id);
            tempListaProdutos.add(auxFavorito);
        }

        return tempListaProdutos;
    }
}
