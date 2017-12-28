/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Chain;
import bean.Produit;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author t3500
 */
@Stateless
public class ChainFacade extends AbstractFacade<Chain> {

    @PersistenceContext(unitName = "chainingProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
       public Chain findByReference(String reference ) {
        if (reference == null || reference.equals("")) {
            return null;
        }
        return loadSingleResult("SELECT p FROM Chain p WHERE p.reference='" + reference + "'");
    }

    public ChainFacade() {
        super(Chain.class);
    }
    
}
