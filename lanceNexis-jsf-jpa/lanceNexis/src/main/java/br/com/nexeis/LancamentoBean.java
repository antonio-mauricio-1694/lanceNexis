package br.com.nexeis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.entitys.Lancamento;
import br.com.entitys.Pessoa;
import br.com.respository.IdaoLancamento;
import br.com.respository.IdaoLancamentosImpl;
import dao.DaoGeneric;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("lancamentoBean")
@SessionScoped
public class LancamentoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Lancamento lancamento = new Lancamento();
    private DaoGeneric<Lancamento> daoGeneric = new DaoGeneric<>();
    private List<Lancamento> lancamentos = new ArrayList<>();
    private IdaoLancamento daIdaoLancamento = new IdaoLancamentosImpl();

    // =========================
    // INIT
    // =========================
    @PostConstruct
    public void init() {
        carregarLancamentos();
    }

    // =========================
    // USUARIO LOGADO
    // =========================
    private Pessoa getUsuarioLogado() {
        return (Pessoa) FacesContext.getCurrentInstance()
                .getExternalContext()
                .getSessionMap()
                .get("usuarioLogado");
    }

    // =========================
    // SALVAR
    // =========================
    public String salvar() {

        FacesContext context = FacesContext.getCurrentInstance();
        Pessoa usuario = getUsuarioLogado();

        if (usuario == null) {
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Sessão expirada. Faça login novamente.", null));
            return "/login.xhtml?faces-redirect=true";
        }

        try {
            lancamento.setUsuario(usuario);

            daoGeneric.salvar(lancamento);

            lancamento = new Lancamento();

            carregarLancamentos();

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Lançamento salvo com sucesso!", null));

        } catch (Exception e) {
            e.printStackTrace();

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro ao salvar lançamento.", null));
        }

        return null;
    }

    // =========================
    // CARREGAR
    // =========================
    public void carregarLancamentos() {

        Pessoa usuario = getUsuarioLogado();

        if (usuario != null) {
            lancamentos = daIdaoLancamento.consultar(usuario.getId());
        } else {
            lancamentos = new ArrayList<>();
        }
    }

    // =========================
    // NOVO
    // =========================
    public String novo() {
        lancamento = new Lancamento();
        return null;
    }
    
    public String editar(Lancamento lancamentoSelecionado) {
        this.lancamento = lancamentoSelecionado;
        return null;
    }

    public void remover(Lancamento l) {
        FacesContext context = FacesContext.getCurrentInstance();

        try {
            daoGeneric.deletePorId(l, l.getId()); // ✅ CORRETO

            carregarLancamentos();

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Removido com sucesso!", null));

        } catch (Exception e) {
            e.printStackTrace();

            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Erro ao remover!", null));
        }
    }

    // =========================
    // GETTERS / SETTERS
    // =========================
    public Lancamento getLancamento() {
        return lancamento;
    }

    public void setLancamento(Lancamento lancamento) {
        this.lancamento = lancamento;
    }

    public List<Lancamento> getLancamentos() {
        return lancamentos;
    }

    public void setLancamentos(List<Lancamento> lancamentos) {
        this.lancamentos = lancamentos;
    }
}