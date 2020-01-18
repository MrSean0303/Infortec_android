package amsi.dei.estg.ipleiria.infortec_android.utils;

import android.content.Context;

import androidx.core.content.ContextCompat;

import org.json.JSONException;
import org.json.JSONObject;

import amsi.dei.estg.ipleiria.infortec_android.models.User;

public class UserJsonParser {

    public static User parserJsonUser(String response, Context context) throws JSONException {
        System.out.println("--> PARSER ADICIONAR: " + response);
        User auxUser = null;


        return auxUser;
    }

    public static User parserJsonUserObject (JSONObject response, Context context) throws JSONException
    {
        System.out.println("--> PARSER ADICIONAR: " + response);
        User auxUser = null;

        int id = response.getInt("id");
        String username = response.getString("username");
        String auth_key = response.getString("auth_key");
        String password_hash = response.getString("password_hash");
        String password_reset_token = "";
        String email = response.getString("email");
        int status = response.getInt("status");
        int role = response.getInt("role");
        int created_at = response.getInt("created_at");
        int updated_at = response.getInt("updated_at");
        String verification_token = response.getString("verification_token");

        auxUser = new User(id,username,auth_key,password_hash,password_reset_token,email,status,role,created_at,updated_at,verification_token);

        System.out.println("--> PARSER Adicionado: " + auxUser);
        return auxUser;
    }
}
