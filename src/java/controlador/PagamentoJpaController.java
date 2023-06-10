/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Aluno;
import modelo.Cobranca;
import modelo.Matricula;
import modelo.Secretária;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Pagamento;

/**
 *
 * @author mahomed
 */
public class PagamentoJpaController implements Serializable {

    public PagamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagamento pagamento) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Cobranca cobrancaIDOrphanCheck = pagamento.getCobrancaID();
        if (cobrancaIDOrphanCheck != null) {
            Pagamento oldPagamentoOfCobrancaID = cobrancaIDOrphanCheck.getPagamento();
            if (oldPagamentoOfCobrancaID != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Cobranca " + cobrancaIDOrphanCheck + " already has an item of type Pagamento whose cobrancaID column cannot be null. Please make another selection for the cobrancaID field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aluno alunoID = pagamento.getAlunoID();
            if (alunoID != null) {
                alunoID = em.getReference(alunoID.getClass(), alunoID.getAlunoID());
                pagamento.setAlunoID(alunoID);
            }
            Cobranca cobrancaID = pagamento.getCobrancaID();
            if (cobrancaID != null) {
                cobrancaID = em.getReference(cobrancaID.getClass(), cobrancaID.getCobrancaID());
                pagamento.setCobrancaID(cobrancaID);
            }
            Matricula matriculaID = pagamento.getMatriculaID();
            if (matriculaID != null) {
                matriculaID = em.getReference(matriculaID.getClass(), matriculaID.getMatriculaID());
                pagamento.setMatriculaID(matriculaID);
            }
            Secretária secretáriaID = pagamento.getSecretáriaID();
            if (secretáriaID != null) {
                secretáriaID = em.getReference(secretáriaID.getClass(), secretáriaID.getSecretáriaID());
                pagamento.setSecretáriaID(secretáriaID);
            }
            em.persist(pagamento);
            if (alunoID != null) {
                alunoID.getPagamentoList().add(pagamento);
                alunoID = em.merge(alunoID);
            }
            if (cobrancaID != null) {
                cobrancaID.setPagamento(pagamento);
                cobrancaID = em.merge(cobrancaID);
            }
            if (matriculaID != null) {
                matriculaID.getPagamentoList().add(pagamento);
                matriculaID = em.merge(matriculaID);
            }
            if (secretáriaID != null) {
                secretáriaID.getPagamentoList().add(pagamento);
                secretáriaID = em.merge(secretáriaID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagamento pagamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagamento persistentPagamento = em.find(Pagamento.class, pagamento.getPagamentoID());
            Aluno alunoIDOld = persistentPagamento.getAlunoID();
            Aluno alunoIDNew = pagamento.getAlunoID();
            Cobranca cobrancaIDOld = persistentPagamento.getCobrancaID();
            Cobranca cobrancaIDNew = pagamento.getCobrancaID();
            Matricula matriculaIDOld = persistentPagamento.getMatriculaID();
            Matricula matriculaIDNew = pagamento.getMatriculaID();
            Secretária secretáriaIDOld = persistentPagamento.getSecretáriaID();
            Secretária secretáriaIDNew = pagamento.getSecretáriaID();
            List<String> illegalOrphanMessages = null;
            if (cobrancaIDNew != null && !cobrancaIDNew.equals(cobrancaIDOld)) {
                Pagamento oldPagamentoOfCobrancaID = cobrancaIDNew.getPagamento();
                if (oldPagamentoOfCobrancaID != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Cobranca " + cobrancaIDNew + " already has an item of type Pagamento whose cobrancaID column cannot be null. Please make another selection for the cobrancaID field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (alunoIDNew != null) {
                alunoIDNew = em.getReference(alunoIDNew.getClass(), alunoIDNew.getAlunoID());
                pagamento.setAlunoID(alunoIDNew);
            }
            if (cobrancaIDNew != null) {
                cobrancaIDNew = em.getReference(cobrancaIDNew.getClass(), cobrancaIDNew.getCobrancaID());
                pagamento.setCobrancaID(cobrancaIDNew);
            }
            if (matriculaIDNew != null) {
                matriculaIDNew = em.getReference(matriculaIDNew.getClass(), matriculaIDNew.getMatriculaID());
                pagamento.setMatriculaID(matriculaIDNew);
            }
            if (secretáriaIDNew != null) {
                secretáriaIDNew = em.getReference(secretáriaIDNew.getClass(), secretáriaIDNew.getSecretáriaID());
                pagamento.setSecretáriaID(secretáriaIDNew);
            }
            pagamento = em.merge(pagamento);
            if (alunoIDOld != null && !alunoIDOld.equals(alunoIDNew)) {
                alunoIDOld.getPagamentoList().remove(pagamento);
                alunoIDOld = em.merge(alunoIDOld);
            }
            if (alunoIDNew != null && !alunoIDNew.equals(alunoIDOld)) {
                alunoIDNew.getPagamentoList().add(pagamento);
                alunoIDNew = em.merge(alunoIDNew);
            }
            if (cobrancaIDOld != null && !cobrancaIDOld.equals(cobrancaIDNew)) {
                cobrancaIDOld.setPagamento(null);
                cobrancaIDOld = em.merge(cobrancaIDOld);
            }
            if (cobrancaIDNew != null && !cobrancaIDNew.equals(cobrancaIDOld)) {
                cobrancaIDNew.setPagamento(pagamento);
                cobrancaIDNew = em.merge(cobrancaIDNew);
            }
            if (matriculaIDOld != null && !matriculaIDOld.equals(matriculaIDNew)) {
                matriculaIDOld.getPagamentoList().remove(pagamento);
                matriculaIDOld = em.merge(matriculaIDOld);
            }
            if (matriculaIDNew != null && !matriculaIDNew.equals(matriculaIDOld)) {
                matriculaIDNew.getPagamentoList().add(pagamento);
                matriculaIDNew = em.merge(matriculaIDNew);
            }
            if (secretáriaIDOld != null && !secretáriaIDOld.equals(secretáriaIDNew)) {
                secretáriaIDOld.getPagamentoList().remove(pagamento);
                secretáriaIDOld = em.merge(secretáriaIDOld);
            }
            if (secretáriaIDNew != null && !secretáriaIDNew.equals(secretáriaIDOld)) {
                secretáriaIDNew.getPagamentoList().add(pagamento);
                secretáriaIDNew = em.merge(secretáriaIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagamento.getPagamentoID();
                if (findPagamento(id) == null) {
                    throw new NonexistentEntityException("The pagamento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagamento pagamento;
            try {
                pagamento = em.getReference(Pagamento.class, id);
                pagamento.getPagamentoID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagamento with id " + id + " no longer exists.", enfe);
            }
            Aluno alunoID = pagamento.getAlunoID();
            if (alunoID != null) {
                alunoID.getPagamentoList().remove(pagamento);
                alunoID = em.merge(alunoID);
            }
            Cobranca cobrancaID = pagamento.getCobrancaID();
            if (cobrancaID != null) {
                cobrancaID.setPagamento(null);
                cobrancaID = em.merge(cobrancaID);
            }
            Matricula matriculaID = pagamento.getMatriculaID();
            if (matriculaID != null) {
                matriculaID.getPagamentoList().remove(pagamento);
                matriculaID = em.merge(matriculaID);
            }
            Secretária secretáriaID = pagamento.getSecretáriaID();
            if (secretáriaID != null) {
                secretáriaID.getPagamentoList().remove(pagamento);
                secretáriaID = em.merge(secretáriaID);
            }
            em.remove(pagamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagamento> findPagamentoEntities() {
        return findPagamentoEntities(true, -1, -1);
    }

    public List<Pagamento> findPagamentoEntities(int maxResults, int firstResult) {
        return findPagamentoEntities(false, maxResults, firstResult);
    }

    private List<Pagamento> findPagamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagamento.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pagamento findPagamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagamento> rt = cq.from(Pagamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
