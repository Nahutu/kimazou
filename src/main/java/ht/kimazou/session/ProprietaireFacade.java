/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht.kimazou.session;

import ht.kimazou.entity.Proprietaire;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author nahum
 */
@Stateless
@LocalBean
public class ProprietaireFacade {

    @PersistenceContext(unitName = "my_persistence_unit")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    
    public void persist(Object object) {
        em.persist(object);
    }
    
    public Proprietaire getProprietaire(int id) { 
        return em.find(Proprietaire.class, id);
    } 

    public void createProprietaire(Proprietaire proprietaire) {
        persist(proprietaire);
    }

    public void update(Proprietaire proprietaire) {
        em.merge(proprietaire);
    }
  /*  
    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false);
    }
    */
    @SuppressWarnings("unchecked")
    public List<Proprietaire> getAllProprietaires() {
        Query query = em.createNamedQuery("Proprietaire.findAll");  
        return query.getResultList();
    }
 
    public void delete(long id){
       Proprietaire p = em.find(Proprietaire.class, id);
       em.remove(p);    
    }
    
    
}
