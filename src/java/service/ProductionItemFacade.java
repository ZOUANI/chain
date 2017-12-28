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
import bean.ProductionItemHelper;
import bean.Produit;
import controler.util.DateUtil;
import controler.util.SearchUtil;
import java.math.BigDecimal;
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
    @EJB
    private service.ProduitFacade produitFacade;
    @EJB
    private service.ChainFacade chainFacade;
    @EJB
    private service.HeureFacade heureFacade;
    @EJB
    private service.CommandeFacade commandeFacade;

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

    @Override
    public void create(ProductionItem selected) {
        CommandeItem commandeItem = commandeItemFacade.findCommandeItemByProduit(selected.getCommande(), selected.getProduit());
        create(selected, commandeItem);
    }

    public List<ProductionItem> findByCommande(Commande commande) {
        if (commande == null || commande.getId() == null) {
            return new ArrayList();
        }
        System.out.println("SELECT pi FROM ProductionItem pi WHERE pi.commande.id=" + commande.getId());
        return em.createQuery("SELECT pi FROM ProductionItem pi WHERE pi.commande.id=" + commande.getId()).getResultList();
    }

    public List<ProductionItem> findByCriteres(ProductionItem productionItem, Date dateMin, Date dateMax) {
        String requtte = "SELECT pi FROM ProductionItem pi WHERE 1=1 ";
        if (productionItem != null) {
            if (productionItem.getCommande() != null) {
                requtte += SearchUtil.addConstraint("pi", "commande.id", "=", productionItem.getCommande().getId());
            }
            if (productionItem.getHeure() != null) {
                requtte += SearchUtil.addConstraint("pi", "heure.id", "=", productionItem.getHeure().getId());
            }
            if (productionItem.getChain() != null) {
                requtte += SearchUtil.addConstraint("pi", "chain.id", "=", productionItem.getChain().getId());
            }
            if (productionItem.getProduit() != null) {
                requtte += SearchUtil.addConstraint("pi", "produit.id", "=", productionItem.getProduit().getId());
            }
        }
        requtte += SearchUtil.addConstraintMinMaxDate("pi", "dateProduction", dateMin, dateMax);
        System.out.println("Requette =" + requtte);
        return em.createQuery(requtte).getResultList();

    }

    public void injectHelper(ProductionItem selectedSearchMultiple, List<ProductionItemHelper> myProductionItems) {
        for (ProductionItemHelper myProductionItem : myProductionItems) {
            myProductionItem.setIdCommande(selectedSearchMultiple.getCommande().getId());
            myProductionItem.setReferenceCommande(selectedSearchMultiple.getCommande().getReference());
            myProductionItem.setProduits(produitFacade.extractProduitReferenceFromCommandeItems(selectedSearchMultiple.getCommande()));
            myProductionItem.setIdChain(selectedSearchMultiple.getChain().getId());
            myProductionItem.setReferenceChain(selectedSearchMultiple.getChain().getReference());
            myProductionItem.setDate(selectedSearchMultiple.getDateProduction());
            myProductionItem.setIdProduit(selectedSearchMultiple.getProduit().getId());
            myProductionItem.setReferenceProduit(selectedSearchMultiple.getProduit().getReference());
        }
    }

    public int saveProductionItemHelpers(List<ProductionItemHelper> myProductionItems) {
        saveProductionItems(transformHelper(myProductionItems));
        return 1;
    }

    private List<ProductionItem> transformHelper(List<ProductionItemHelper> myProductionItems) {

        List<ProductionItem> productionItems = new ArrayList();
        int i = 0;
        for (ProductionItemHelper productionItemHelper : myProductionItems) {
            ProductionItem productionItem = new ProductionItem();
            boolean valid = validateProductionItemHelper(productionItemHelper);
            System.out.println("validation pour i=" + i + " est ============== " + valid);
            if (valid) {
                productionItem.setCommande(commandeFacade.findByReference(productionItemHelper.getReferenceCommande()));
                productionItem.setChain(chainFacade.findByReference(productionItemHelper.getReferenceChain()));
                productionItem.setHeure(heureFacade.findByReference(productionItemHelper.getReferenceHeure()));
                // chang to findByRef
                productionItem.setProduit(produitFacade.find(3L));
                productionItem.setDateProduction(productionItemHelper.getDate());
                productionItem.setQte(new BigDecimal(productionItemHelper.getQte()));
                productionItems.add(productionItem);
            }
            i++;
        }
        return productionItems;
    }

    private int saveProductionItems(List<ProductionItem> productionItems) {
        for (ProductionItem productionItem : productionItems) {
            create(productionItem);
        }
        return 1;
    }

    private boolean validateProductionItemHelper(ProductionItemHelper productionItemHelper) {
        return productionItemHelper.getDate() != null && productionItemHelper.getQte() != null
                && productionItemHelper.getIdCommande() != null
                //&& productionItemHelper.getIdProduit() != null
                && productionItemHelper.getIdHeure() != null && productionItemHelper.getIdChain() != null;
    }

    public boolean validateProductionItemHelperLigne(ProductionItemHelper productionItemHelper) {
        return (validateProductionItemHelper(productionItemHelper))
                || (productionItemHelper.getDate() == null && productionItemHelper.getQte() == null
                && productionItemHelper.getIdCommande() == null
                && productionItemHelper.getIdProduit() == null && productionItemHelper.getIdHeure() == null
                && productionItemHelper.getIdChain() == null);
    }

}
