/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Famille;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author moulaYounes
 */
@Stateless
public class FamilleFacade extends AbstractFacade<Famille> {

    @PersistenceContext(unitName = "chainingProjectPU")
    private EntityManager em;

    private Long generateId() {
        Long maxId = (Long) em.createQuery("SELECT MAX(fm.id) FROM Famille fm").getSingleResult();
        return (maxId == null ? 1l : maxId + 1);
    }

    @Override
    public void create(Famille famille) {
        famille.setId(generateId());
        super.create(famille);
    }

   
    
    public Famille cloneFamille(Famille familleToBeCloned) {
        Famille famille = new Famille();
        famille.setId(familleToBeCloned.getId());
        famille.setLibelle(familleToBeCloned.getLibelle());
        return famille;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FamilleFacade() {
        super(Famille.class);
    }

}
