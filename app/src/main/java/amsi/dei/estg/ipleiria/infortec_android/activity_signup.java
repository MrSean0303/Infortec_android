package amsi.dei.estg.ipleiria.infortec_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import amsi.dei.estg.ipleiria.infortec_android.models.SingletonGestorTabelas;
import amsi.dei.estg.ipleiria.infortec_android.models.User;

public class activity_signup extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    public void onClick(View v) {
        String nome = editTextNome.getText().toString();
        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String passord = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();
        String nif = editTextNif.getText().toString();


        if (!isEmailValido(email)) {
            return;
        }

        if (!isPasswordValida(passord, confirmPassword)) {
            return;
        }

        if (!isNifValido(nif)) {
            return;
        }
/*
        //Cria user incompleto com os dados introduzidos.
        User user = new User(0, nome, username, "", passord,  email, 0, "", "", 0);

        System.out.println("---> Antes do Singleton: " + user);

        //Envia o user para ser registado e adiciona-o a base de dados local
        SingletonGestorTabelas.getInstance(this.getBaseContext()).adicionarUserAPI(user, getBaseContext());

        System.out.println("---> Depois do Singleton: ");

        //Vai buscar o user colocado na base de dados local
        user = SingletonGestorTabelas.getInstance(this.getBaseContext()).getUserByUsername(user.getUsername());

        System.out.println("--> Até aqui tudo bem: " + user);
*/
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
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Email não existe");
            return false;
        }
        return true;
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

        if (password.length() < 8){
            editTextPassword.setError("Password tem de conter mais de 8 caracteres");
            return false;
        }

        return true;
    }



}
