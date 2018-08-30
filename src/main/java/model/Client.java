package model;

public class Client {

    public static final String CLIENT_CODE = "002";

    private String cnpj;
    private String nome;
    private String businessArea;

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    @Override
    public String toString() {
        return "Client{"
                + ", cnpj='" + cnpj + '\''
                + ", nome='" + nome + '\''
                + ", businessArea='" + businessArea + '\''
                + '}';
    }
}
