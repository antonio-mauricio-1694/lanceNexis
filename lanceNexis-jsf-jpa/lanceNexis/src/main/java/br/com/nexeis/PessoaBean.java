package br.com.nexeis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("pessoaBean")
@ApplicationScoped
public class PessoaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nome;
    private List<String> nomes = new ArrayList<>();

    public String addNome() {
        if (nome != null && !nome.trim().isEmpty()) {
            nomes.add(nome);
            nome = ""; // limpa input (UX melhor)
        }
        return null; // JSF moderno usa null, não ""
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<String> getNomes() {
        return nomes;
    }

    public void setNomes(List<String> nomes) {
        this.nomes = nomes;
    }
}