package controler;

import bean.Chain;
import bean.Commande;
import bean.CommandeItem;
import bean.Heure;
import bean.ProductionItem;
import bean.ProductionItemHelper;
import bean.Produit;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import service.ProductionItemFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import service.CommandeItemFacade;

@Named("productionItemController")
@SessionScoped
public class ProductionItemController implements Serializable {

    @EJB
    private service.ProductionItemFacade ejbFacade;
    @EJB
    private service.ProduitFacade produitFacade;
    private List<ProductionItem> items = null;
    private List<ProductionItemHelper> myProductionItems = null;
    //private List<Produit> produits = null;
    private ProductionItem selected;
    private ProductionItem selectedSearchMultiple;
    private @EJB
    service.CommandeItemFacade commandeItemFacade;
    private @EJB
    service.HeureFacade heureFacade;
    private CommandeItem commandeItem;
   
    private ProductionItem productionItemSearch;
    private Date dateMin = new Date();
    private Date dateMax = new Date();

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (newValue != null && !newValue.equals(oldValue) && event.getColumn().getColumnKey().endsWith("commandeRow")) {
            Commande myCommande = new Commande();
            myCommande.setReference(event.getNewValue() + "");
            myProductionItems.get(event.getRowIndex()).setProduits(produitFacade.extractProduitReferenceFromCommandeItems(myCommande));
        }
        for (int i = 0; i < myProductionItems.size(); i++) {
            ProductionItemHelper get = myProductionItems.get(i);
            System.out.println("ha index ==> " + i + " o ha ProductionItemHelper " + get);
        }
    }

    public void initialisermyProductionItem() {
        ejbFacade.injectHelper(selectedSearchMultiple, myProductionItems);
    }

    public void saveProductionItemHelpers() {
        for (int i = 0; i < myProductionItems.size(); i++) {
            ProductionItemHelper get = myProductionItems.get(i);
            System.out.println("ha index ==> " + i + " o ha ProductionItemHelper " + get);
        }
        ejbFacade.saveProductionItemHelpers(myProductionItems);
        prepareMyProductionItems();
    }

    public void onRowEdit(RowEditEvent event) {

    }

    public void onRowCancel(RowEditEvent event) {
        JsfUtil.addSuccessMessage("Cell Changedn ");

    }

    public void findByCriteres() {
        items = ejbFacade.findByCriteres(productionItemSearch, dateMin, dateMax);
    }

    public void clearView() {
    }

    public void calcQteRestante() {
        selected.setQte(commandeItem.getQte().subtract(commandeItem.getQteRecu()));
    }

    public void findCommadeItemsByIdCmd() {
        selected.getCommande().setCommandeItems(commandeItemFacade.findCommadeItemsByIdCmd(selected.getCommande()));
    }

    public void findCommadeItemsByIdCmdForSearch() {
        selectedSearchMultiple.getCommande().setCommandeItems(commandeItemFacade.findCommadeItemsByIdCmd(selectedSearchMultiple.getCommande()));
    }

    public ProductionItemController() {
    }

    public ProductionItem getSelected() {
        if (selected == null) {
            selected = new ProductionItem();
        }
        return selected;
    }

    public void setSelected(ProductionItem selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private ProductionItemFacade getFacade() {
        return ejbFacade;
    }

    public CommandeItem getCommandeItem() {
        if (commandeItem == null) {
            commandeItem = new CommandeItem();
        }
        return commandeItem;
    }

    public void setCommandeItem(CommandeItem commandeItem) {
        this.commandeItem = commandeItem;
    }

    public ProductionItem prepareCreate() {
        selected = new ProductionItem();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("ProductionItemCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("ProductionItemUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("ProductionItemDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<ProductionItem> getItems() {
        if (items == null) {
            findByCriteres();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    getFacade().create(selected, commandeItem);
                } else if (persistAction == PersistAction.UPDATE) {
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

    public ProductionItem getProductionItem(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<ProductionItem> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<ProductionItem> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    public void prepareMyProductionItems() {
        List<Heure> heures = heureFacade.findAll();
        myProductionItems = new ArrayList<>();
        int i = 1;
        for (Heure myHeure : heures) {
            ProductionItemHelper productionItem = new ProductionItemHelper();
            productionItem.setIdHeure(myHeure.getId());
            productionItem.setReferenceHeure(myHeure.getReference());
            productionItem.setId(new Long(i));
            myProductionItems.add(productionItem);
            i++;
        }
    }

    @FacesConverter(forClass = ProductionItem.class)
    public static class ProductionItemControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            ProductionItemController controller = (ProductionItemController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "productionItemController");
            return controller.getProductionItem(getKey(value));
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
            if (object instanceof ProductionItem) {
                ProductionItem o = (ProductionItem) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), ProductionItem.class.getName()});
                return null;
            }
        }

    }

    public ProductionItemFacade getEjbFacade() {
        return ejbFacade;
    }

    public void setEjbFacade(ProductionItemFacade ejbFacade) {
        this.ejbFacade = ejbFacade;
    }

    public List<ProductionItemHelper> getMyProductionItems() {
        return myProductionItems;
    }

    public void setMyProductionItems(List<ProductionItemHelper> myProductionItems) {
        this.myProductionItems = myProductionItems;
    }

    public CommandeItemFacade getCommandeItemFacade() {
        return commandeItemFacade;
    }

    public void setCommandeItemFacade(CommandeItemFacade commandeItemFacade) {
        this.commandeItemFacade = commandeItemFacade;
    }

  
    public Date getDateMin() {
        return dateMin;
    }

    public void setDateMin(Date dateMin) {
        this.dateMin = dateMin;
    }

    public Date getDateMax() {
        return dateMax;
    }

    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax;
    }

    public ProductionItem getSelectedSearchMultiple() {
        if (selectedSearchMultiple == null) {
            selectedSearchMultiple = new ProductionItem();
        }
        return selectedSearchMultiple;
    }

    public void setSelectedSearchMultiple(ProductionItem selectedSearchMultiple) {
        this.selectedSearchMultiple = selectedSearchMultiple;
    }

    public ProductionItem getProductionItemSearch() {
         if (productionItemSearch == null) {
            productionItemSearch = new ProductionItem();
        }
        return productionItemSearch;
    }

    public void setProductionItemSearch(ProductionItem productionItemSearch) {
        this.productionItemSearch = productionItemSearch;
    }

}
