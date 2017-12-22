/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Commande;
import bean.CommandeItem;
import bean.Couleur;
import bean.Famille;
import bean.Produit;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author moulaYounes
 */
@Stateless
public class CommandeItemFacade extends AbstractFacade<CommandeItem> {

    @PersistenceContext(unitName = "chainingProjectPU")
    private EntityManager em;

    private @EJB
    CommandeFacade commandeFacade;
    private @EJB
    ProduitFacade produitFacade;
    private @EJB
    CouleurFacade couleurFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

//    public void prepare(CommandeItem commandeItem, Commande commande, Reception reception, ReceptionItem receptionItem, Abonne abonne, boolean isSave, boolean commit, boolean downloadReception) {
//        receptionItemFacade.prepareCreateOrRemove(commande, reception, receptionItem, abonne, isSave);
//        associate(commande, commandeItem);
//    }
    private void associate(Commande commande, CommandeItem commandeItem) {
        commandeItem.setCommande(commande);
        if (commande.getCommandeItems().indexOf(commandeItem) == -1) {
            commande.getCommandeItems().add(commandeItem);
        }
    }

    @Override
    public void remove(CommandeItem commandeItem) {
        commandeItem.getCommande().setMontantTotal(commandeItem.getCommande().getMontantTotal().subtract((commandeItem.getPrix().multiply(commandeItem.getQte()))));
        commandeFacade.edit(commandeItem.getCommande());
        super.remove(commandeItem);
    }

    public void remove(Commande commande, List<CommandeItem> commandeItems) {
        for (CommandeItem commandeItem : commandeItems) {
            commandeItem.setCommande(commande);
            remove(commandeItem);
        }
    }

    public void createCommandeItemOfExistingCommande(CommandeItem commandeItem, Commande commande) {
        commandeItem.setCommande(commande);
        commande.setMontantTotal(commande.getMontantTotal().add(commandeItem.getPrix().multiply(commandeItem.getQte())));
        create(commandeItem);
        commandeFacade.edit(commandeItem.getCommande());
    }

    public void createCommandeItems(Commande commande) {
        List<CommandeItem> commandeItems = commande.getCommandeItems();
        for (CommandeItem commandeItem : commandeItems) {
            commandeItem.setCommande(commande);
            create(commandeItem);
        }
    }

    public CommandeItemFacade() {
        super(CommandeItem.class);
    }

    public Object[] findCommandeItemByProduit(List<CommandeItem> commandeItems, Produit produit) {
        int index = 0;
        for (CommandeItem commandeItem : commandeItems) {
            if (produit.equals(commandeItem.getProduit())) {
                return new Object[]{index, commandeItem};
            }
            index++;
        }
        return new Object[]{-1, null};
    }

    public int removeCommandeItemFromCommande(Commande commande, CommandeItem commandeItem) {
        Object[] myCommandeItemObject = findCommandeItemByProduit(commande.getCommandeItems(), commandeItem.getProduit());
        int index = new Integer(myCommandeItemObject[0] + "");
        if (index == -1) {
            return -1;
        }
        commande.getCommandeItems().remove(index);
        commande.setMontantTotal(commande.getMontantTotal().subtract((commandeItem.getQte().multiply(commandeItem.getPrix()))));
        return 1;
    }

    public int addCommandeItemToCommande(Commande commande, CommandeItem commandeItem) {
        List<CommandeItem> commandeItems = commande.getCommandeItems();
        Object[] myCommandeItemObject = findCommandeItemByProduit(commandeItems, commandeItem.getProduit());
        if (myCommandeItemObject[1] != null) {
            return -1;
        }
        commande.setMontantTotal(commande.getMontantTotal().add((commandeItem.getQte().multiply(commandeItem.getPrix()))));
        commande.getCommandeItems().add(commandeItem);
        return 1;

    }

    public CommandeItem findCommandeItemByProduit(Commande commande, Produit produit) {
        return (CommandeItem) em.createQuery("SELECT cmditem FROM CommandeItem cmditem WHERE"
                + " cmditem.produit.id=" + produit.getId()
                + " AND cmditem.commande.id=" + commande.getId()).getSingleResult();
    }

    public List<CommandeItem> findCommadeItemsByIdCmd(Commande commande) {
        if (commande == null || commande.getId() == null) {
            return new ArrayList();
        }
        System.out.println("SELECT cmdi FROM CommandeItem cmdi WHERE cmdi.commande.id=" + commande.getId());
        return em.createQuery("SELECT cmdi FROM CommandeItem cmdi WHERE cmdi.commande.id=" + commande.getId()).getResultList();
    }

    public List<CommandeItem> findCommadeItemsEnCourByIdCmd(Commande commande) {
        return em.createQuery("SELECT cmdi FROM CommandeItem cmdi WHERE cmdi.qte > cmdi.qteRecu - cmdi.qteAvoir  and cmdi.commande.id=" + commande.getId()).getResultList();
    }

    public CommandeItem cloneCommandeItem(Commande commande, CommandeItem commandeItemToBeCloned, Produit produit, Famille famille, Couleur couleur) {
        CommandeItem commandeItem = new CommandeItem();
        commandeItem.setCommande(commande);
        commandeItem.setPrix(commandeItemToBeCloned.getPrix());
        commandeItem.setQte(commandeItemToBeCloned.getQte());
        commandeItem.setProduit(produitFacade.cloneProduitWithFamille(produit, famille));
        commandeItem.setCouleur(couleurFacade.clone(couleur));
        commandeItem.setQteRecu(new BigDecimal(0));
        return commandeItem;
    }

    public BigDecimal calculerMontantTotal(List<CommandeItem> commandeItems) {
        BigDecimal montantTotal = new BigDecimal(0);
        for (CommandeItem item : commandeItems) {
            montantTotal = montantTotal.add(item.getPrix().multiply(item.getQte()));
        }
        return montantTotal;
    }

}
