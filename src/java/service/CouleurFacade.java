/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Couleur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author t3500
 */
@Stateless
public class CouleurFacade extends AbstractFacade<Couleur> {

    @PersistenceContext(unitName = "chainingProjectPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CouleurFacade() {
        super(Couleur.class);
    }

   public Couleur clone(Couleur couleur) {
        Couleur cloned = new Couleur();
        cloned.setId(couleur.getId());
        cloned.setReference(couleur.getReference());
        cloned.setNom(couleur.getNom());
        return cloned;
    }

}
