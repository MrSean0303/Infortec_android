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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
    }

    public void onClickSignup() {
        String email = editTextEmail.getText().toString();
        String passord = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (!isEmailValido(email)){
            return;
        }

        if (!isPasswordValida(passord, confirmPassword)) {
            return;
        }





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
