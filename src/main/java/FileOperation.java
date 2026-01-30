import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class FileOperation {

    public static void supprimeFichier(String path){
        try{
            File fileResultat = new File(path);
            if(fileResultat.exists()){
                fileResultat.delete();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void ecritFichierResultat(File file, int compteurLF, int compteurCRLF, int compteurLigne){
        //Résultat
        try(FileWriter writerResultat = new FileWriter(Const.PATH_RESULTAT, true)){
            writerResultat.write(
                    file.getName() + Const.LF +
                            "LF: " + compteurLF + Const.LF +
                            "CLRF: " + compteurCRLF + Const.LF +
                            "Nombre de ligne: " + compteurLigne + Const.LF +
                            Const.SEPARATEUR + Const.LF + Const.LF
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void correcteur(File file){
        try(FileWriter myWriter = new FileWriter(Const.PATH + '\\' + Const.TEMP + file.getName())){
            if (file.exists()){
                int compteurLF = 0;
                int compteurCRLF = 0;
                int compteurLigne = 0;
                Scanner myReader = new Scanner(file);
                while (myReader.hasNextLine()){
                    String data = myReader.nextLine();
                    compteurLigne++;
                    char[] ligne = data.toCharArray();
                    myWriter.write(data);
                    //Si data.length = " alors LF
                    if (ligne.length > 0){
                        if (ligne[ligne.length -1] == '"'){
                            myWriter.write(Const.LF);
                            compteurLF++;
                        } else if (ligne[ligne.length -1] == '}'){
                            myWriter.write(Const.LF);
                            compteurCRLF++;
                        }
                        //Sinon le reste est CRLF
                        else{
                            myWriter.write(Const.CRLF);
                            compteurCRLF++;
                        }
                    }
                    //Si data.length = 0 alors \n
                    else{
                        myWriter.write(Const.LF);
                        compteurLF++;
                    }
                }
                myReader.close();

                //Résultat
                ecritFichierResultat(file, compteurLF, compteurCRLF, compteurLigne);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void traiteur(String[] listeFichiers){
        for (String fichier : listeFichiers) {
            if (fichier.toLowerCase().contains(".txt")){
                System.out.println(fichier + " est traité");
                File file = new File(Const.PATH + '\\' + fichier);

                correcteur(file);

                //supprime le fichier d'origine
                if(file.delete()){
                    //Renomme le fichier TEMP pour le nom d'origine
                    File nouveauFichier = new File(Const.PATH + '\\' + Const.TEMP + file.getName());
                    try{
                        nouveauFichier.renameTo(file);
                    } catch (Exception e) {
                        System.out.println("Il y a eu une erreur lorsque le fichier " + nouveauFichier + " a été renommé");
                        throw new RuntimeException(e);
                    }
                }
                else{
                    System.out.println("Il y a eu une erreur lors de la suppression du fichier " + fichier);
                }
            }
            else{
                System.out.println(fichier + " n'est pas un fichier \".txt\", il ne sera donc pas traité");
            }
        }
    }
}
