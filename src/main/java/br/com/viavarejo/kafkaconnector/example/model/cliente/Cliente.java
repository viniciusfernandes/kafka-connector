package br.com.viavarejo.kafkaconnector.example.model.cliente;

import java.io.Serializable;

public class Cliente implements Serializable {

    private static final long serialVersionUID = 1264490621991794984L;

    private Integer codigo;
    private String nome;
    private String cpf;

    public Cliente() {}

    public Cliente(final Integer codigo, final String nome, final String cpf) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(final Integer codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(final String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String toString() {
        return "Cliente [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf + "]";
    }

}
