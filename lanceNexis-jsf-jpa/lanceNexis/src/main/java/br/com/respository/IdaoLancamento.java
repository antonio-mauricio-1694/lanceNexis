package br.com.respository;

import java.util.List;

import br.com.entitys.Lancamento;

public interface IdaoLancamento {
	
	List<Lancamento> consultar(Long codUser);

}
