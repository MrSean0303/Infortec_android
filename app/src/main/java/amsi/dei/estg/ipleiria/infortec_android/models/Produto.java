package amsi.dei.estg.ipleiria.infortec_android.models;

public class Produto {

    private int id, quantStock, pontos, subCategoria_id, iva_id;
    private String nome, fotoProduto, descricao, descricaoGeral;
    private double preco, valorDesconto;

    public Produto(int id, int quantStock, int pontos, int subCategoria_id, int iva_id, String nome, String fotoProduto, String descricao, String descricaoGeral, double preco, double valorDesconto)
    {
        this.id = id;
        this.quantStock = quantStock;
        this.pontos = pontos;
        this.subCategoria_id = subCategoria_id;
        this.iva_id = iva_id;
        this.nome = nome;
        this.fotoProduto = fotoProduto;
        this.descricao = descricao;
        this.descricaoGeral = descricaoGeral;
        this.preco = preco;
        this.valorDesconto = valorDesconto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantStock() {
        return quantStock;
    }

    public void setQuantStock(int quantStock) {
        this.quantStock = quantStock;
    }

    public int getPontos() {
        return pontos;
    }

    public void setPontos(int pontos) {
        this.pontos = pontos;
    }

    public int getSubCategoria_id() {
        return subCategoria_id;
    }

    public void setSubCategoria_id(int subCategoria_id) {
        this.subCategoria_id = subCategoria_id;
    }

    public int getIva_id() {
        return iva_id;
    }

    public void setIva_id(int iva_id) {
        this.iva_id = iva_id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFotoProduto() {
        return fotoProduto;
    }

    public void setFotoProduto(String fotoProduto) {
        this.fotoProduto = fotoProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricaoGeral() {
        return descricaoGeral;
    }

    public void setDescricaoGeral(String descricaoGeral) {
        this.descricaoGeral = descricaoGeral;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public double getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(double valorDesconto) {
        this.valorDesconto = valorDesconto;
    }
}
