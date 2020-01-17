package amsi.dei.estg.ipleiria.infortec_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import amsi.dei.estg.ipleiria.infortec_android.models.User;

public class activity_signup extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextNome;
    private EditText editTextNif;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextNome = findViewById(R.id.editTextNome);
        editTextNif = findViewById(R.id.editTextNif);

    }

    public void onClickSignup() {
        String nome = editTextNome.getText().toString();
        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String passord = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        String nif =  editTextNif.getText().toString();

        if (!isEmailValido(email)){
            return;
        }

        if (!isPasswordValida(passord, confirmPassword)) {
            return;
        }

        if (!isNifValido(nif)){
            return;
        }





    }

    public boolean isNifValido(String nif){
        if (nif.length() != 9){
            editTextNif.setError("Nif invalido");
            return false;
        }

        return true;
    }

    public boolean isEmailValido(String email){
        if (email == null) {
            editTextEmail.setError("Email Invalido");
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public boolean isPasswordValida(String password, String conf){

        if (password == null || conf == null) {
            editTextPassword.setError("Password não pode estar vazia");
            return false;
        }

        if (!password.equals(conf)){
            editTextPassword.setError("Password não coincidem");
            return false;
        }

        return password.length() > 4;
    }
}
