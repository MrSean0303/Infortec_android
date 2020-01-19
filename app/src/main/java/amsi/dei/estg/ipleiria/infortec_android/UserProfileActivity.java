package amsi.dei.estg.ipleiria.infortec_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.models.User;

public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText Etnome;
    private EditText EtUsername;
    private EditText EtEmail;
    private EditText EtMorada;
    private EditText EtNif;

    public User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Etnome = findViewById(R.id.EtNome);
        EtEmail = findViewById(R.id.EtEmail);
        EtMorada = findViewById(R.id.EtMorada);
        EtNif = findViewById(R.id.EtNif);

        user = SingletonGestorTabelas.getInstance(getBaseContext()).getUser();

        Etnome.setText(user.getNome());
        EtEmail.setText(user.getEmail());
        EtMorada.setText(user.getMorada());
        EtNif.setText(user.getNif());
    }

    @Override
    public void onClick(View v) {

        SharedPreferences pref = this.getSharedPreferences("MyPref", 0);
        String username = pref.getString("username", null);
        String password = pref.getString("password", null);

        user.setNome(Etnome.getText().toString());
        user.setMorada(EtMorada.getText().toString());
        user.setNif(EtNif.getText().toString());
        user.setEmail(EtEmail.getText().toString());

        Map<String, String> auxuser = new HashMap<>();
        auxuser.put("username",username );
        auxuser.put("password", password);
        auxuser.put("nome", user.getNome());
        auxuser.put("morada", user.getMorada());
        auxuser.put("nif", user.getNif());
        auxuser.put("email", user.getEmail());

        System.out.println("--> ABC " + username);
        System.out.println("--> ABC " + password);
        System.out.println("--> nome " + user.getNome());
        System.out.println("--> morada " + user.getMorada());
        System.out.println("--> nif " + user.getNif());
        System.out.println("--> email " + user.getEmail());

        try {
            SingletonGestorTabelas.getInstance(getBaseContext()).editUserAPI(auxuser, getBaseContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SingletonGestorTabelas.getInstance(getBaseContext()).adicionarUserBD(user);
        finish();
    }
}
