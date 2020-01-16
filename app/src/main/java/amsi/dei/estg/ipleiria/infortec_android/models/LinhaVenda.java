package amsi.dei.estg.ipleiria.infortec_android.models;

public class LinhaVenda {

    public int id, quantidade, venda_id, produto_id,isPontos;
    public Double preco;

    public LinhaVenda(int id, int quantidade, int isPontos, Double preco, int venda_id, int produto_id) {
        this.id = id;
        this.quantidade = quantidade;
        this.venda_id = venda_id;
        this.produto_id = produto_id;
        this.preco = preco;
        this.isPontos = isPontos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getVenda_id() {
        return venda_id;
    }

    public void setVenda_id(int venda_id) {
        this.venda_id = venda_id;
    }

    public int getProduto_id() {
        return produto_id;
    }

    public void setProduto_id(int produto_id) {
        this.produto_id = produto_id;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public int getIsPontos() {
        return isPontos;
    }

    public void setIsPontos(int isPontos) {
        this.isPontos = isPontos;
    }

    public int isPontos() {
        return isPontos;
    }

    public void setPontos(int pontos) {
        isPontos = pontos;
    }
}
