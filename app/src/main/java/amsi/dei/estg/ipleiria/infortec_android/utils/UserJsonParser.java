package amsi.dei.estg.ipleiria.infortec_android.utils;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import amsi.dei.estg.ipleiria.infortec_android.models.User;

public class UserJsonParser {

    public static User parserJsonUser(String response, Context context) throws JSONException {
        System.out.println("--> PARSER ADICIONAR: " + response);
        User auxUser = null;

        JSONObject user = new JSONObject(response);

        int id = user.getInt("id");
        String username = user.getString("username");
        String auth_key = user.getString("auth_key");
        String password_hash = user.getString("password_hash");
        String password_reset_token = "";
        String email = user.getString("email");
        int status = user.getInt("status");
        int role = user.getInt("role");
        int created_at = user.getInt("created_at");
        int updated_at = user.getInt("updated_at");
        String verification_token = user.getString("verification_token");

        auxUser = new User(id,username,auth_key,password_hash,password_reset_token,email,status,role,created_at,updated_at,verification_token);

        System.out.println("--> PARSER Adicionado: " + auxUser);
        return auxUser;


    }
}
