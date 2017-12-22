package controler;

import bean.Commande;
import bean.CommandeItem;
import bean.Couleur;
import bean.Famille;
import bean.Produit;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import controler.util.Message;
import controler.util.MessageManager;
import controler.util.SessionUtil;
import service.CommandeFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.event.Reception;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "commandeController")
@SessionScoped
public class CommandeController implements Serializable {

    private @EJB
    service.CommandeFacade ejbFacade;
    private @EJB
    service.CommandeItemFacade commandeItemFacade;

    private @EJB
    service.ProduitFacade produitFacade;
    private @EJB
    service.ProductionItemFacade productionItemFacade;

    private List<Commande> items;
    private Commande selected;
    private CommandeItem commandItem;
    private Produit selectedProduit = new Produit();
    private Famille selectedFamille = new Famille();
    private Couleur selectedCouleur = new Couleur();

    private List<Produit> produits;
    private List<Famille> familles;

    private Commande critereObj;
    private int etatReception;
    private int etatPaiement;
    private Date dateCommandeMin = new Date();
    private Date dateCommandeMax;
    private Date dateEcheanceMin;
    private Date dateEcheanceMax;
    private int booleanSwitch = 0;
    private boolean alertSelected;
    private Message message;

    private Message validateSerarchForm() {
        if (dateCommandeMin != null && dateCommandeMax != null) {
            if (dateCommandeMin.getTime() > dateCommandeMax.getTime()) {
                message = MessageManager.createErrorMessage(-1, "la dateCommandeMax doit etre superieur au dateCommandeMin");
            }
        } else if (dateEcheanceMin != null && dateEcheanceMax != null) {
            if (dateEcheanceMin.getTime() > dateEcheanceMax.getTime()) {
                message = MessageManager.createErrorMessage(-2, "la dateEcheanceMax doit etre superieur au dateEcheanceMin");
            }
        } else {
            message = MessageManager.createErrorMessage(1, "");
        }
        return message;

    }

    public void removeCommandeItem(CommandeItem commandeItem) {
        this.commandItem = commandeItem;
        this.commandItem.setCommande(selected);
        removeCommandeItem();
    }

    public void removeCommandeItem() {

        commandeItemFacade.remove(commandItem);
        selected.getCommandeItems().remove((commandItem));

    }

    public void findProduitByFamille(int deleted) {
        produits = produitFacade.findProduitByFamille(selectedFamille, 0);
    }

    public void findByCriteres(int deleted) {
        validateSerarchForm();
        if (message != null && message.getResultat() > 0) {
            items = ejbFacade.findByCriteres(critereObj, 0, etatPaiement, etatReception, dateCommandeMin, dateCommandeMax, dateEcheanceMin, dateEcheanceMax);
            SessionUtil.setAttribute("listaCmd", items);
            selected = new Commande();
        } else if (message.getResultat() < 0) {
            MessageManager.showMessage(message);
        }
        clearView();
    }

    public void findCommadeItemsByIdCmd(Commande commande) {
        booleanSwitch = 1;
        clearOtherViews(commande);
        selected = commande;
        selected.setCommandeItems(commandeItemFacade.findCommadeItemsByIdCmd(commande));
        selected.setProductionItems(productionItemFacade.findByCommande(commande));
    }

    public String versCommandeItems(Commande commande) {
        selected = commande;
        return "versCommandeItems";
    }

    public void generateReferenceIndexCommande() {
        getSelected().setReferenceIndex(ejbFacade.generateReferenceIndexCommande(getSelected()));
    }

    private void validateView() {
        int resReference = ejbFacade.constructReference(getSelected());
        if (resReference < 0) {
            message = MessageManager.createErrorMessage(-1, "Merci de spécifier la reference");
        } else if (getSelected().getCommandeItems().isEmpty()) {
            message = MessageManager.createErrorMessage(-4, "Merci de selectionner un Produit");
        } else if (ejbFacade.verifierExistenceReference(selected, 0, false) == true) {
            message = MessageManager.createErrorMessage(-5, "La valeur de la référence est déjà affectée");
        } else {
            message = MessageManager.createInfoMessage(1, "Commande crée avec succes");
        }
    }

    public void createCommande() {
        validateView();
        if (message.getResultat() > 0) {
            int resCreate = ejbFacade.createCommande(getSelected(), SessionUtil.getConnectedUser());
            if (resCreate > 0) {
                selected = new Commande();
            } else {
                message = MessageManager.createErrorMessage(-6, "Reference de la commande exist!!");
            }
        }
        MessageManager.showMessage(message);

    }

    public void deleteCommandeItem(CommandeItem commandeItem) {
        int position = produitFacade.findIndexOfProduitInCommandeItems(getSelected().getCommandeItems(), commandeItem.getProduit());
        if (position != -1) {
            getSelected().getCommandeItems().remove(position);
            JsfUtil.addSuccessMessage("Produit Supprimer avec succes!!");
        }
        getSelected().setMontantTotal(commandeItemFacade.calculerMontantTotal(getSelected().getCommandeItems()));
    }

    private void createCommandeItem(boolean commandeExist) {
        CommandeItem clonedCommandeItem = commandeItemFacade.cloneCommandeItem(selected, commandItem, selectedProduit, selectedFamille,selectedCouleur);
        int position = produitFacade.findIndexOfProduitInCommandeItems(getSelected().getCommandeItems(), clonedCommandeItem.getProduit());
        if (position == -1) {
            getSelected().getCommandeItems().add(clonedCommandeItem);
            if (commandeExist) {
                commandeItemFacade.createCommandeItemOfExistingCommande(commandItem, selected);
            } else {
                getSelected().setMontantTotal(commandeItemFacade.calculerMontantTotal(getSelected().getCommandeItems()));
            }
        } else {
            message = MessageManager.createErrorMessage(-8, "Produit existe !!!");
            MessageManager.showMessage(message);
        }
    }

    public void createCommandeItem() {
        createCommandeItem(false);
    }

    public void createCommandeItemOfExistingCommande() {
        createCommandeItem(true);
    }

    //////////***************** RABI3*****************
    private void clearView() {
        getSelected().getCommandeItems().clear();
    }

    private void clearOtherViews(Commande commande) {
        if (getSelected() != null && commande != null) {
            if (!Objects.equals(getSelected().getId(), commande.getId())) {
                clearView();

            }
        }
    }

    public void findAll() {
        items = null;
    }

    public Commande getCritereObj() {
        if (critereObj == null) {
            critereObj = new Commande();
        }

        return critereObj;
    }

    public void setCritereObj(Commande critereObj) {
        if (critereObj == null) {
            critereObj = new Commande();
        }
        this.critereObj = critereObj;
    }

    public int getBooleanSwitch() {
        return booleanSwitch;
    }

    public void setBooleanSwitch(int booleanSwitch) {
        this.booleanSwitch = booleanSwitch;
    }

    public Date getDateCommandeMin() {
        return dateCommandeMin;
    }

    public void setDateCommandeMin(Date dateCommandeMin) {
        this.dateCommandeMin = dateCommandeMin;
    }

    public Date getDateCommandeMax() {
        return dateCommandeMax;
    }

    public void setDateCommandeMax(Date dateCommandeMax) {
        this.dateCommandeMax = dateCommandeMax;
    }

    public Date getDateEcheanceMin() {
        return dateEcheanceMin;
    }

    public void setDateEcheanceMin(Date dateEcheanceMin) {
        this.dateEcheanceMin = dateEcheanceMin;
    }

    public Date getDateEcheanceMax() {
        return dateEcheanceMax;
    }

    public void setDateEcheanceMax(Date dateEcheanceMax) {
        this.dateEcheanceMax = dateEcheanceMax;
    }

    public int getEtatPaiement() {
        return etatPaiement;
    }

    public void setEtatPaiement(int etatPaiement) {
        this.etatPaiement = etatPaiement;
    }

    public int getEtatReception() {
        return etatReception;
    }

    public void setEtatReception(int etatReception) {
        this.etatReception = etatReception;
    }

    public CommandeController() {
    }

    public Commande getSelected() {
        if (selected == null) {
            selected = new Commande();
        }
        return selected;
    }

    public void setSelected(Commande selected) {
        this.selected = selected;
    }

    public CommandeItem getCommandItem() {
        if (commandItem == null) {
            commandItem = new CommandeItem();
        }
        return commandItem;
    }

    public void setCommandItem(CommandeItem commandItem) {
        this.commandItem = commandItem;
    }

    public Produit getSelectedProduit() {
        if (selectedProduit == null) {
            selectedProduit = new Produit();
        }
        return selectedProduit;
    }

    public void setSelectedProduit(Produit selectedProduit) {
        this.selectedProduit = selectedProduit;
    }

    public Famille getSelectedFamille() {
        if (selectedFamille == null) {
            selectedFamille = new Famille();
        }
        return selectedFamille;
    }

    public Couleur getSelectedCouleur() {
        if (selectedCouleur == null) {
            selectedCouleur = new Couleur();
        }
        return selectedCouleur;
    }

    public void setSelectedCouleur(Couleur selectedCouleur) {
        this.selectedCouleur = selectedCouleur;
    }

    public void setSelectedFamille(Famille selectedFamille) {
        this.selectedFamille = selectedFamille;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CommandeFacade getFacade() {
        return ejbFacade;
    }

    public void prepareCreate() {
        commandItem = new CommandeItem();
        selected = new Commande();
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CommandeCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update(Commande commande) {
        selected = commande;
    }

    public void update() {
        selected.setReference(selected.getReferencePriffix() + "-" + selected.getReferenceIndex() + "-" + selected.getReferenceSuffix());
        if (!ejbFacade.verifierExistenceReference(selected, 0, true)) {
            persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CommandeUpdated"));
        } else {
            items.set(items.indexOf(selected), ejbFacade.find(selected.getId())); // cancel valu
            message = MessageManager.createErrorMessage(-5, "La valeur de la référence est déjà affectée");
            MessageManager.showMessage(message);
        }
    }

    public void destroy() {

        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CommandeDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            items.remove(items.indexOf(selected));
            selected = new Commande(); // Remove selection
        }
    }

    public void destroy(Commande commande) {
        selected = commande;
        destroy();
    }

    public List<Commande> getItems() {
        SessionUtil.setAttribute("listaCmd", items); // a revoir rah deja kayena setAttribute f findByCriter
        if (items == null) {
            items = getFacade().findAll(); // lahhhhhhhhhhhhhhhhhhhhhh a modifier
        }
        return items;
    }

    public List<Produit> getProduits() {
        if (produits == null) {
            produits = new ArrayList();
        }
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public List<Famille> getFamilles() {
        if (familles == null) {
            familles = new ArrayList();
        }
        return familles;
    }

    public void setFamilles(List<Famille> familles) {
        this.familles = familles;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Commande> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Commande> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Commande.class)
    public static class CommandeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CommandeController controller = (CommandeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "commandeController");
            return controller.getFacade().find(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Commande) {
                Commande o = (Commande) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Commande.class.getName()});
                return null;
            }
        }

    }

    public boolean isAlertSelected() {
        return alertSelected;
    }

    public void setAlertSelected(boolean alertSelected) {
        this.alertSelected = alertSelected;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
