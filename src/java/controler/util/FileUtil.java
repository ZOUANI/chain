/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controler.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
//import org.apache.commons.io.FileUtils;

/**
 *
 * @author abdelmoughit
 */
public class FileUtil {

    public static String vmParam = "stock.projet.files.path";//chemin dont laquelle on va creer le dosqsier globale qui aura pour bute de contenir la totalitees des dossier d un abonnee

    //generer la totalitees des dossier qui concerne un new abonne
    public static String generateFiles(String indice) {

        List<File> files = new ArrayList<File>();
        String path = System.getProperty(vmParam);
        if (path == null) {
            FacesMessage message = new FacesMessage("Erreur", "option JVM manquante \"" + vmParam + "\"");
            FacesContext.getCurrentInstance().addMessage(null, message);
        } else {

            File file3 = new File(path + "/" + indice + "/DevisCommande");
            File file4 = new File(path + "/" + indice + "/DevisDemande");
            File file5 = new File(path + "/" + indice + "/PaimentAchat");
            File file6 = new File(path + "/" + indice + "/PaimentCommande");
            File file7 = new File(path + "/" + indice + "/PaimentVenteDirect");
            File file8 = new File(path + "/" + indice + "/PhotoCheque");
            File file9 = new File(path + "/" + indice + "/PhotoEffet");
            File file10 = new File(path + "/" + indice + "/PhotoProduit");
            File file11 = new File(path + "/" + indice + "/Livraison");
            File file12 = new File(path + "/" + indice + "/Logo");

            files.add(file3);
            files.add(file4);
            files.add(file5);
            files.add(file6);
            files.add(file7);
            files.add(file8);
            files.add(file9);
            files.add(file10);
            files.add(file11);
            files.add(file12);

            for (File loaded : files) {
                System.out.println(mkdir(loaded));
            }
            return path + "\\" + indice;
        }
        return "";

    }

    //creer les dossier et c est sous dossier
    private static String mkdir(File file) {
        if (!file.exists()) {
            if (file.mkdirs()) {//mkdir
                return file.getName() + " Directory is created!";
            }
            return "Failed to create " + file.getName() + " directory!";
        }
        return file.getName() + " Directory already existe!";
    }

  
}
