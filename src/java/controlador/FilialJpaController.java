/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import controlador.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.CoordenaçãoDesportiva;
import modelo.GestorDeFilial;
import modelo.RelatorioDiário;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;
import modelo.Atividade;
import modelo.Filial;

/**
 *
 * @author mahomed
 */
public class FilialJpaController implements Serializable {

    public FilialJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Filial filial) throws RollbackFailureException, Exception {
        if (filial.getRelatorioDiárioList() == null) {
            filial.setRelatorioDiárioList(new ArrayList<RelatorioDiário>());
        }
        if (filial.getAtividadeList() == null) {
            filial.setAtividadeList(new ArrayList<Atividade>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CoordenaçãoDesportiva coordenaçãoDesportivaID = filial.getCoordenaçãoDesportivaID();
            if (coordenaçãoDesportivaID != null) {
                coordenaçãoDesportivaID = em.getReference(coordenaçãoDesportivaID.getClass(), coordenaçãoDesportivaID.getCoordenaçãoDesportivaID());
                filial.setCoordenaçãoDesportivaID(coordenaçãoDesportivaID);
            }
            GestorDeFilial gestorDeFilialID = filial.getGestorDeFilialID();
            if (gestorDeFilialID != null) {
                gestorDeFilialID = em.getReference(gestorDeFilialID.getClass(), gestorDeFilialID.getGestorDeFilialID());
                filial.setGestorDeFilialID(gestorDeFilialID);
            }
            List<RelatorioDiário> attachedRelatorioDiárioList = new ArrayList<RelatorioDiário>();
            for (RelatorioDiário relatorioDiárioListRelatorioDiárioToAttach : filial.getRelatorioDiárioList()) {
                relatorioDiárioListRelatorioDiárioToAttach = em.getReference(relatorioDiárioListRelatorioDiárioToAttach.getClass(), relatorioDiárioListRelatorioDiárioToAttach.getRelatorioDiárioID());
                attachedRelatorioDiárioList.add(relatorioDiárioListRelatorioDiárioToAttach);
            }
            filial.setRelatorioDiárioList(attachedRelatorioDiárioList);
            List<Atividade> attachedAtividadeList = new ArrayList<Atividade>();
            for (Atividade atividadeListAtividadeToAttach : filial.getAtividadeList()) {
                atividadeListAtividadeToAttach = em.getReference(atividadeListAtividadeToAttach.getClass(), atividadeListAtividadeToAttach.getAtividadeID());
                attachedAtividadeList.add(atividadeListAtividadeToAttach);
            }
            filial.setAtividadeList(attachedAtividadeList);
            em.persist(filial);
            if (coordenaçãoDesportivaID != null) {
                coordenaçãoDesportivaID.getFilialList().add(filial);
                coordenaçãoDesportivaID = em.merge(coordenaçãoDesportivaID);
            }
            if (gestorDeFilialID != null) {
                gestorDeFilialID.getFilialList().add(filial);
                gestorDeFilialID = em.merge(gestorDeFilialID);
            }
            for (RelatorioDiário relatorioDiárioListRelatorioDiário : filial.getRelatorioDiárioList()) {
                Filial oldOrigemOfRelatorioDiárioListRelatorioDiário = relatorioDiárioListRelatorioDiário.getOrigem();
                relatorioDiárioListRelatorioDiário.setOrigem(filial);
                relatorioDiárioListRelatorioDiário = em.merge(relatorioDiárioListRelatorioDiário);
                if (oldOrigemOfRelatorioDiárioListRelatorioDiário != null) {
                    oldOrigemOfRelatorioDiárioListRelatorioDiário.getRelatorioDiárioList().remove(relatorioDiárioListRelatorioDiário);
                    oldOrigemOfRelatorioDiárioListRelatorioDiário = em.merge(oldOrigemOfRelatorioDiárioListRelatorioDiário);
                }
            }
            for (Atividade atividadeListAtividade : filial.getAtividadeList()) {
                Filial oldFilialIDOfAtividadeListAtividade = atividadeListAtividade.getFilialID();
                atividadeListAtividade.setFilialID(filial);
                atividadeListAtividade = em.merge(atividadeListAtividade);
                if (oldFilialIDOfAtividadeListAtividade != null) {
                    oldFilialIDOfAtividadeListAtividade.getAtividadeList().remove(atividadeListAtividade);
                    oldFilialIDOfAtividadeListAtividade = em.merge(oldFilialIDOfAtividadeListAtividade);
                }
            }
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

    public void edit(Filial filial) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Filial persistentFilial = em.find(Filial.class, filial.getFilialID());
            CoordenaçãoDesportiva coordenaçãoDesportivaIDOld = persistentFilial.getCoordenaçãoDesportivaID();
            CoordenaçãoDesportiva coordenaçãoDesportivaIDNew = filial.getCoordenaçãoDesportivaID();
            GestorDeFilial gestorDeFilialIDOld = persistentFilial.getGestorDeFilialID();
            GestorDeFilial gestorDeFilialIDNew = filial.getGestorDeFilialID();
            List<RelatorioDiário> relatorioDiárioListOld = persistentFilial.getRelatorioDiárioList();
            List<RelatorioDiário> relatorioDiárioListNew = filial.getRelatorioDiárioList();
            List<Atividade> atividadeListOld = persistentFilial.getAtividadeList();
            List<Atividade> atividadeListNew = filial.getAtividadeList();
            List<String> illegalOrphanMessages = null;
            for (Atividade atividadeListOldAtividade : atividadeListOld) {
                if (!atividadeListNew.contains(atividadeListOldAtividade)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Atividade " + atividadeListOldAtividade + " since its filialID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (coordenaçãoDesportivaIDNew != null) {
                coordenaçãoDesportivaIDNew = em.getReference(coordenaçãoDesportivaIDNew.getClass(), coordenaçãoDesportivaIDNew.getCoordenaçãoDesportivaID());
                filial.setCoordenaçãoDesportivaID(coordenaçãoDesportivaIDNew);
            }
            if (gestorDeFilialIDNew != null) {
                gestorDeFilialIDNew = em.getReference(gestorDeFilialIDNew.getClass(), gestorDeFilialIDNew.getGestorDeFilialID());
                filial.setGestorDeFilialID(gestorDeFilialIDNew);
            }
            List<RelatorioDiário> attachedRelatorioDiárioListNew = new ArrayList<RelatorioDiário>();
            for (RelatorioDiário relatorioDiárioListNewRelatorioDiárioToAttach : relatorioDiárioListNew) {
                relatorioDiárioListNewRelatorioDiárioToAttach = em.getReference(relatorioDiárioListNewRelatorioDiárioToAttach.getClass(), relatorioDiárioListNewRelatorioDiárioToAttach.getRelatorioDiárioID());
                attachedRelatorioDiárioListNew.add(relatorioDiárioListNewRelatorioDiárioToAttach);
            }
            relatorioDiárioListNew = attachedRelatorioDiárioListNew;
            filial.setRelatorioDiárioList(relatorioDiárioListNew);
            List<Atividade> attachedAtividadeListNew = new ArrayList<Atividade>();
            for (Atividade atividadeListNewAtividadeToAttach : atividadeListNew) {
                atividadeListNewAtividadeToAttach = em.getReference(atividadeListNewAtividadeToAttach.getClass(), atividadeListNewAtividadeToAttach.getAtividadeID());
                attachedAtividadeListNew.add(atividadeListNewAtividadeToAttach);
            }
            atividadeListNew = attachedAtividadeListNew;
            filial.setAtividadeList(atividadeListNew);
            filial = em.merge(filial);
            if (coordenaçãoDesportivaIDOld != null && !coordenaçãoDesportivaIDOld.equals(coordenaçãoDesportivaIDNew)) {
                coordenaçãoDesportivaIDOld.getFilialList().remove(filial);
                coordenaçãoDesportivaIDOld = em.merge(coordenaçãoDesportivaIDOld);
            }
            if (coordenaçãoDesportivaIDNew != null && !coordenaçãoDesportivaIDNew.equals(coordenaçãoDesportivaIDOld)) {
                coordenaçãoDesportivaIDNew.getFilialList().add(filial);
                coordenaçãoDesportivaIDNew = em.merge(coordenaçãoDesportivaIDNew);
            }
            if (gestorDeFilialIDOld != null && !gestorDeFilialIDOld.equals(gestorDeFilialIDNew)) {
                gestorDeFilialIDOld.getFilialList().remove(filial);
                gestorDeFilialIDOld = em.merge(gestorDeFilialIDOld);
            }
            if (gestorDeFilialIDNew != null && !gestorDeFilialIDNew.equals(gestorDeFilialIDOld)) {
                gestorDeFilialIDNew.getFilialList().add(filial);
                gestorDeFilialIDNew = em.merge(gestorDeFilialIDNew);
            }
            for (RelatorioDiário relatorioDiárioListOldRelatorioDiário : relatorioDiárioListOld) {
                if (!relatorioDiárioListNew.contains(relatorioDiárioListOldRelatorioDiário)) {
                    relatorioDiárioListOldRelatorioDiário.setOrigem(null);
                    relatorioDiárioListOldRelatorioDiário = em.merge(relatorioDiárioListOldRelatorioDiário);
                }
            }
            for (RelatorioDiário relatorioDiárioListNewRelatorioDiário : relatorioDiárioListNew) {
                if (!relatorioDiárioListOld.contains(relatorioDiárioListNewRelatorioDiário)) {
                    Filial oldOrigemOfRelatorioDiárioListNewRelatorioDiário = relatorioDiárioListNewRelatorioDiário.getOrigem();
                    relatorioDiárioListNewRelatorioDiário.setOrigem(filial);
                    relatorioDiárioListNewRelatorioDiário = em.merge(relatorioDiárioListNewRelatorioDiário);
                    if (oldOrigemOfRelatorioDiárioListNewRelatorioDiário != null && !oldOrigemOfRelatorioDiárioListNewRelatorioDiário.equals(filial)) {
                        oldOrigemOfRelatorioDiárioListNewRelatorioDiário.getRelatorioDiárioList().remove(relatorioDiárioListNewRelatorioDiário);
                        oldOrigemOfRelatorioDiárioListNewRelatorioDiário = em.merge(oldOrigemOfRelatorioDiárioListNewRelatorioDiário);
                    }
                }
            }
            for (Atividade atividadeListNewAtividade : atividadeListNew) {
                if (!atividadeListOld.contains(atividadeListNewAtividade)) {
                    Filial oldFilialIDOfAtividadeListNewAtividade = atividadeListNewAtividade.getFilialID();
                    atividadeListNewAtividade.setFilialID(filial);
                    atividadeListNewAtividade = em.merge(atividadeListNewAtividade);
                    if (oldFilialIDOfAtividadeListNewAtividade != null && !oldFilialIDOfAtividadeListNewAtividade.equals(filial)) {
                        oldFilialIDOfAtividadeListNewAtividade.getAtividadeList().remove(atividadeListNewAtividade);
                        oldFilialIDOfAtividadeListNewAtividade = em.merge(oldFilialIDOfAtividadeListNewAtividade);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = filial.getFilialID();
                if (findFilial(id) == null) {
                    throw new NonexistentEntityException("The filial with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Filial filial;
            try {
                filial = em.getReference(Filial.class, id);
                filial.getFilialID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The filial with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Atividade> atividadeListOrphanCheck = filial.getAtividadeList();
            for (Atividade atividadeListOrphanCheckAtividade : atividadeListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Filial (" + filial + ") cannot be destroyed since the Atividade " + atividadeListOrphanCheckAtividade + " in its atividadeList field has a non-nullable filialID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            CoordenaçãoDesportiva coordenaçãoDesportivaID = filial.getCoordenaçãoDesportivaID();
            if (coordenaçãoDesportivaID != null) {
                coordenaçãoDesportivaID.getFilialList().remove(filial);
                coordenaçãoDesportivaID = em.merge(coordenaçãoDesportivaID);
            }
            GestorDeFilial gestorDeFilialID = filial.getGestorDeFilialID();
            if (gestorDeFilialID != null) {
                gestorDeFilialID.getFilialList().remove(filial);
                gestorDeFilialID = em.merge(gestorDeFilialID);
            }
            List<RelatorioDiário> relatorioDiárioList = filial.getRelatorioDiárioList();
            for (RelatorioDiário relatorioDiárioListRelatorioDiário : relatorioDiárioList) {
                relatorioDiárioListRelatorioDiário.setOrigem(null);
                relatorioDiárioListRelatorioDiário = em.merge(relatorioDiárioListRelatorioDiário);
            }
            em.remove(filial);
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

    public List<Filial> findFilialEntities() {
        return findFilialEntities(true, -1, -1);
    }

    public List<Filial> findFilialEntities(int maxResults, int firstResult) {
        return findFilialEntities(false, maxResults, firstResult);
    }

    private List<Filial> findFilialEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Filial.class));
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

    public Filial findFilial(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Filial.class, id);
        } finally {
            em.close();
        }
    }

    public int getFilialCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Filial> rt = cq.from(Filial.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
