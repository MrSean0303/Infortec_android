package amsi.dei.estg.ipleiria.infortec_android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import amsi.dei.estg.ipleiria.infortec_android.models.Favorito;

public class FavoritosJsonParser {

    public static Favorito FavoritoJsonParserObj(JSONObject response, Context context) throws JSONException
    {
        System.out.println("--> PARSER LISTA PRODUTOS: "+ response);

        Favorito auxFavorito;

        int produto_id = response.getInt("produto_id");
        int idFavorito = response.getInt("idFavorito");
        int utilizador_id = response.getInt("utilizador_id");

        auxFavorito = new Favorito(idFavorito, produto_id, utilizador_id);

        return auxFavorito;
    }

    public static ArrayList<Favorito> FavoritoJsonParser(JSONArray response, Context context) throws JSONException
    {
        System.out.println("--> PARSER LISTA PRODUTOS: "+ response);

        ArrayList<Favorito> tempListaFavoritos = new ArrayList<Favorito>();

        Favorito auxFavorito = null;

        for(int i = 0; i < response.length(); i++)
        {
            JSONObject favorito = response.getJSONObject(i);

            int produto_id = favorito.getInt("produto_id");
            int idFavorito = favorito.getInt("idFavorito");
            int utilizador_id = favorito.getInt("utilizador_id");

            auxFavorito = new Favorito(idFavorito, produto_id, utilizador_id);
            tempListaFavoritos.add(auxFavorito);
        }


        return tempListaFavoritos;
    }
    public static boolean isConnectionInternet(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
