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
import modelo.Pagamento;
import modelo.Matricula;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Cobranca;

/**
 *
 * @author mahomed
 */
public class CobrancaJpaController implements Serializable {

    public CobrancaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cobranca cobranca) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagamento pagamento = cobranca.getPagamento();
            if (pagamento != null) {
                pagamento = em.getReference(pagamento.getClass(), pagamento.getPagamentoID());
                cobranca.setPagamento(pagamento);
            }
            Matricula matriculaID = cobranca.getMatriculaID();
            if (matriculaID != null) {
                matriculaID = em.getReference(matriculaID.getClass(), matriculaID.getMatriculaID());
                cobranca.setMatriculaID(matriculaID);
            }
            em.persist(cobranca);
            if (pagamento != null) {
                Cobranca oldCobrancaIDOfPagamento = pagamento.getCobrancaID();
                if (oldCobrancaIDOfPagamento != null) {
                    oldCobrancaIDOfPagamento.setPagamento(null);
                    oldCobrancaIDOfPagamento = em.merge(oldCobrancaIDOfPagamento);
                }
                pagamento.setCobrancaID(cobranca);
                pagamento = em.merge(pagamento);
            }
            if (matriculaID != null) {
                matriculaID.getCobrancaList().add(cobranca);
                matriculaID = em.merge(matriculaID);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cobranca cobranca) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cobranca persistentCobranca = em.find(Cobranca.class, cobranca.getCobrancaID());
            Pagamento pagamentoOld = persistentCobranca.getPagamento();
            Pagamento pagamentoNew = cobranca.getPagamento();
            Matricula matriculaIDOld = persistentCobranca.getMatriculaID();
            Matricula matriculaIDNew = cobranca.getMatriculaID();
            List<String> illegalOrphanMessages = null;
            if (pagamentoOld != null && !pagamentoOld.equals(pagamentoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Pagamento " + pagamentoOld + " since its cobrancaID field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pagamentoNew != null) {
                pagamentoNew = em.getReference(pagamentoNew.getClass(), pagamentoNew.getPagamentoID());
                cobranca.setPagamento(pagamentoNew);
            }
            if (matriculaIDNew != null) {
                matriculaIDNew = em.getReference(matriculaIDNew.getClass(), matriculaIDNew.getMatriculaID());
                cobranca.setMatriculaID(matriculaIDNew);
            }
            cobranca = em.merge(cobranca);
            if (pagamentoNew != null && !pagamentoNew.equals(pagamentoOld)) {
                Cobranca oldCobrancaIDOfPagamento = pagamentoNew.getCobrancaID();
                if (oldCobrancaIDOfPagamento != null) {
                    oldCobrancaIDOfPagamento.setPagamento(null);
                    oldCobrancaIDOfPagamento = em.merge(oldCobrancaIDOfPagamento);
                }
                pagamentoNew.setCobrancaID(cobranca);
                pagamentoNew = em.merge(pagamentoNew);
            }
            if (matriculaIDOld != null && !matriculaIDOld.equals(matriculaIDNew)) {
                matriculaIDOld.getCobrancaList().remove(cobranca);
                matriculaIDOld = em.merge(matriculaIDOld);
            }
            if (matriculaIDNew != null && !matriculaIDNew.equals(matriculaIDOld)) {
                matriculaIDNew.getCobrancaList().add(cobranca);
                matriculaIDNew = em.merge(matriculaIDNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cobranca.getCobrancaID();
                if (findCobranca(id) == null) {
                    throw new NonexistentEntityException("The cobranca with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cobranca cobranca;
            try {
                cobranca = em.getReference(Cobranca.class, id);
                cobranca.getCobrancaID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cobranca with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Pagamento pagamentoOrphanCheck = cobranca.getPagamento();
            if (pagamentoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cobranca (" + cobranca + ") cannot be destroyed since the Pagamento " + pagamentoOrphanCheck + " in its pagamento field has a non-nullable cobrancaID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Matricula matriculaID = cobranca.getMatriculaID();
            if (matriculaID != null) {
                matriculaID.getCobrancaList().remove(cobranca);
                matriculaID = em.merge(matriculaID);
            }
            em.remove(cobranca);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cobranca> findCobrancaEntities() {
        return findCobrancaEntities(true, -1, -1);
    }

    public List<Cobranca> findCobrancaEntities(int maxResults, int firstResult) {
        return findCobrancaEntities(false, maxResults, firstResult);
    }

    private List<Cobranca> findCobrancaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cobranca.class));
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

    public Cobranca findCobranca(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cobranca.class, id);
        } finally {
            em.close();
        }
    }

    public int getCobrancaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cobranca> rt = cq.from(Cobranca.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
