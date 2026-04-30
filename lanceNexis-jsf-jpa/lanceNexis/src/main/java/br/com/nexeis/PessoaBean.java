package br.com.nexeis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.entitys.Pessoa;
import dao.DaoGeneric;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("pessoaBean")
@ViewScoped
public class PessoaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pessoa pessoa = new Pessoa();
    private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<>();
    private List<Pessoa> pessoas = new ArrayList<>();

    public PessoaBean() {
        caregarPessoas();
    }

    // 🔥 SALVAR CORRIGIDO
    public String salvar() {

        // REGRA: só um pode ser true
        if (pessoa.getInativo()) {
            pessoa.setAtivo(false);
        } else if (pessoa.getAtivo()) {
            pessoa.setInativo(false);
        }

        // fallback (nenhum marcado)
        if (!pessoa.getAtivo() && !pessoa.getInativo()) {
            pessoa.setInativo(true);
        }

        pessoa = daoGeneric.merge(pessoa);

        // limpa form
        pessoa = new Pessoa();

        caregarPessoas();

        return null;
    }

    public String novo() {
        pessoa = new Pessoa();
        return null;
    }

    public String remove() {
        daoGeneric.deletePorId(pessoa, pessoa.getId());
        pessoa = new Pessoa();
        caregarPessoas();
        return null;
    }

    public void caregarPessoas() {
        pessoas = daoGeneric.getListEntity(Pessoa.class);
    }

    // ================= GETTERS =================

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public DaoGeneric<Pessoa> getDaoGeneric() {
        return daoGeneric;
    }

    public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
        this.daoGeneric = daoGeneric;
    }

    // ================= SETOR =================

    public List<String> getOpcoesSetor() {
        return Arrays.asList("Gerente", "Diretor", "Administrativo", "RH");
    }

    // ================= STATUS (CHECKBOX CONTROLADO) =================

    public void onAtivoChange() {
        if (pessoa.getAtivo()) {
            pessoa.setInativo(false);
        }
    }

    public void onInativoChange() {
        if (pessoa.getInativo()) {
            pessoa.setAtivo(false);
        }
    }
}