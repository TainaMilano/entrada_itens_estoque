/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Entrada;

/**
 *
 * @author Milano
 */
public class EntradaDAO {
    private EntityManager em;
    private EntityManagerFactory emf;
    
    public EntradaDAO() throws SQLException {
        this.emf = Persistence.createEntityManagerFactory("API_REST");
        this.em = this.emf.createEntityManager();
    }
    
    public Entrada insert(Entrada entrada) {
        try {
            em.getTransaction().begin();
            em.persist(entrada);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println(e.getMessage());
            throw e;
        }
        return entrada;
    }

    public Entrada update(Entrada entrada) {
        try {
            em.getTransaction().begin();
            em.merge(entrada);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println(e.getMessage());
            throw e;
        }
        return entrada;
    }

    public Boolean delete(Entrada entrada) {
        Boolean retorno;
        try {
            em.getTransaction().begin();
            em.remove(entrada);
            em.getTransaction().commit();
            retorno = true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println(e.getMessage());
            retorno = false;
            throw e;
        }
        return retorno;
    }

    public Boolean delete(int id) {
        Boolean retorno;
        try {
            Entrada obj = this.getById(id);
            em.getTransaction().begin();
            em.remove(obj);
            em.getTransaction().commit();
            retorno = true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println(e.getMessage());
            retorno = false;
            throw e;
        }
        return retorno;
    }

    public List<Entrada> getAll() {
        return em.createNamedQuery("Entrada.findAll").getResultList();
    }

    public Entrada getById(int id) {
        return em.find(Entrada.class, id);
    }
}
