package amsi.dei.estg.ipleiria.infortec_android;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.Objects;

import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.utils.UserJsonParser;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {

    private EditText editTextUserName;
    private EditText editTextPassword;
    private String mUrlApiLogin = "http://188.81.0.111/Infortec/infortec_site/frontend/web/api/user";

    public LoginFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewRoot = inflater.inflate(R.layout.fragment_login, container, false);

        Button buttonLogin = viewRoot.findViewById(R.id.buttonlogin);
        buttonLogin.setOnClickListener(this);

        editTextUserName = viewRoot.findViewById(R.id.editTextUserName);
        editTextPassword = viewRoot.findViewById(R.id.editTextPassword);


        final View buttonRegist = viewRoot.findViewById(R.id.buttonRegist);
        buttonRegist.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getContext(), SignupActivity.class);
                        startActivity(intent);
                    }
                }
        );


        return viewRoot;
    }

    @Override
    public void onStart() {
        super.onStart();

        SharedPreferences pref = SingletonGestorTabelas.getInstance(getContext()).readPreferences(Objects.requireNonNull(getContext()));
        String username = pref.getString("username", null);
        String password = pref.getString("password", null);

        editTextUserName.setText(username);
        editTextPassword.setText(password);

    }

    @Override
    public void onClick(View v) {
        String username = editTextUserName.getText().toString();
        String password = editTextPassword.getText().toString();

        SingletonGestorTabelas.getInstance(getContext()).writePreferences("username", username);
        SingletonGestorTabelas.getInstance(getContext()).writePreferences("password", password);

        SingletonGestorTabelas.getInstance(getContext()).getUserAPI(getContext(), UserJsonParser.isConnectionInternet(Objects.requireNonNull(getContext())), username, password);
    }
}
