/*
 * The MIT License
 *
 * Copyright 2015 mazuh.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.com.fanex.mazuh.jpa;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import br.com.fanex.mazuh.acesso.Hierarquia;
import br.com.fanex.mazuh.acesso.Usuario;
import br.com.fanex.mazuh.edu.Exercicio;
import br.com.fanex.mazuh.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author mazuh
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getExercicioList() == null) {
            usuario.setExercicioList(new ArrayList<Exercicio>());
        }
        if (usuario.getExercicioList1() == null) {
            usuario.setExercicioList1(new ArrayList<Exercicio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Hierarquia idCategoria = usuario.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getId());
                usuario.setIdCategoria(idCategoria);
            }
            List<Exercicio> attachedExercicioList = new ArrayList<Exercicio>();
            for (Exercicio exercicioListExercicioToAttach : usuario.getExercicioList()) {
                exercicioListExercicioToAttach = em.getReference(exercicioListExercicioToAttach.getClass(), exercicioListExercicioToAttach.getId());
                attachedExercicioList.add(exercicioListExercicioToAttach);
            }
            usuario.setExercicioList(attachedExercicioList);
            List<Exercicio> attachedExercicioList1 = new ArrayList<Exercicio>();
            for (Exercicio exercicioList1ExercicioToAttach : usuario.getExercicioList1()) {
                exercicioList1ExercicioToAttach = em.getReference(exercicioList1ExercicioToAttach.getClass(), exercicioList1ExercicioToAttach.getId());
                attachedExercicioList1.add(exercicioList1ExercicioToAttach);
            }
            usuario.setExercicioList1(attachedExercicioList1);
            em.persist(usuario);
            if (idCategoria != null) {
                idCategoria.getUsuarioList().add(usuario);
                idCategoria = em.merge(idCategoria);
            }
            for (Exercicio exercicioListExercicio : usuario.getExercicioList()) {
                Usuario oldIdInstrutorOfExercicioListExercicio = exercicioListExercicio.getIdInstrutor();
                exercicioListExercicio.setIdInstrutor(usuario);
                exercicioListExercicio = em.merge(exercicioListExercicio);
                if (oldIdInstrutorOfExercicioListExercicio != null) {
                    oldIdInstrutorOfExercicioListExercicio.getExercicioList().remove(exercicioListExercicio);
                    oldIdInstrutorOfExercicioListExercicio = em.merge(oldIdInstrutorOfExercicioListExercicio);
                }
            }
            for (Exercicio exercicioList1Exercicio : usuario.getExercicioList1()) {
                Usuario oldIdAlunoOfExercicioList1Exercicio = exercicioList1Exercicio.getIdAluno();
                exercicioList1Exercicio.setIdAluno(usuario);
                exercicioList1Exercicio = em.merge(exercicioList1Exercicio);
                if (oldIdAlunoOfExercicioList1Exercicio != null) {
                    oldIdAlunoOfExercicioList1Exercicio.getExercicioList1().remove(exercicioList1Exercicio);
                    oldIdAlunoOfExercicioList1Exercicio = em.merge(oldIdAlunoOfExercicioList1Exercicio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getId());
            Hierarquia idCategoriaOld = persistentUsuario.getIdCategoria();
            Hierarquia idCategoriaNew = usuario.getIdCategoria();
            List<Exercicio> exercicioListOld = persistentUsuario.getExercicioList();
            List<Exercicio> exercicioListNew = usuario.getExercicioList();
            List<Exercicio> exercicioList1Old = persistentUsuario.getExercicioList1();
            List<Exercicio> exercicioList1New = usuario.getExercicioList1();
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getId());
                usuario.setIdCategoria(idCategoriaNew);
            }
            List<Exercicio> attachedExercicioListNew = new ArrayList<Exercicio>();
            for (Exercicio exercicioListNewExercicioToAttach : exercicioListNew) {
                exercicioListNewExercicioToAttach = em.getReference(exercicioListNewExercicioToAttach.getClass(), exercicioListNewExercicioToAttach.getId());
                attachedExercicioListNew.add(exercicioListNewExercicioToAttach);
            }
            exercicioListNew = attachedExercicioListNew;
            usuario.setExercicioList(exercicioListNew);
            List<Exercicio> attachedExercicioList1New = new ArrayList<Exercicio>();
            for (Exercicio exercicioList1NewExercicioToAttach : exercicioList1New) {
                exercicioList1NewExercicioToAttach = em.getReference(exercicioList1NewExercicioToAttach.getClass(), exercicioList1NewExercicioToAttach.getId());
                attachedExercicioList1New.add(exercicioList1NewExercicioToAttach);
            }
            exercicioList1New = attachedExercicioList1New;
            usuario.setExercicioList1(exercicioList1New);
            usuario = em.merge(usuario);
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getUsuarioList().remove(usuario);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getUsuarioList().add(usuario);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            for (Exercicio exercicioListOldExercicio : exercicioListOld) {
                if (!exercicioListNew.contains(exercicioListOldExercicio)) {
                    exercicioListOldExercicio.setIdInstrutor(null);
                    exercicioListOldExercicio = em.merge(exercicioListOldExercicio);
                }
            }
            for (Exercicio exercicioListNewExercicio : exercicioListNew) {
                if (!exercicioListOld.contains(exercicioListNewExercicio)) {
                    Usuario oldIdInstrutorOfExercicioListNewExercicio = exercicioListNewExercicio.getIdInstrutor();
                    exercicioListNewExercicio.setIdInstrutor(usuario);
                    exercicioListNewExercicio = em.merge(exercicioListNewExercicio);
                    if (oldIdInstrutorOfExercicioListNewExercicio != null && !oldIdInstrutorOfExercicioListNewExercicio.equals(usuario)) {
                        oldIdInstrutorOfExercicioListNewExercicio.getExercicioList().remove(exercicioListNewExercicio);
                        oldIdInstrutorOfExercicioListNewExercicio = em.merge(oldIdInstrutorOfExercicioListNewExercicio);
                    }
                }
            }
            for (Exercicio exercicioList1OldExercicio : exercicioList1Old) {
                if (!exercicioList1New.contains(exercicioList1OldExercicio)) {
                    exercicioList1OldExercicio.setIdAluno(null);
                    exercicioList1OldExercicio = em.merge(exercicioList1OldExercicio);
                }
            }
            for (Exercicio exercicioList1NewExercicio : exercicioList1New) {
                if (!exercicioList1Old.contains(exercicioList1NewExercicio)) {
                    Usuario oldIdAlunoOfExercicioList1NewExercicio = exercicioList1NewExercicio.getIdAluno();
                    exercicioList1NewExercicio.setIdAluno(usuario);
                    exercicioList1NewExercicio = em.merge(exercicioList1NewExercicio);
                    if (oldIdAlunoOfExercicioList1NewExercicio != null && !oldIdAlunoOfExercicioList1NewExercicio.equals(usuario)) {
                        oldIdAlunoOfExercicioList1NewExercicio.getExercicioList1().remove(exercicioList1NewExercicio);
                        oldIdAlunoOfExercicioList1NewExercicio = em.merge(oldIdAlunoOfExercicioList1NewExercicio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getId();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            Hierarquia idCategoria = usuario.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getUsuarioList().remove(usuario);
                idCategoria = em.merge(idCategoria);
            }
            List<Exercicio> exercicioList = usuario.getExercicioList();
            for (Exercicio exercicioListExercicio : exercicioList) {
                exercicioListExercicio.setIdInstrutor(null);
                exercicioListExercicio = em.merge(exercicioListExercicio);
            }
            List<Exercicio> exercicioList1 = usuario.getExercicioList1();
            for (Exercicio exercicioList1Exercicio : exercicioList1) {
                exercicioList1Exercicio.setIdAluno(null);
                exercicioList1Exercicio = em.merge(exercicioList1Exercicio);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
