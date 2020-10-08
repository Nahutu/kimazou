/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Pistolet;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author nahum
 */
@Stateless
public class PistoletFacade extends AbstractFacade<Pistolet> {

    @PersistenceContext(unitName = "kimazouPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PistoletFacade() {
        super(Pistolet.class);
    }
    
}
