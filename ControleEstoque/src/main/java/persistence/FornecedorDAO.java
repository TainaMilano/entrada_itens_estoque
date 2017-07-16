/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistence;

import helpers.Hash;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import model.Fornecedor;

/**
 *
 * @author Milano
 */
public class FornecedorDAO {
    private EntityManager em;
    private EntityManagerFactory emf;
    
    public FornecedorDAO() throws SQLException {
        this.emf = Persistence.createEntityManagerFactory("API_REST");
        this.em = this.emf.createEntityManager();
    }
    
    public Fornecedor insert(Fornecedor fornecedor) {
        try {
            em.getTransaction().begin();
            em.persist(fornecedor);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println(e.getMessage());
            throw e;
        }
        return fornecedor;
    }

    public Fornecedor update(Fornecedor fornecedor) {
        try {
            em.getTransaction().begin();
            em.merge(fornecedor);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            System.err.println(e.getMessage());
            throw e;
        }
        return fornecedor;
    }

    public Boolean delete(Fornecedor fornecedor) {
        Boolean retorno;
        try {
            em.getTransaction().begin();
            em.remove(fornecedor);
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
            Fornecedor obj = this.getById(id);
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

    public List<Fornecedor> getAll() {
        return em.createNamedQuery("Fornecedor.findAll").getResultList();
    }

    public Fornecedor getById(int id) {
        return em.find(Fornecedor.class, id);
    }
    
    public Fornecedor login(Fornecedor fornecedor) throws NoSuchAlgorithmException {
            
        fornecedor.setSenha(Hash.generateHash(fornecedor.getSenha()));
        
        if(this.checarLogin(fornecedor)) {
            Query query = em.createNamedQuery("Fornecedor.findByLogin");
            query.setParameter("login", fornecedor.getLogin());
            fornecedor = (Fornecedor) query.getSingleResult();
            String token = Hash.base64encode("login=" + fornecedor.getLogin() + "&" + "senha=" + fornecedor.getSenha());
            fornecedor.setLogin("");
            fornecedor.setSenha("");
            fornecedor.setToken(token);
        } else {
            fornecedor.setToken("");            
        }
        
        return fornecedor;
    }

    public boolean checarLogin(Fornecedor fornecedor) {
        Query query = em.createNamedQuery("Fornecedor.login");
        query.setParameter("login", fornecedor.getLogin());
        query.setParameter("senha", fornecedor.getSenha());
        return query.getResultList().size() > 0;        
    }
}
