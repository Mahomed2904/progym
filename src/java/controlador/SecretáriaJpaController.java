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
import modelo.Matricula;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Pagamento;
import modelo.Secretária;

/**
 *
 * @author mahomed
 */
public class SecretáriaJpaController implements Serializable {

    public SecretáriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Secretária secretária) {
        if (secretária.getMatriculaList() == null) {
            secretária.setMatriculaList(new ArrayList<Matricula>());
        }
        if (secretária.getPagamentoList() == null) {
            secretária.setPagamentoList(new ArrayList<Pagamento>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Matricula> attachedMatriculaList = new ArrayList<Matricula>();
            for (Matricula matriculaListMatriculaToAttach : secretária.getMatriculaList()) {
                matriculaListMatriculaToAttach = em.getReference(matriculaListMatriculaToAttach.getClass(), matriculaListMatriculaToAttach.getMatriculaID());
                attachedMatriculaList.add(matriculaListMatriculaToAttach);
            }
            secretária.setMatriculaList(attachedMatriculaList);
            List<Pagamento> attachedPagamentoList = new ArrayList<Pagamento>();
            for (Pagamento pagamentoListPagamentoToAttach : secretária.getPagamentoList()) {
                pagamentoListPagamentoToAttach = em.getReference(pagamentoListPagamentoToAttach.getClass(), pagamentoListPagamentoToAttach.getPagamentoID());
                attachedPagamentoList.add(pagamentoListPagamentoToAttach);
            }
            secretária.setPagamentoList(attachedPagamentoList);
            em.persist(secretária);
            for (Matricula matriculaListMatricula : secretária.getMatriculaList()) {
                Secretária oldSecretáriaIDOfMatriculaListMatricula = matriculaListMatricula.getSecretáriaID();
                matriculaListMatricula.setSecretáriaID(secretária);
                matriculaListMatricula = em.merge(matriculaListMatricula);
                if (oldSecretáriaIDOfMatriculaListMatricula != null) {
                    oldSecretáriaIDOfMatriculaListMatricula.getMatriculaList().remove(matriculaListMatricula);
                    oldSecretáriaIDOfMatriculaListMatricula = em.merge(oldSecretáriaIDOfMatriculaListMatricula);
                }
            }
            for (Pagamento pagamentoListPagamento : secretária.getPagamentoList()) {
                Secretária oldSecretáriaIDOfPagamentoListPagamento = pagamentoListPagamento.getSecretáriaID();
                pagamentoListPagamento.setSecretáriaID(secretária);
                pagamentoListPagamento = em.merge(pagamentoListPagamento);
                if (oldSecretáriaIDOfPagamentoListPagamento != null) {
                    oldSecretáriaIDOfPagamentoListPagamento.getPagamentoList().remove(pagamentoListPagamento);
                    oldSecretáriaIDOfPagamentoListPagamento = em.merge(oldSecretáriaIDOfPagamentoListPagamento);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Secretária secretária) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Secretária persistentSecretária = em.find(Secretária.class, secretária.getSecretáriaID());
            List<Matricula> matriculaListOld = persistentSecretária.getMatriculaList();
            List<Matricula> matriculaListNew = secretária.getMatriculaList();
            List<Pagamento> pagamentoListOld = persistentSecretária.getPagamentoList();
            List<Pagamento> pagamentoListNew = secretária.getPagamentoList();
            List<String> illegalOrphanMessages = null;
            for (Matricula matriculaListOldMatricula : matriculaListOld) {
                if (!matriculaListNew.contains(matriculaListOldMatricula)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Matricula " + matriculaListOldMatricula + " since its secret\u00e1riaID field is not nullable.");
                }
            }
            for (Pagamento pagamentoListOldPagamento : pagamentoListOld) {
                if (!pagamentoListNew.contains(pagamentoListOldPagamento)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Pagamento " + pagamentoListOldPagamento + " since its secret\u00e1riaID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Matricula> attachedMatriculaListNew = new ArrayList<Matricula>();
            for (Matricula matriculaListNewMatriculaToAttach : matriculaListNew) {
                matriculaListNewMatriculaToAttach = em.getReference(matriculaListNewMatriculaToAttach.getClass(), matriculaListNewMatriculaToAttach.getMatriculaID());
                attachedMatriculaListNew.add(matriculaListNewMatriculaToAttach);
            }
            matriculaListNew = attachedMatriculaListNew;
            secretária.setMatriculaList(matriculaListNew);
            List<Pagamento> attachedPagamentoListNew = new ArrayList<Pagamento>();
            for (Pagamento pagamentoListNewPagamentoToAttach : pagamentoListNew) {
                pagamentoListNewPagamentoToAttach = em.getReference(pagamentoListNewPagamentoToAttach.getClass(), pagamentoListNewPagamentoToAttach.getPagamentoID());
                attachedPagamentoListNew.add(pagamentoListNewPagamentoToAttach);
            }
            pagamentoListNew = attachedPagamentoListNew;
            secretária.setPagamentoList(pagamentoListNew);
            secretária = em.merge(secretária);
            for (Matricula matriculaListNewMatricula : matriculaListNew) {
                if (!matriculaListOld.contains(matriculaListNewMatricula)) {
                    Secretária oldSecretáriaIDOfMatriculaListNewMatricula = matriculaListNewMatricula.getSecretáriaID();
                    matriculaListNewMatricula.setSecretáriaID(secretária);
                    matriculaListNewMatricula = em.merge(matriculaListNewMatricula);
                    if (oldSecretáriaIDOfMatriculaListNewMatricula != null && !oldSecretáriaIDOfMatriculaListNewMatricula.equals(secretária)) {
                        oldSecretáriaIDOfMatriculaListNewMatricula.getMatriculaList().remove(matriculaListNewMatricula);
                        oldSecretáriaIDOfMatriculaListNewMatricula = em.merge(oldSecretáriaIDOfMatriculaListNewMatricula);
                    }
                }
            }
            for (Pagamento pagamentoListNewPagamento : pagamentoListNew) {
                if (!pagamentoListOld.contains(pagamentoListNewPagamento)) {
                    Secretária oldSecretáriaIDOfPagamentoListNewPagamento = pagamentoListNewPagamento.getSecretáriaID();
                    pagamentoListNewPagamento.setSecretáriaID(secretária);
                    pagamentoListNewPagamento = em.merge(pagamentoListNewPagamento);
                    if (oldSecretáriaIDOfPagamentoListNewPagamento != null && !oldSecretáriaIDOfPagamentoListNewPagamento.equals(secretária)) {
                        oldSecretáriaIDOfPagamentoListNewPagamento.getPagamentoList().remove(pagamentoListNewPagamento);
                        oldSecretáriaIDOfPagamentoListNewPagamento = em.merge(oldSecretáriaIDOfPagamentoListNewPagamento);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = secretária.getSecretáriaID();
                if (findSecretária(id) == null) {
                    throw new NonexistentEntityException("The secret\u00e1ria with id " + id + " no longer exists.");
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
            Secretária secretária;
            try {
                secretária = em.getReference(Secretária.class, id);
                secretária.getSecretáriaID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The secret\u00e1ria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Matricula> matriculaListOrphanCheck = secretária.getMatriculaList();
            for (Matricula matriculaListOrphanCheckMatricula : matriculaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Secret\u00e1ria (" + secretária + ") cannot be destroyed since the Matricula " + matriculaListOrphanCheckMatricula + " in its matriculaList field has a non-nullable secret\u00e1riaID field.");
            }
            List<Pagamento> pagamentoListOrphanCheck = secretária.getPagamentoList();
            for (Pagamento pagamentoListOrphanCheckPagamento : pagamentoListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Secret\u00e1ria (" + secretária + ") cannot be destroyed since the Pagamento " + pagamentoListOrphanCheckPagamento + " in its pagamentoList field has a non-nullable secret\u00e1riaID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(secretária);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Secretária> findSecretáriaEntities() {
        return findSecretáriaEntities(true, -1, -1);
    }

    public List<Secretária> findSecretáriaEntities(int maxResults, int firstResult) {
        return findSecretáriaEntities(false, maxResults, firstResult);
    }

    private List<Secretária> findSecretáriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Secretária.class));
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

    public Secretária findSecretária(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Secretária.class, id);
        } finally {
            em.close();
        }
    }

    public int getSecretáriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Secretária> rt = cq.from(Secretária.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
