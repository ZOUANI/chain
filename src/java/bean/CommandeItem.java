/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author moulaYounes
 */
@Entity
public class CommandeItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Commande commande;
    @ManyToOne
    private Produit produit;
    @ManyToOne
    private Couleur couleur;
    private BigDecimal qte;
    private BigDecimal qteRecu;
    private BigDecimal prix;

    public Couleur getCouleur() {
        if (couleur == null) {
            couleur = new Couleur();
        }
        return couleur;
    }

    public void setCouleur(Couleur couleur) {
        this.couleur = couleur;
    }

    public BigDecimal getQte() {
        if (qte == null) {
            qte = new BigDecimal(0);
        }
        return qte;
    }

    public void setQte(BigDecimal qte) {
        this.qte = qte;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public BigDecimal getQteRecu() {
        if (qteRecu == null) {
            qteRecu = new BigDecimal(0);
        }
        return qteRecu;
    }

    public void setQteRecu(BigDecimal qteRecu) {
        this.qteRecu = qteRecu;
    }

    public BigDecimal getPrix() {
        if (prix == null) {
            prix = new BigDecimal(0);
        }
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

  

    public CommandeItem() {
    }

    public Produit getProduit() {
        if (produit == null) {
            produit = new Produit();
        }
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.id);
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
        final CommandeItem other = (CommandeItem) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
