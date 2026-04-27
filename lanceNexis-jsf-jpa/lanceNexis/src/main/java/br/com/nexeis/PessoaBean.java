// src/main/java/br/com/nexeis/PessoaBean.java
package br.com.nexeis;

import java.io.Serializable;
import br.com.entitys.Pessoa;
import dao.DaoGeneric;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named("pessoaBean")
@SessionScoped
public class PessoaBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Pessoa pessoa = new Pessoa();
    private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();

    public String salvar() {
    	 pessoa =  daoGeneric.merge(pessoa);
        // limpa o form após salvar
        return null;
    }
    
    public String novo() {
    	
    	pessoa = new Pessoa();
    	
    	return null;
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
}