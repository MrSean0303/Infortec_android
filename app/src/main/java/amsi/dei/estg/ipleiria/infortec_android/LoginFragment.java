package amsi.dei.estg.ipleiria.infortec_android;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.utils.ProdutoJsonParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    Button buttonLogin;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private static RequestQueue volleyQueue = null;
    private String mUrlApiLogin = "http://188.81.0.111/Infortec/infortec_site/frontend/web/api/user";

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_login, container, false);

        buttonLogin = (Button) viewRoot.findViewById(R.id.buttonlogin);
        buttonLogin.setOnClickListener(this);

        editTextUserName = viewRoot.findViewById(R.id.editTextUserName);
        editTextPassword = viewRoot.findViewById(R.id.editTextPassword);

        final View buttonRegist = viewRoot.findViewById(R.id.buttonRegist);
        buttonRegist.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), activity_signup.class);
                        startActivity(intent);
                    }
                }
        );


        return viewRoot;
    }

    @Override
    public void onClick(View v) {

        String username = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

        SingletonGestorTabelas.getInstance(getContext()).getUserAPI(getContext(), ProdutoJsonParser.isConnectionInternet(getContext()), username, password);
        /*byte[] data = new byte[0];
        try {
            data = userName.getBytes("UTF-8");
            String base64User = Base64.encodeToString(data, Base64.DEFAULT);

            data = password.getBytes("UTF-8");
            String base64Pass = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        /*JsonArrayRequest req = new JsonArrayRequest(Request.Method.GET, mUrlApiLogin, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
            }
        }){    @Override
        public Map<String, String> getHeaders() {
            HashMap<String, String> headers = new HashMap<>();
            String credentials = userName + ":" + password;
            String base64EncodedCredentials = Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
            headers.put("Authorization", "Basic" + base64EncodedCredentials);
            return headers;
        }
        };volleyQueue.add(req);*/

    }
}
