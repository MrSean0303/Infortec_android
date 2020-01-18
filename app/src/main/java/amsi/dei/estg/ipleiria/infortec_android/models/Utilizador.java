package amsi.dei.estg.ipleiria.infortec_android.models;

public class Utilizador {

    private int id, numPontos, user_id;
    private String nome, morada,nif;

    public Utilizador(int id, String nome, String nif, String morada, int numPontos, int user_id) {
        this.id = id;
        this.nif = nif;
        this.numPontos = numPontos;
        this.user_id = user_id;
        this.nome = nome;
        this.morada = morada;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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
}
