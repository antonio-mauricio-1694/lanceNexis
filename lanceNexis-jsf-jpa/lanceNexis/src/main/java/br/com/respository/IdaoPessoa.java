package br.com.respository;

import br.com.entitys.Pessoa;

public interface IdaoPessoa {
	
	
		
		Pessoa  consultarUsuario(String login , String senha);
	

}
