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
import modelo.Atividade;
import modelo.Secretária;
import modelo.Pagamento;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Cobranca;
import modelo.Matricula;

/**
 *
 * @author mahomed
 */
public class MatriculaJpaController implements Serializable {

    public MatriculaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Matricula matricula) {
        if (matricula.getPagamentoList() == null) {
            matricula.setPagamentoList(new ArrayList<Pagamento>());
        }
        if (matricula.getCobrancaList() == null) {
            matricula.setCobrancaList(new ArrayList<Cobranca>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aluno alunoID = matricula.getAlunoID();
            if (alunoID != null) {
                alunoID = em.getReference(alunoID.getClass(), alunoID.getAlunoID());
                matricula.setAlunoID(alunoID);
            }
            Atividade atividadeID = matricula.getAtividadeID();
            if (atividadeID != null) {
                atividadeID = em.getReference(atividadeID.getClass(), atividadeID.getAtividadeID());
                matricula.setAtividadeID(atividadeID);
            }
            Secretária secretáriaID = matricula.getSecretáriaID();
            if (secretáriaID != null) {
                secretáriaID = em.getReference(secretáriaID.getClass(), secretáriaID.getSecretáriaID());
                matricula.setSecretáriaID(secretáriaID);
            }
            List<Pagamento> attachedPagamentoList = new ArrayList<Pagamento>();
            for (Pagamento pagamentoListPagamentoToAttach : matricula.getPagamentoList()) {
                pagamentoListPagamentoToAttach = em.getReference(pagamentoListPagamentoToAttach.getClass(), pagamentoListPagamentoToAttach.getPagamentoID());
                attachedPagamentoList.add(pagamentoListPagamentoToAttach);
            }
            matricula.setPagamentoList(attachedPagamentoList);
            List<Cobranca> attachedCobrancaList = new ArrayList<Cobranca>();
            for (Cobranca cobrancaListCobrancaToAttach : matricula.getCobrancaList()) {
                cobrancaListCobrancaToAttach = em.getReference(cobrancaListCobrancaToAttach.getClass(), cobrancaListCobrancaToAttach.getCobrancaID());
                attachedCobrancaList.add(cobrancaListCobrancaToAttach);
            }
            matricula.setCobrancaList(attachedCobrancaList);
            em.persist(matricula);
            if (alunoID != null) {
                alunoID.getMatriculaList().add(matricula);
                alunoID = em.merge(alunoID);
            }
            if (atividadeID != null) {
                atividadeID.getMatriculaList().add(matricula);
                atividadeID = em.merge(atividadeID);
            }
            if (secretáriaID != null) {
                secretáriaID.getMatriculaList().add(matricula);
                secretáriaID = em.merge(secretáriaID);
            }
            for (Pagamento pagamentoListPagamento : matricula.getPagamentoList()) {
                Matricula oldMatriculaIDOfPagamentoListPagamento = pagamentoListPagamento.getMatriculaID();
                pagamentoListPagamento.setMatriculaID(matricula);
                pagamentoListPagamento = em.merge(pagamentoListPagamento);
                if (oldMatriculaIDOfPagamentoListPagamento != null) {
                    oldMatriculaIDOfPagamentoListPagamento.getPagamentoList().remove(pagamentoListPagamento);
                    oldMatriculaIDOfPagamentoListPagamento = em.merge(oldMatriculaIDOfPagamentoListPagamento);
                }
            }
            for (Cobranca cobrancaListCobranca : matricula.getCobrancaList()) {
                Matricula oldMatriculaIDOfCobrancaListCobranca = cobrancaListCobranca.getMatriculaID();
                cobrancaListCobranca.setMatriculaID(matricula);
                cobrancaListCobranca = em.merge(cobrancaListCobranca);
                if (oldMatriculaIDOfCobrancaListCobranca != null) {
                    oldMatriculaIDOfCobrancaListCobranca.getCobrancaList().remove(cobrancaListCobranca);
                    oldMatriculaIDOfCobrancaListCobranca = em.merge(oldMatriculaIDOfCobrancaListCobranca);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Matricula matricula) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Matricula persistentMatricula = em.find(Matricula.class, matricula.getMatriculaID());
            Aluno alunoIDOld = persistentMatricula.getAlunoID();
            Aluno alunoIDNew = matricula.getAlunoID();
            Atividade atividadeIDOld = persistentMatricula.getAtividadeID();
            Atividade atividadeIDNew = matricula.getAtividadeID();
            Secretária secretáriaIDOld = persistentMatricula.getSecretáriaID();
            Secretária secretáriaIDNew = matricula.getSecretáriaID();
            List<Pagamento> pagamentoListOld = persistentMatricula.getPagamentoList();
            List<Pagamento> pagamentoListNew = matricula.getPagamentoList();
            List<Cobranca> cobrancaListOld = persistentMatricula.getCobrancaList();
            List<Cobranca> cobrancaListNew = matricula.getCobrancaList();
            List<String> illegalOrphanMessages = null;
            for (Cobranca cobrancaListOldCobranca : cobrancaListOld) {
                if (!cobrancaListNew.contains(cobrancaListOldCobranca)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Cobranca " + cobrancaListOldCobranca + " since its matriculaID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (alunoIDNew != null) {
                alunoIDNew = em.getReference(alunoIDNew.getClass(), alunoIDNew.getAlunoID());
                matricula.setAlunoID(alunoIDNew);
            }
            if (atividadeIDNew != null) {
                atividadeIDNew = em.getReference(atividadeIDNew.getClass(), atividadeIDNew.getAtividadeID());
                matricula.setAtividadeID(atividadeIDNew);
            }
            if (secretáriaIDNew != null) {
                secretáriaIDNew = em.getReference(secretáriaIDNew.getClass(), secretáriaIDNew.getSecretáriaID());
                matricula.setSecretáriaID(secretáriaIDNew);
            }
            List<Pagamento> attachedPagamentoListNew = new ArrayList<Pagamento>();
            for (Pagamento pagamentoListNewPagamentoToAttach : pagamentoListNew) {
                pagamentoListNewPagamentoToAttach = em.getReference(pagamentoListNewPagamentoToAttach.getClass(), pagamentoListNewPagamentoToAttach.getPagamentoID());
                attachedPagamentoListNew.add(pagamentoListNewPagamentoToAttach);
            }
            pagamentoListNew = attachedPagamentoListNew;
            matricula.setPagamentoList(pagamentoListNew);
            List<Cobranca> attachedCobrancaListNew = new ArrayList<Cobranca>();
            for (Cobranca cobrancaListNewCobrancaToAttach : cobrancaListNew) {
                cobrancaListNewCobrancaToAttach = em.getReference(cobrancaListNewCobrancaToAttach.getClass(), cobrancaListNewCobrancaToAttach.getCobrancaID());
                attachedCobrancaListNew.add(cobrancaListNewCobrancaToAttach);
            }
            cobrancaListNew = attachedCobrancaListNew;
            matricula.setCobrancaList(cobrancaListNew);
            matricula = em.merge(matricula);
            if (alunoIDOld != null && !alunoIDOld.equals(alunoIDNew)) {
                alunoIDOld.getMatriculaList().remove(matricula);
                alunoIDOld = em.merge(alunoIDOld);
            }
            if (alunoIDNew != null && !alunoIDNew.equals(alunoIDOld)) {
                alunoIDNew.getMatriculaList().add(matricula);
                alunoIDNew = em.merge(alunoIDNew);
            }
            if (atividadeIDOld != null && !atividadeIDOld.equals(atividadeIDNew)) {
                atividadeIDOld.getMatriculaList().remove(matricula);
                atividadeIDOld = em.merge(atividadeIDOld);
            }
            if (atividadeIDNew != null && !atividadeIDNew.equals(atividadeIDOld)) {
                atividadeIDNew.getMatriculaList().add(matricula);
                atividadeIDNew = em.merge(atividadeIDNew);
            }
            if (secretáriaIDOld != null && !secretáriaIDOld.equals(secretáriaIDNew)) {
                secretáriaIDOld.getMatriculaList().remove(matricula);
                secretáriaIDOld = em.merge(secretáriaIDOld);
            }
            if (secretáriaIDNew != null && !secretáriaIDNew.equals(secretáriaIDOld)) {
                secretáriaIDNew.getMatriculaList().add(matricula);
                secretáriaIDNew = em.merge(secretáriaIDNew);
            }
            for (Pagamento pagamentoListOldPagamento : pagamentoListOld) {
                if (!pagamentoListNew.contains(pagamentoListOldPagamento)) {
                    pagamentoListOldPagamento.setMatriculaID(null);
                    pagamentoListOldPagamento = em.merge(pagamentoListOldPagamento);
                }
            }
            for (Pagamento pagamentoListNewPagamento : pagamentoListNew) {
                if (!pagamentoListOld.contains(pagamentoListNewPagamento)) {
                    Matricula oldMatriculaIDOfPagamentoListNewPagamento = pagamentoListNewPagamento.getMatriculaID();
                    pagamentoListNewPagamento.setMatriculaID(matricula);
                    pagamentoListNewPagamento = em.merge(pagamentoListNewPagamento);
                    if (oldMatriculaIDOfPagamentoListNewPagamento != null && !oldMatriculaIDOfPagamentoListNewPagamento.equals(matricula)) {
                        oldMatriculaIDOfPagamentoListNewPagamento.getPagamentoList().remove(pagamentoListNewPagamento);
                        oldMatriculaIDOfPagamentoListNewPagamento = em.merge(oldMatriculaIDOfPagamentoListNewPagamento);
                    }
                }
            }
            for (Cobranca cobrancaListNewCobranca : cobrancaListNew) {
                if (!cobrancaListOld.contains(cobrancaListNewCobranca)) {
                    Matricula oldMatriculaIDOfCobrancaListNewCobranca = cobrancaListNewCobranca.getMatriculaID();
                    cobrancaListNewCobranca.setMatriculaID(matricula);
                    cobrancaListNewCobranca = em.merge(cobrancaListNewCobranca);
                    if (oldMatriculaIDOfCobrancaListNewCobranca != null && !oldMatriculaIDOfCobrancaListNewCobranca.equals(matricula)) {
                        oldMatriculaIDOfCobrancaListNewCobranca.getCobrancaList().remove(cobrancaListNewCobranca);
                        oldMatriculaIDOfCobrancaListNewCobranca = em.merge(oldMatriculaIDOfCobrancaListNewCobranca);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = matricula.getMatriculaID();
                if (findMatricula(id) == null) {
                    throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.");
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
            Matricula matricula;
            try {
                matricula = em.getReference(Matricula.class, id);
                matricula.getMatriculaID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matricula with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Cobranca> cobrancaListOrphanCheck = matricula.getCobrancaList();
            for (Cobranca cobrancaListOrphanCheckCobranca : cobrancaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Matricula (" + matricula + ") cannot be destroyed since the Cobranca " + cobrancaListOrphanCheckCobranca + " in its cobrancaList field has a non-nullable matriculaID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Aluno alunoID = matricula.getAlunoID();
            if (alunoID != null) {
                alunoID.getMatriculaList().remove(matricula);
                alunoID = em.merge(alunoID);
            }
            Atividade atividadeID = matricula.getAtividadeID();
            if (atividadeID != null) {
                atividadeID.getMatriculaList().remove(matricula);
                atividadeID = em.merge(atividadeID);
            }
            Secretária secretáriaID = matricula.getSecretáriaID();
            if (secretáriaID != null) {
                secretáriaID.getMatriculaList().remove(matricula);
                secretáriaID = em.merge(secretáriaID);
            }
            List<Pagamento> pagamentoList = matricula.getPagamentoList();
            for (Pagamento pagamentoListPagamento : pagamentoList) {
                pagamentoListPagamento.setMatriculaID(null);
                pagamentoListPagamento = em.merge(pagamentoListPagamento);
            }
            em.remove(matricula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Matricula> findMatriculaEntities() {
        return findMatriculaEntities(true, -1, -1);
    }

    public List<Matricula> findMatriculaEntities(int maxResults, int firstResult) {
        return findMatriculaEntities(false, maxResults, firstResult);
    }

    private List<Matricula> findMatriculaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Matricula.class));
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

    public Matricula findMatricula(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Matricula.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatriculaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Matricula> rt = cq.from(Matricula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
