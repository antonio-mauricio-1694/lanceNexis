// src/main/java/br/com/nexeis/PessoaBean.java
package br.com.nexeis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.entitys.Pessoa;
import br.com.respository.IdaoPessoa;
import dao.DaoGeneric;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named("pessoaBean")
@SessionScoped
public class PessoaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    // ================= LOGIN =================
    private String login;
    private String senha;
    private Pessoa usuarioLogado;

    // ================= CRUD =================
    private Pessoa pessoa = new Pessoa();
    private List<Pessoa> pessoas = new ArrayList<>();

    // ✅ Instance<> resolve o WELD-001413 sem tocar no DaoGeneric
    // O Weld não tenta serializar o proxy, apenas o Instance wrapper
    @Inject
    private Instance<DaoGeneric<Pessoa>> daoInstance;

    @Inject
    private IdaoPessoa idaoPessoa;

    // ✅ Atalho interno — não expõe o campo direto
    private DaoGeneric<Pessoa> dao() {
        return daoInstance.get();
    }

    @PostConstruct
    public void init() {
        carregarPessoas();
    }

    // ================= LOGIN =================
    public String logar() {
        Pessoa usuario = idaoPessoa.consultarUsuario(login, senha);

        if (usuario != null) {
            this.usuarioLogado = usuario;

            // ✅ Grava na sessão HTTP — filtro lê daqui
            FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .put("usuarioLogado", usuario);

            System.out.println("LOGIN OK: " + usuario.getNome());
            return "/index.xhtml?faces-redirect=true";
        }

        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                "Login ou senha inválidos.", null));

        return null;
    }

    public String deslogar() {
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .invalidateSession();
        return "/login.xhtml?faces-redirect=true";
    }

    // ================= CRUD =================
    public String salvar() {
        if (Boolean.TRUE.equals(pessoa.getInativo())) {
            pessoa.setAtivo(false);
        } else if (Boolean.TRUE.equals(pessoa.getAtivo())) {
            pessoa.setInativo(false);
        } else {
            pessoa.setInativo(true);
        }

        dao().merge(pessoa);
        pessoa = new Pessoa();
        carregarPessoas();
        return null;
    }

    public String novo() {
        pessoa = new Pessoa();
        return null;
    }

    public String remove() {
        dao().deletePorId(pessoa, pessoa.getId());
        pessoa = new Pessoa();
        carregarPessoas();
        return null;
    }

    public void carregarPessoas() {
        pessoas = dao().getListEntity(Pessoa.class);
    }

    // ================= STATUS =================
    public void onAtivoChange() {
        if (Boolean.TRUE.equals(pessoa.getAtivo())) {
            pessoa.setInativo(false);
        }
    }

    public void onInativoChange() {
        if (Boolean.TRUE.equals(pessoa.getInativo())) {
            pessoa.setAtivo(false);
        }
    }

    // ================= GETTERS/SETTERS =================
    public List<Pessoa> getPessoas()            { return pessoas; }
    public Pessoa getPessoa()                   { return pessoa; }
    public void setPessoa(Pessoa pessoa)        { this.pessoa = pessoa; }
    public String getLogin()                    { return login; }
    public void setLogin(String login)          { this.login = login; }
    public String getSenha()                    { return senha; }
    public void setSenha(String senha)          { this.senha = senha; }
    public Pessoa getUsuarioLogado()            { return usuarioLogado; }
    public boolean isLogado()                   { return usuarioLogado != null; }

    public List<String> getOpcoesSetor() {
        return Arrays.asList("Gerente", "Diretor", "Administrativo", "RH");
    }
}