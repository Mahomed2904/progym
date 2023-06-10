/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import modelo.AssociaçãoAdministrativa;

/**
 *
 * @author mahomed
 */
public class AssociaçãoAdministrativaJpaController implements Serializable {

    public AssociaçãoAdministrativaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AssociaçãoAdministrativa associaçãoAdministrativa) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(associaçãoAdministrativa);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AssociaçãoAdministrativa associaçãoAdministrativa) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            associaçãoAdministrativa = em.merge(associaçãoAdministrativa);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = associaçãoAdministrativa.getAssociaçãoAdministrativaID();
                if (findAssociaçãoAdministrativa(id) == null) {
                    throw new NonexistentEntityException("The associa\u00e7\u00e3oAdministrativa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            AssociaçãoAdministrativa associaçãoAdministrativa;
            try {
                associaçãoAdministrativa = em.getReference(AssociaçãoAdministrativa.class, id);
                associaçãoAdministrativa.getAssociaçãoAdministrativaID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The associa\u00e7\u00e3oAdministrativa with id " + id + " no longer exists.", enfe);
            }
            em.remove(associaçãoAdministrativa);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<AssociaçãoAdministrativa> findAssociaçãoAdministrativaEntities() {
        return findAssociaçãoAdministrativaEntities(true, -1, -1);
    }

    public List<AssociaçãoAdministrativa> findAssociaçãoAdministrativaEntities(int maxResults, int firstResult) {
        return findAssociaçãoAdministrativaEntities(false, maxResults, firstResult);
    }

    private List<AssociaçãoAdministrativa> findAssociaçãoAdministrativaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(AssociaçãoAdministrativa.class));
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

    public AssociaçãoAdministrativa findAssociaçãoAdministrativa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(AssociaçãoAdministrativa.class, id);
        } finally {
            em.close();
        }
    }

    public int getAssociaçãoAdministrativaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<AssociaçãoAdministrativa> rt = cq.from(AssociaçãoAdministrativa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
