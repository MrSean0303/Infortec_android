package amsi.dei.estg.ipleiria.infortec_android.models;

public class User {
    private int id, status;
    private String username, auth_key, password_hash, email;
    private String nome, morada, nif;
    private int numPontos;

    public User(int id,String nome, String username,String auth_key, String password_hash, String email, int status, String morada, String nif, int numPontos) {
        this.id = id;
        this.status = status;
        this.username = username;
        this.auth_key = auth_key;
        this.password_hash = password_hash;
        this.email = email;
        this.nome = nome;
        this.morada = morada;
        this.nif = nif;
        this.numPontos = numPontos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public int getNumPontos() {
        return numPontos;
    }

    public void setNumPontos(int numPontos) {
        this.numPontos = numPontos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuth_key() {
        return auth_key;
    }

    public void setAuth_key(String auth_key) {
        this.auth_key = auth_key;
    }

    public String getPassword_hash() {
        return password_hash;
    }

    public void setPassword_hash(String password_hash) {
        this.password_hash = password_hash;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
