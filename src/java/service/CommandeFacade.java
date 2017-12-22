/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Commande;

import bean.User;
import controler.util.DateUtil;
import java.util.Date;
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
public class CommandeFacade extends AbstractFacade<Commande> {

    @PersistenceContext(unitName = "chainingProjectPU")
    private EntityManager em;

    private @EJB
    CommandeItemFacade commandeItemFacade;

//***********************************
    public int createCommande(Commande commande, User user) {
        if (!verifierExistenceReference(commande, 0, false)) {
            commande.setId(generateId());
            create(commande);
            commandeItemFacade.createCommandeItems(commande);
            return 1;
        }
        return -1;
    }

    private Long generateId() {
        Long maxId = (Long) em.createQuery("SELECT MAX(cmd.id) FROM Commande cmd").getSingleResult();
        return (maxId == null ? 1l : maxId + 1);
    }

    public boolean verifierExistenceReference(Commande commande, int deleted, boolean isUpdateAction) {
        String requtte = "SELECT c FROM Commande c WHERE 1=1";
        if (commande.getReference() != null && !commande.getReference().equals("")) {
            requtte += " and c.reference ='" + commande.getReference() + "'";
        }
        if (deleted != -1) {
            requtte += " and c.supprimer =" + deleted;
        }
        if (isUpdateAction) {
            requtte += " and c.id <> " + commande.getId();
        }
        List resultat = em.createQuery(requtte).getResultList();
        return (!resultat.isEmpty());

    }

    public List<Commande> findByCriteres(Commande commande, int deleted, int etatPaiement, int etatReception, Date dateCommandeMin, Date dateCommandeMax, Date dateEcheanceMin, Date dateEcheanceMax) {
        String requtte = "SELECT c FROM Commande c WHERE 1=1 ";
        if (commande.getReference() != null && !commande.getReference().equals("")) {
            requtte += " and c.reference ='" + commande.getReference() + "'";
        }
        if (deleted != -1) {
            requtte += " and c.supprimer =" + deleted;
        }

        if (dateCommandeMax != null) {
            requtte += " and c.dateCommande <= '" + DateUtil.getSqlDate(dateCommandeMax) + "'";
        }
        if (dateCommandeMin != null) {
            requtte += " and c.dateCommande >= '" + DateUtil.getSqlDate(dateCommandeMin) + "'";
        }
        if (dateEcheanceMax != null) {
            requtte += " and c.dateEchance <= '" + DateUtil.getSqlDate(dateEcheanceMax) + "'";
        }
        if (dateEcheanceMin != null) {
            requtte += " and c.dateEchance >= '" + DateUtil.getSqlDate(dateEcheanceMin) + "'";
        }
        System.out.println("Requette =" + requtte);
        return em.createQuery(requtte).getResultList();

    }

//    @Override
//    public void create(Commande commande) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        System.out.println("haaa respp ==> " + commande.getResponsable().getId());
//        em.createNativeQuery("INSERT INTO `commande` (`ID`, `COMMENTAIRE`, `DATECOMMANDE`, `DATEECHANCE`"
//                + ", `MONTANTTOTAL`, `REFERENCE`,`REFERENCESUFFIX`,`REFERENCEPRIFFIX`,`REFERENCEINDEX` ,"
//                + " `TVA`, `ABONNE_ID`, `FOURNISSEUR_ID`, `PROJET_ID`, `RESPONSABLE_ID`,"
//                + " `ETATRECEPTION`, `MODIFIER` , `PAIEMENT`, `PAIEMENTEFFETENCOUR`, `SUPPRIMER`"
//                + ", `MONTANTTOTALRECEPTION`, `MONTANTTOTALAVOIR`) "
//                + "VALUES ('" + generateId() + "', '" + commande.getCommentaire() + "' ,'"
//                + simpleDateFormat.format(commande.getDateCommande()) + "',"
//                + "  '" + simpleDateFormat.format(commande.getDateEchance()) + "',"
//                + " '" + commande.getMontantTotal() + "', "
//                + " '" + commande.getReference() + "', '" + commande.getReferenceSuffix() + ""
//                + "', '" + commande.getReferencePriffix() + "', '" + commande.getReferenceIndex() + "', "
//                + " '" + commande.getTva() + "', "
//                + "'" + commande.getAbonne().getId() + "', '" + commande.getFournisseur().getId() + "',"
//                + " '" + commande.getProjet().getId() + "', '" + commande.getResponsable().getId() + "'"
//                + " ,0,0,0,0,0,0,0)").executeUpdate();
//    }
    public Long generateReferenceIndexCommande(Commande commande) {
        String requette = "SELECT MAX(c.referenceIndex) FROM Commande c WHERE 1=1 ";
        if (commande.getReferencePriffix() != null && !commande.getReferencePriffix().equals("")) {
            requette += " and c.referencePriffix='" + commande.getReferencePriffix() + "'";
        }
        if (commande.getReferenceSuffix() != null && !commande.getReferenceSuffix().equals("")) {
            requette += " and c.referenceSuffix='" + commande.getReferenceSuffix() + "'";
        }
        Long max = (Long) em.createQuery(requette).getSingleResult();
        return max == null ? 1l : max + 1;

    }

    public int constructReference(Commande commande) {
        String reference = "";
        if (commande.getReferencePriffix() != null && !commande.getReferencePriffix().equals("")) {
            reference = commande.getReferencePriffix() + "-";
        }
        if (commande.getReferenceIndex() != null && !commande.getReferenceIndex().equals(0)) {
            reference += commande.getReferenceIndex();
        } else {
            return -1;
        }
        if (commande.getReferenceSuffix() != null && !commande.getReferenceSuffix().equals("")) {
            reference += ("-" + commande.getReferenceSuffix());
        }
        commande.setReference(reference);
        return 1;
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CommandeFacade() {
        super(Commande.class);
    }

}
