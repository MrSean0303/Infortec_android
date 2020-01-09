package amsi.dei.estg.ipleiria.infortec_android.models;

public class Favorito {
    private int idFavorito, produto_id, utilizador_id;

    public Favorito(int idFavorito, int produto_id, int utilizador_id)
    {
        this.idFavorito = idFavorito;
        this.produto_id = produto_id;
        this.utilizador_id = utilizador_id;
    }


    public int getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(int idFavorito) {
        this.idFavorito = idFavorito;
    }

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public int getUtilizador_id() {
        return utilizador_id;
    }

    public void setUtilizador_id(int utilizador_id) {
        this.utilizador_id = utilizador_id;
    }
}

