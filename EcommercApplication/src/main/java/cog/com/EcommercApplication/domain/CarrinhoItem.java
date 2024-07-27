package cog.com.EcommercApplication.domain;

public class CarrinhoItem {
    private Suplementos suplemento;
    private int quantidade;

    public CarrinhoItem(Suplementos suplemento, int quantidade) {
        this.suplemento = suplemento;
        this.quantidade = quantidade;
    }

    public Suplementos getSuplemento() {
        return suplemento;
    }

    public void setSuplemento(Suplementos suplemento) {
        this.suplemento = suplemento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}

