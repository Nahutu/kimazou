/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Proprietaire;
import entity.Station;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nahum
 */
@Stateless
public class StationFacade extends AbstractFacade<Station> {

    @PersistenceContext(unitName = "kimazouPU")
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StationFacade() {
        super(Station.class);
    }
    
    public Proprietaire getProprietaire(Integer id){
        return em.find(Proprietaire.class, id);
    }
    
}
