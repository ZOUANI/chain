/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author moulaYounes
 */
@Entity
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private Long referenceIndex;
    private String reference;
    private String referenceSuffix;
    private String referencePriffix;
    private String commentaire;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateCommande;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateEchance;
    private BigDecimal montantTotal;
    private BigDecimal paiement;
    private BigDecimal paiementEffetEnCour;
    private BigDecimal montantTotalReception;
    private BigDecimal montantTotalAvoir;
    private int etatReception;
    private BigDecimal tva = new BigDecimal(20);
    @OneToMany(mappedBy = "commande")
    private List<CommandeItem> commandeItems;
    private int modifier;
    private int supprimer;
    @OneToMany(mappedBy = "commande")
    private List<ProductionItem> productionItems;

    public List<ProductionItem> getProductionItems() {
        return productionItems;
    }

    public void setProductionItems(List<ProductionItem> productionItems) {
        this.productionItems = productionItems;
    }

    public Commande() {

    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public int getSupprimer() {
        return supprimer;
    }

    public void setSupprimer(int supprimer) {
        this.supprimer = supprimer;
    }

    public BigDecimal getPaiementEffetEnCour() {
        if (paiementEffetEnCour == null) {
            paiementEffetEnCour = new BigDecimal(0);
        }
        return paiementEffetEnCour;
    }

    public void setPaiementEffetEnCour(BigDecimal paiementEffetEnCour) {
        this.paiementEffetEnCour = paiementEffetEnCour;
    }

    public BigDecimal getTva() {
        return tva;
    }

    public void setTva(BigDecimal tva) {
        this.tva = tva;
    }

    public BigDecimal getMontantTotalReception() {
        if (montantTotalReception == null) {
            montantTotalReception = new BigDecimal(BigInteger.ZERO);
        }
        return montantTotalReception;
    }

    public void setMontantTotalReception(BigDecimal montantTotalReception) {
        this.montantTotalReception = montantTotalReception;
    }

    public BigDecimal getMontantTotalAvoir() {
        if (montantTotalAvoir == null) {
            montantTotalAvoir = new BigDecimal(BigInteger.ZERO);
        }
        return montantTotalAvoir;
    }

    public void setMontantTotalAvoir(BigDecimal montantTotalAvoir) {
        this.montantTotalAvoir = montantTotalAvoir;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDateCommande() {
        if (dateCommande == null) {
            dateCommande = new Date();
        }
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Date getDateEchance() {
        if (dateEchance == null) {
            dateEchance = new Date();
        }
        return dateEchance;
    }

    public void setDateEchance(Date dateEchance) {
        this.dateEchance = dateEchance;
    }

    public BigDecimal getMontantTotal() {
        if (montantTotal == null) {
            montantTotal = new BigDecimal(0);
        }
        return montantTotal;
    }

    public void setMontantTotal(BigDecimal montantTotal) {
        this.montantTotal = montantTotal;
    }

    public BigDecimal getPaiement() {
        if (paiement == null) {
            paiement = new BigDecimal(0);
        }
        return paiement;
    }

    public void setPaiement(BigDecimal paiement) {
        this.paiement = paiement;
    }

    public int getEtatReception() {
        return etatReception;
    }

    public void setEtatReception(int etatReception) {
        this.etatReception = etatReception;
    }

    public List<CommandeItem> getCommandeItems() {
        if (commandeItems == null) {
            commandeItems = new ArrayList();
        }
        return commandeItems;
    }

    public void setCommandeItems(List<CommandeItem> commandeItems) {
        this.commandeItems = commandeItems;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Commande other = (Commande) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
//        return "Commande{" + "id=" + id + ", reference=" + reference + ", commentaire=" + commentaire + ", dateCommande="
//                + dateCommande + ", dateEchance=" + dateEchance + ", montantTotal=" + montantTotal + ", paiement=" + paiement
//                + ", paiementEffetEnCour=" + paiementEffetEnCour + ", etatReception=" + etatReception
//                + ", tva=" + tva + '}';
        return reference;
    }

    public String getReferenceSuffix() {
        return referenceSuffix;
    }

    public void setReferenceSuffix(String referenceSuffix) {
        this.referenceSuffix = referenceSuffix;
    }

    public String getReferencePriffix() {
        return referencePriffix;
    }

    public void setReferencePriffix(String referencePriffix) {
        this.referencePriffix = referencePriffix;
    }

    public Long getReferenceIndex() {
        return referenceIndex;
    }

    public void setReferenceIndex(Long referenceIndex) {
        this.referenceIndex = referenceIndex;
    }
    
    

}
