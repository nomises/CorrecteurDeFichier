import java.io.*;

public class Main {
    public static void main(String[] args) {
        FileOperation.supprimeFichier(Const.PATH_RESULTAT);

        String[] listeFichiers;
        File dossier = new File(Const.PATH);
        if(dossier.isDirectory()){
            listeFichiers = dossier.list();
            if (listeFichiers.length == 0)
                System.out.println("Il n'y a pas de fichier \".txt\" dans le répertoire" + Const.PATH);
            FileOperation.traiteur(listeFichiers);
        }else {
            System.out.println("Le dossier " + dossier + " spécifié n'est pas valide");
        }
    }
}