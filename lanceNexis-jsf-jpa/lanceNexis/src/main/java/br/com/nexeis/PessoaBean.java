// src/main/java/br/com/nexeis/PessoaBean.java
package br.com.nexeis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();

	public String salvar() {
		pessoa = daoGeneric.merge(pessoa);
		// limpa o form após salvar
		caregarPessoas();
		return null;
	}

	public String novo() {

		pessoa = new Pessoa();

		return null;
	}

	public String remove() {
	    daoGeneric.deletePorId(pessoa, pessoa.getId());
	    caregarPessoas();
	    return null;
	}
	
	public void caregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
		
	}
	
	
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
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