package model;

import java.math.BigDecimal;

public class Salesman {

    public static final String SALESMAN_CODE = "001";

    private String cpf;
    private String nome;
    private BigDecimal salary;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Salesman{"
                + "cpf='" + cpf + '\''
                + ", nome='" + nome + '\''
                + ", salary=" + salary
                + '}';
    }
}
