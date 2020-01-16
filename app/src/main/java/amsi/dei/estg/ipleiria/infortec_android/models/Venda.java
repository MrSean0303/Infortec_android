package amsi.dei.estg.ipleiria.infortec_android.models;


public class Venda {

    public int id, user_id;
    public String data;
    public double totalVenda;

    public Venda(int id, double totalVenda, String data, int user_id) {
        this.id = id;
        this.user_id = user_id;
        this.data = data;
        this.totalVenda = totalVenda;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(double totalVenda) {
        this.totalVenda = totalVenda;
    }
}
