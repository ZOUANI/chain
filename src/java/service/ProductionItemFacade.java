/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Chain;
import bean.Commande;
import bean.CommandeItem;
import bean.Heure;
import bean.ProductionItem;
import bean.Produit;
import controler.util.DateUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author t3500
 */
@Stateless
public class ProductionItemFacade extends AbstractFacade<ProductionItem> {

    @PersistenceContext(unitName = "chainingProjectPU")
    private EntityManager em;
    @EJB
    CommandeItemFacade commandeItemFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductionItemFacade() {
        super(ProductionItem.class);
    }

    public void create(ProductionItem selected, CommandeItem commandeItem) {
        selected.setProduit(commandeItem.getProduit());
        commandeItem.setQteRecu(commandeItem.getQteRecu().add(selected.getQte()));
        commandeItemFacade.edit(commandeItem);
        super.create(selected);
    }

    public List<ProductionItem> findByCommande(Commande commande) {
        if (commande == null || commande.getId() == null) {
            return new ArrayList();
        }
        System.out.println("SELECT pi FROM ProductionItem pi WHERE pi.commande.id=" + commande.getId());
        return em.createQuery("SELECT pi FROM ProductionItem pi WHERE pi.commande.id=" + commande.getId()).getResultList();
    }

    public List<ProductionItem> findByCriteres(Commande commande, Heure heure, Chain chain, Produit produit, Date dateMin, Date dateMax) {
        String requtte = "SELECT pi FROM ProductionItem pi WHERE 1=1 ";
        if (commande!= null && commande.getId() != null) {
            requtte += " and pi.commande.id ='" + commande.getId() + "'";
        }
        if (heure != null && heure.getId() != null) {
            requtte += " and pi.heure.id ='" + heure.getId() + "'";
        }
        if (chain != null && chain.getId() != null) {
            requtte += " and pi.chain.id ='" + chain.getId() + "'";
        }
        if (produit != null && produit.getId() != null) {
            requtte += " and pi.produit.id ='" + produit.getId() + "'";
        }

        if (dateMax != null) {
            requtte += " and pi.dateProduction <= '" + DateUtil.getSqlDate(dateMax) + "'";
        }
        if (dateMin != null) {
            requtte += " and pi.dateProduction >= '" + DateUtil.getSqlDate(dateMin) + "'";
        }

        System.out.println("Requette =" + requtte);
        return em.createQuery(requtte).getResultList();

    }

}
