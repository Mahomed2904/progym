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
import modelo.Filial;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Atividade;
import modelo.Contrato;
import modelo.CoordenaçãoDesportiva;

/**
 *
 * @author mahomed
 */
public class CoordenaçãoDesportivaJpaController implements Serializable {

    public CoordenaçãoDesportivaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CoordenaçãoDesportiva coordenaçãoDesportiva) {
        if (coordenaçãoDesportiva.getFilialList() == null) {
            coordenaçãoDesportiva.setFilialList(new ArrayList<Filial>());
        }
        if (coordenaçãoDesportiva.getAtividadeList() == null) {
            coordenaçãoDesportiva.setAtividadeList(new ArrayList<Atividade>());
        }
        if (coordenaçãoDesportiva.getContratoList() == null) {
            coordenaçãoDesportiva.setContratoList(new ArrayList<Contrato>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Filial> attachedFilialList = new ArrayList<Filial>();
            for (Filial filialListFilialToAttach : coordenaçãoDesportiva.getFilialList()) {
                filialListFilialToAttach = em.getReference(filialListFilialToAttach.getClass(), filialListFilialToAttach.getFilialID());
                attachedFilialList.add(filialListFilialToAttach);
            }
            coordenaçãoDesportiva.setFilialList(attachedFilialList);
            List<Atividade> attachedAtividadeList = new ArrayList<Atividade>();
            for (Atividade atividadeListAtividadeToAttach : coordenaçãoDesportiva.getAtividadeList()) {
                atividadeListAtividadeToAttach = em.getReference(atividadeListAtividadeToAttach.getClass(), atividadeListAtividadeToAttach.getAtividadeID());
                attachedAtividadeList.add(atividadeListAtividadeToAttach);
            }
            coordenaçãoDesportiva.setAtividadeList(attachedAtividadeList);
            List<Contrato> attachedContratoList = new ArrayList<Contrato>();
            for (Contrato contratoListContratoToAttach : coordenaçãoDesportiva.getContratoList()) {
                contratoListContratoToAttach = em.getReference(contratoListContratoToAttach.getClass(), contratoListContratoToAttach.getContratoID());
                attachedContratoList.add(contratoListContratoToAttach);
            }
            coordenaçãoDesportiva.setContratoList(attachedContratoList);
            em.persist(coordenaçãoDesportiva);
            for (Filial filialListFilial : coordenaçãoDesportiva.getFilialList()) {
                CoordenaçãoDesportiva oldCoordenaçãoDesportivaIDOfFilialListFilial = filialListFilial.getCoordenaçãoDesportivaID();
                filialListFilial.setCoordenaçãoDesportivaID(coordenaçãoDesportiva);
                filialListFilial = em.merge(filialListFilial);
                if (oldCoordenaçãoDesportivaIDOfFilialListFilial != null) {
                    oldCoordenaçãoDesportivaIDOfFilialListFilial.getFilialList().remove(filialListFilial);
                    oldCoordenaçãoDesportivaIDOfFilialListFilial = em.merge(oldCoordenaçãoDesportivaIDOfFilialListFilial);
                }
            }
            for (Atividade atividadeListAtividade : coordenaçãoDesportiva.getAtividadeList()) {
                CoordenaçãoDesportiva oldCoordenaçãoDesportivaIDOfAtividadeListAtividade = atividadeListAtividade.getCoordenaçãoDesportivaID();
                atividadeListAtividade.setCoordenaçãoDesportivaID(coordenaçãoDesportiva);
                atividadeListAtividade = em.merge(atividadeListAtividade);
                if (oldCoordenaçãoDesportivaIDOfAtividadeListAtividade != null) {
                    oldCoordenaçãoDesportivaIDOfAtividadeListAtividade.getAtividadeList().remove(atividadeListAtividade);
                    oldCoordenaçãoDesportivaIDOfAtividadeListAtividade = em.merge(oldCoordenaçãoDesportivaIDOfAtividadeListAtividade);
                }
            }
            for (Contrato contratoListContrato : coordenaçãoDesportiva.getContratoList()) {
                CoordenaçãoDesportiva oldCoordenaçãoDesportivaIDOfContratoListContrato = contratoListContrato.getCoordenaçãoDesportivaID();
                contratoListContrato.setCoordenaçãoDesportivaID(coordenaçãoDesportiva);
                contratoListContrato = em.merge(contratoListContrato);
                if (oldCoordenaçãoDesportivaIDOfContratoListContrato != null) {
                    oldCoordenaçãoDesportivaIDOfContratoListContrato.getContratoList().remove(contratoListContrato);
                    oldCoordenaçãoDesportivaIDOfContratoListContrato = em.merge(oldCoordenaçãoDesportivaIDOfContratoListContrato);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CoordenaçãoDesportiva coordenaçãoDesportiva) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CoordenaçãoDesportiva persistentCoordenaçãoDesportiva = em.find(CoordenaçãoDesportiva.class, coordenaçãoDesportiva.getCoordenaçãoDesportivaID());
            List<Filial> filialListOld = persistentCoordenaçãoDesportiva.getFilialList();
            List<Filial> filialListNew = coordenaçãoDesportiva.getFilialList();
            List<Atividade> atividadeListOld = persistentCoordenaçãoDesportiva.getAtividadeList();
            List<Atividade> atividadeListNew = coordenaçãoDesportiva.getAtividadeList();
            List<Contrato> contratoListOld = persistentCoordenaçãoDesportiva.getContratoList();
            List<Contrato> contratoListNew = coordenaçãoDesportiva.getContratoList();
            List<String> illegalOrphanMessages = null;
            for (Filial filialListOldFilial : filialListOld) {
                if (!filialListNew.contains(filialListOldFilial)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Filial " + filialListOldFilial + " since its coordena\u00e7\u00e3oDesportivaID field is not nullable.");
                }
            }
            for (Atividade atividadeListOldAtividade : atividadeListOld) {
                if (!atividadeListNew.contains(atividadeListOldAtividade)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Atividade " + atividadeListOldAtividade + " since its coordena\u00e7\u00e3oDesportivaID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Filial> attachedFilialListNew = new ArrayList<Filial>();
            for (Filial filialListNewFilialToAttach : filialListNew) {
                filialListNewFilialToAttach = em.getReference(filialListNewFilialToAttach.getClass(), filialListNewFilialToAttach.getFilialID());
                attachedFilialListNew.add(filialListNewFilialToAttach);
            }
            filialListNew = attachedFilialListNew;
            coordenaçãoDesportiva.setFilialList(filialListNew);
            List<Atividade> attachedAtividadeListNew = new ArrayList<Atividade>();
            for (Atividade atividadeListNewAtividadeToAttach : atividadeListNew) {
                atividadeListNewAtividadeToAttach = em.getReference(atividadeListNewAtividadeToAttach.getClass(), atividadeListNewAtividadeToAttach.getAtividadeID());
                attachedAtividadeListNew.add(atividadeListNewAtividadeToAttach);
            }
            atividadeListNew = attachedAtividadeListNew;
            coordenaçãoDesportiva.setAtividadeList(atividadeListNew);
            List<Contrato> attachedContratoListNew = new ArrayList<Contrato>();
            for (Contrato contratoListNewContratoToAttach : contratoListNew) {
                contratoListNewContratoToAttach = em.getReference(contratoListNewContratoToAttach.getClass(), contratoListNewContratoToAttach.getContratoID());
                attachedContratoListNew.add(contratoListNewContratoToAttach);
            }
            contratoListNew = attachedContratoListNew;
            coordenaçãoDesportiva.setContratoList(contratoListNew);
            coordenaçãoDesportiva = em.merge(coordenaçãoDesportiva);
            for (Filial filialListNewFilial : filialListNew) {
                if (!filialListOld.contains(filialListNewFilial)) {
                    CoordenaçãoDesportiva oldCoordenaçãoDesportivaIDOfFilialListNewFilial = filialListNewFilial.getCoordenaçãoDesportivaID();
                    filialListNewFilial.setCoordenaçãoDesportivaID(coordenaçãoDesportiva);
                    filialListNewFilial = em.merge(filialListNewFilial);
                    if (oldCoordenaçãoDesportivaIDOfFilialListNewFilial != null && !oldCoordenaçãoDesportivaIDOfFilialListNewFilial.equals(coordenaçãoDesportiva)) {
                        oldCoordenaçãoDesportivaIDOfFilialListNewFilial.getFilialList().remove(filialListNewFilial);
                        oldCoordenaçãoDesportivaIDOfFilialListNewFilial = em.merge(oldCoordenaçãoDesportivaIDOfFilialListNewFilial);
                    }
                }
            }
            for (Atividade atividadeListNewAtividade : atividadeListNew) {
                if (!atividadeListOld.contains(atividadeListNewAtividade)) {
                    CoordenaçãoDesportiva oldCoordenaçãoDesportivaIDOfAtividadeListNewAtividade = atividadeListNewAtividade.getCoordenaçãoDesportivaID();
                    atividadeListNewAtividade.setCoordenaçãoDesportivaID(coordenaçãoDesportiva);
                    atividadeListNewAtividade = em.merge(atividadeListNewAtividade);
                    if (oldCoordenaçãoDesportivaIDOfAtividadeListNewAtividade != null && !oldCoordenaçãoDesportivaIDOfAtividadeListNewAtividade.equals(coordenaçãoDesportiva)) {
                        oldCoordenaçãoDesportivaIDOfAtividadeListNewAtividade.getAtividadeList().remove(atividadeListNewAtividade);
                        oldCoordenaçãoDesportivaIDOfAtividadeListNewAtividade = em.merge(oldCoordenaçãoDesportivaIDOfAtividadeListNewAtividade);
                    }
                }
            }
            for (Contrato contratoListOldContrato : contratoListOld) {
                if (!contratoListNew.contains(contratoListOldContrato)) {
                    contratoListOldContrato.setCoordenaçãoDesportivaID(null);
                    contratoListOldContrato = em.merge(contratoListOldContrato);
                }
            }
            for (Contrato contratoListNewContrato : contratoListNew) {
                if (!contratoListOld.contains(contratoListNewContrato)) {
                    CoordenaçãoDesportiva oldCoordenaçãoDesportivaIDOfContratoListNewContrato = contratoListNewContrato.getCoordenaçãoDesportivaID();
                    contratoListNewContrato.setCoordenaçãoDesportivaID(coordenaçãoDesportiva);
                    contratoListNewContrato = em.merge(contratoListNewContrato);
                    if (oldCoordenaçãoDesportivaIDOfContratoListNewContrato != null && !oldCoordenaçãoDesportivaIDOfContratoListNewContrato.equals(coordenaçãoDesportiva)) {
                        oldCoordenaçãoDesportivaIDOfContratoListNewContrato.getContratoList().remove(contratoListNewContrato);
                        oldCoordenaçãoDesportivaIDOfContratoListNewContrato = em.merge(oldCoordenaçãoDesportivaIDOfContratoListNewContrato);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = coordenaçãoDesportiva.getCoordenaçãoDesportivaID();
                if (findCoordenaçãoDesportiva(id) == null) {
                    throw new NonexistentEntityException("The coordena\u00e7\u00e3oDesportiva with id " + id + " no longer exists.");
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
            CoordenaçãoDesportiva coordenaçãoDesportiva;
            try {
                coordenaçãoDesportiva = em.getReference(CoordenaçãoDesportiva.class, id);
                coordenaçãoDesportiva.getCoordenaçãoDesportivaID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The coordena\u00e7\u00e3oDesportiva with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Filial> filialListOrphanCheck = coordenaçãoDesportiva.getFilialList();
            for (Filial filialListOrphanCheckFilial : filialListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Coordena\u00e7\u00e3oDesportiva (" + coordenaçãoDesportiva + ") cannot be destroyed since the Filial " + filialListOrphanCheckFilial + " in its filialList field has a non-nullable coordena\u00e7\u00e3oDesportivaID field.");
            }
            List<Atividade> atividadeListOrphanCheck = coordenaçãoDesportiva.getAtividadeList();
            for (Atividade atividadeListOrphanCheckAtividade : atividadeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Coordena\u00e7\u00e3oDesportiva (" + coordenaçãoDesportiva + ") cannot be destroyed since the Atividade " + atividadeListOrphanCheckAtividade + " in its atividadeList field has a non-nullable coordena\u00e7\u00e3oDesportivaID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Contrato> contratoList = coordenaçãoDesportiva.getContratoList();
            for (Contrato contratoListContrato : contratoList) {
                contratoListContrato.setCoordenaçãoDesportivaID(null);
                contratoListContrato = em.merge(contratoListContrato);
            }
            em.remove(coordenaçãoDesportiva);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CoordenaçãoDesportiva> findCoordenaçãoDesportivaEntities() {
        return findCoordenaçãoDesportivaEntities(true, -1, -1);
    }

    public List<CoordenaçãoDesportiva> findCoordenaçãoDesportivaEntities(int maxResults, int firstResult) {
        return findCoordenaçãoDesportivaEntities(false, maxResults, firstResult);
    }

    private List<CoordenaçãoDesportiva> findCoordenaçãoDesportivaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CoordenaçãoDesportiva.class));
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

    public CoordenaçãoDesportiva findCoordenaçãoDesportiva(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CoordenaçãoDesportiva.class, id);
        } finally {
            em.close();
        }
    }

    public int getCoordenaçãoDesportivaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CoordenaçãoDesportiva> rt = cq.from(CoordenaçãoDesportiva.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
