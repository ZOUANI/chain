package controler;

import bean.Chain;
import bean.Commande;
import bean.CommandeItem;
import bean.Heure;
import bean.ProductionItem;
import bean.Produit;
import controler.util.JsfUtil;
import controler.util.JsfUtil.PersistAction;
import controler.util.Message;
import controler.util.MessageManager;
import controler.util.SessionUtil;
import service.ProductionItemFacade;

import java.io.Serializable;
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
import static org.primefaces.behavior.confirm.ConfirmBehavior.PropertyKeys.message;

@Named("productionItemController")
@SessionScoped
public class ProductionItemController implements Serializable {

    @EJB
    private service.ProductionItemFacade ejbFacade;
    private List<ProductionItem> items = null;
    private ProductionItem selected;
    private @EJB
    service.CommandeItemFacade commandeItemFacade;
    private CommandeItem commandeItem;
    private Commande commande;
    private Heure heure;
    private Chain chain;
    private Produit produit;
    private Date dateMin;
    private Date dateMax;
    
     public void findByCriteres(){
            items = ejbFacade.findByCriteres(commande, heure,  chain,  produit,  dateMin, dateMax);
    }
 
    public void  clearView(){
    }
    

    public void calcQteRestante(){
        selected.setQte(commandeItem.getQte().subtract(commandeItem.getQteRecu()));
    }
    public void findCommadeItemsByIdCmd() {
        selected.getCommande().setCommandeItems(commandeItemFacade.findCommadeItemsByIdCmd(selected.getCommande()));
    }

    public ProductionItemController() {
    }

    public ProductionItem getSelected() {
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
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    getFacade().create(selected,commandeItem);
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

}
