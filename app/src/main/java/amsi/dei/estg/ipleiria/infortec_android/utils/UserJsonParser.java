package amsi.dei.estg.ipleiria.infortec_android.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import amsi.dei.estg.ipleiria.infortec_android.models.User;

public class UserJsonParser {

    public static User parserJsonUserObject (JSONObject response, Context context) throws JSONException
    {
        System.out.println("--> PARSER ADICIONAR: " + response);
        User auxUser = null;

        int id = response.getInt("id");
        String nome = response.getString("nome");
        String username = response.getString("username");
        String email = response.getString("email");
        int status = response.getInt("status");
        String morada = response.getString("morada");
        int numPontos = response.getInt("numPontos");
        String nif = response.getString("nif");


        auxUser = new User(id, nome, username,email,status, morada, nif, numPontos);

        System.out.println("--> PARSER Adicionado: " + auxUser);
        return auxUser;
    }
}
