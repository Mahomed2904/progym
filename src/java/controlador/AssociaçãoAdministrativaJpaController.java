/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.AssociaçãoAdministrativa;

/**
 *
 * @author mahomed
 */
public class AssociaçãoAdministrativaJpaController implements Serializable {

    public AssociaçãoAdministrativaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(AssociaçãoAdministrativa associaçãoAdministrativa) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(associaçãoAdministrativa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(AssociaçãoAdministrativa associaçãoAdministrativa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            associaçãoAdministrativa = em.merge(associaçãoAdministrativa);
            em.getTransaction().commit();
        } catch (Exception ex) {
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AssociaçãoAdministrativa associaçãoAdministrativa;
            try {
                associaçãoAdministrativa = em.getReference(AssociaçãoAdministrativa.class, id);
                associaçãoAdministrativa.getAssociaçãoAdministrativaID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The associa\u00e7\u00e3oAdministrativa with id " + id + " no longer exists.", enfe);
            }
            em.remove(associaçãoAdministrativa);
            em.getTransaction().commit();
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
