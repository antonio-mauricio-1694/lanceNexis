// src/main/java/br/com/respository/IdaoPessoImpl.java
package br.com.respository;

import br.com.entitys.Pessoa;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

@ApplicationScoped
public class IdaoPessoImpl implements IdaoPessoa {

    private static final EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("lanceNexis");

    @Override
    public Pessoa consultarUsuario(String login, String senha) {

        if (login == null || login.isBlank() || senha == null || senha.isBlank()) {
            return null;
        }

        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Pessoa> query = em.createQuery(
                "SELECT p FROM Pessoa p WHERE p.login = :login",
                Pessoa.class
            );
            query.setParameter("login", login);

            Pessoa pessoa = query.getSingleResult();

            if (pessoa.getSenha() != null && pessoa.getSenha().equals(senha)) {
                return pessoa;
            }

            return null;

        } catch (NoResultException e) {
            return null;

        } finally {
            em.close();
        }
    }
}