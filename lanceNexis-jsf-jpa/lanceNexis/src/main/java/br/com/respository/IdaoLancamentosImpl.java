package br.com.respository;

import java.util.List;

import br.com.entitys.Lancamento;
import br.com.jpaUtil.JpaUtil;
import jakarta.persistence.EntityManager;

public class IdaoLancamentosImpl implements IdaoLancamento {

    @Override
    public List<Lancamento> consultar(Long codUser) {

        EntityManager entityManager = JpaUtil.getEntityManager();

        try {
            return entityManager.createQuery(
                    "SELECT l FROM Lancamento l WHERE l.usuario.id = :usuarioId",
                    Lancamento.class
            )
            .setParameter("usuarioId", codUser)
            .getResultList();

        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    // ✅ DELETE CORRETO (AGORA SIM)
    public void deletar(Long id) {

        EntityManager entityManager = JpaUtil.getEntityManager();

        try {
            entityManager.getTransaction().begin();

            Lancamento lancamento = entityManager.find(Lancamento.class, id);

            if (lancamento != null) {
                entityManager.remove(lancamento);
            }

            entityManager.getTransaction().commit();

        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;

        } finally {
            if (entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}