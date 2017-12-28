/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author t3500
 */
public class ProductionItemHelper {

    private Long id;
    private Long idCommande;
    private String referenceCommande;
    private Long idHeure;
    private String referenceHeure;
    private Long idChain;
    private String referenceChain;
    private Long idProduit;
    private String referenceProduit;
    private String qte;
    private Date date;
    private List<String> produits;

    public List<String> getProduits() {
        return produits;
    }

    public void setProduits(List<String> produits) {
        this.produits = produits;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public String getReferenceCommande() {
        return referenceCommande;
    }

    public void setReferenceCommande(String referenceCommande) {
        this.referenceCommande = referenceCommande;
    }

    public Long getIdHeure() {
        return idHeure;
    }

    public void setIdHeure(Long idHeure) {
        this.idHeure = idHeure;
    }

    public String getReferenceHeure() {
        return referenceHeure;
    }

    public void setReferenceHeure(String referenceHeure) {
        this.referenceHeure = referenceHeure;
    }

    public Long getIdChain() {
        return idChain;
    }

    public void setIdChain(Long idChain) {
        this.idChain = idChain;
    }

    public String getReferenceChain() {
        return referenceChain;
    }

    public void setReferenceChain(String referenceidChain) {
        this.referenceChain = referenceidChain;
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getReferenceProduit() {
        return referenceProduit;
    }

    public void setReferenceProduit(String referenceProduit) {
        this.referenceProduit = referenceProduit;
    }

    public String getQte() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte = qte;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductionItemHelper other = (ProductionItemHelper) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProductionItemHelper{" + "id=" + id + ", idCommande=" + idCommande + ", referenceCommande=" + referenceCommande + ", idHeure=" + idHeure + ", referenceHeure=" + referenceHeure + ", idChain=" + idChain + ", referenceChain=" + referenceChain + ", idProduit=" + idProduit + ", referenceProduit=" + referenceProduit + ", qte=" + qte + ", date=" + date + ", produits=" + produits + '}';
    }

    
}
