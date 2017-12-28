/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Commande;
import bean.CommandeItem;
import bean.Famille;
import bean.Produit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author moulaYounes
 */
@Stateless
public class ProduitFacade extends AbstractFacade<Produit> {

    @PersistenceContext(unitName = "chainingProjectPU")
    private EntityManager em;
    private @EJB
    FamilleFacade familleFacade;
    private @EJB
    CommandeItemFacade commandeItemFacade;

       public Produit findByReference(String reference) {
       if (reference == null || reference.equals("")) {
            return null;
        }
        return loadSingleResult("SELECT p FROM Produit p WHERE p.reference='" + reference + "'");
    }
    private int updateQteGlobalProduit(Produit produit, BigDecimal qte, boolean isSave, boolean checkMode) {
        int res = 0;
        if (isSave) {
            if (!checkMode) {
                produit.setQteGlobalStock(produit.getQteGlobalStock().add(qte));
            }
            res = 1;
        } else {
            if (produit.getQteGlobalStock().compareTo(qte) >= 0) {
                System.out.println("updateOrCreateStock ==> produit.getQteGlobalStock().compareTo(qte) >= 0 : true");
                if (!checkMode) {
                    produit.setQteGlobalStock(produit.getQteGlobalStock().subtract(qte));
                }
                res = 2;
            } else {
                System.out.println("updateOrCreateStock ==> produit.getQteGlobalStock().compareTo(qte) >= 0 : false");
                res = -1;
            }
        }
        if (res >= 0 && !checkMode) {
            edit(produit);
        }
        return res;
    }

    private String findProduitByMagasinAndFamilleRequest(Famille famille, int deleted) {
        String request = "SELECT pr.id,pr.reference,pr.libelle , pr.uniteMesure.nom ,pr.qteGlobalStock FROM Produit pr "
                + " , UniteMesure unm WHERE unm.id=pr.uniteMesure.id ";
        if (famille != null && famille.getId() != null) {
            request += " AND pr.famille.id=" + famille.getId();
        }
        if (deleted != -1) {
            request += " AND pr.supprimer=" + deleted;
        }
        System.out.println(" findProduitByMagasinAndFamilleRequest=> request=" + request);
        return (request);
    }

    public List<Produit> findProduitByFamille(Famille famille, int deleted) {
        List<Produit> myProduits = new ArrayList();
        if (famille != null) {
            List<Object[]> results = em.createQuery(findProduitByMagasinAndFamilleRequest(famille, deleted)).getResultList();
            for (Object[] result : results) {
                Produit produit = new Produit((Long) result[0], (String) result[1], (String) result[2], (String) result[3], (BigDecimal) result[4]);
                myProduits.add(produit);
            }
        }
        return myProduits;
    }

    public List<Produit> extractProduitFromCommandeItems(List<CommandeItem> commandeItems) {
        List<Produit> produits = new ArrayList<>();
        for (CommandeItem commandeItem : commandeItems) {
            produits.add(commandeItem.getProduit());
        }
        return produits;
    }
    public List<String> extractProduitReferenceFromCommandeItems(List<CommandeItem> commandeItems) {
        List<String> produits = new ArrayList<>();
        for (CommandeItem commandeItem : commandeItems) {
            produits.add(commandeItem.getProduit().getReference());
        }
        return produits;
    }
    public List<Produit> extractProduitFromCommandeItems(Commande commande) {
       return extractProduitFromCommandeItems(commandeItemFacade.findCommadeItemsByReferenceCmd(commande));
    }
    public List<String> extractProduitReferenceFromCommandeItems(Commande commande) {
       return extractProduitReferenceFromCommandeItems(commandeItemFacade.findCommadeItemsByReferenceCmd(commande));
    }

    private int findIndexOfProduit(List<Produit> produits, Produit produit) {
        int i = 0;
        for (Produit myProduit : produits) {
            if (Objects.equals(produit.getId(), myProduit.getId())) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public int findIndexOfProduitInCommandeItems(List<CommandeItem> commandeItems, Produit produit) {
        return findIndexOfProduit(extractProduitFromCommandeItems(commandeItems), produit);
    }

    public Produit cloneProduitWithFamille(Produit produitToBeCloned, Famille familleToBeCloned) {
        Produit produit = new Produit();
        produit.setId(produitToBeCloned.getId());
        produit.setLibelle(produitToBeCloned.getLibelle());
        produit.setQteGlobalStock(produitToBeCloned.getQteGlobalStock());
        produit.setFamille(familleFacade.cloneFamille(familleToBeCloned));
        return produit;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProduitFacade() {
        super(Produit.class);
    }

}
