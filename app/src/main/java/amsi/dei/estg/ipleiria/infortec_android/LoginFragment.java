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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.models.User;
import amsi.dei.estg.ipleiria.infortec_android.utils.ProdutoJsonParser;
import amsi.dei.estg.ipleiria.infortec_android.utils.UserJsonParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener{

    private Button buttonLogin;
    private EditText editTextUserName;
    private EditText editTextPassword;
    private NavigationView navigationView;
    private static RequestQueue volleyQueue = null;
    private User user;
    private String mUrlApiLogin = "http://188.81.0.111/Infortec/infortec_site/frontend/web/api/user";

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_login, container, false);
        View view = inflater.inflate(R.layout.activity_main, container, false);
        navigationView = view.findViewById(R.id.nav_view);

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

        SingletonGestorTabelas.getInstance(getContext()).getUserAPI(getContext(), UserJsonParser.isConnectionInternet(getContext()), username, password);

    }
}
