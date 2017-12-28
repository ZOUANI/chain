/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Chain;
import bean.Heure;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author t3500
 */
@Stateless
public class HeureFacade extends AbstractFacade<Heure> {

    @PersistenceContext(unitName = "chainingProjectPU")
    private EntityManager em;

    
    
       public Heure findByReference(String reference ) {
        if (reference == null || reference.equals("")) {
            return null;
        }
        return loadSingleResult("SELECT p FROM Heure p WHERE p.reference='" + reference + "'");
    }
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HeureFacade() {
        super(Heure.class);
    }
    
}
