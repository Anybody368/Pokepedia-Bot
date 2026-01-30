package other;

import utilitaire.*;

import javax.swing.*;
import java.io.File;
import java.nio.file.*;

public class UploadSleepingSprites {
    private static final String DESCRIPTION = "{{#invoke:Description|sprite|source=sleepwiki}}";
    private static final String COMMENT = "Upload automatique des styles de dodo de Pokémon Sleep";

    public static void main(String[] args) {
        Login.handleLogin(args);

        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setDialogTitle("Sélectionnez le dossier contenant les Sprites de Dodo");

        int result = chooser.showOpenDialog(null);
        File mainDirectory;

        if (result != JFileChooser.APPROVE_OPTION) {
            System.exit(0);
        }
        mainDirectory = chooser.getSelectedFile();

        File[] subDirectories = mainDirectory.listFiles();
        assert subDirectories != null;
        for(File subDirectory : subDirectories) {
            String name = subDirectory.getName();
            if(name.equals("onSnorlax")) name = "4";

            HandleDirectoryUpload(subDirectory, Integer.parseInt(name), false);
        }
    }

    private static void HandleDirectoryUpload(File directory, int number, boolean isShiny) {
        File[] files = directory.listFiles();
        assert files != null;
        for (File file : files) {
            if (file.isDirectory()) {
                HandleDirectoryUpload(file, number, true);
                continue;
            }
            if(file.getName().contains("849") || file.getName().contains("-")) {
                System.err.println("File ignored : " + file.getName());
                continue;
            }
            int iNumDex = Integer.parseInt(file.getName().substring(0, file.getName().length() - 4));
            if(iNumDex > 1025) {
                System.err.println("File ignored : " + file.getName());
                continue;
            }
            long now = System.currentTimeMillis();
            if(iNumDex == 132 && isShiny) continue;

            String numDex = Util.numberToPokepediaDexFormat(iNumDex);
            String uploadName = "Sprite " + numDex + " Dodo " + number;
            if(isShiny) uploadName += " chromatique";
            uploadName += " Sleep.png";

            if(uploadSprite(file, uploadName)) {
                System.out.println(uploadName + " upload successful");
                long elapsed = System.currentTimeMillis() - now;
                if(elapsed < 1000) {
                    try {
                        Thread.sleep(1000 - elapsed);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                System.err.println(uploadName + " upload failed");
            }
        }
    }

    private static boolean uploadSprite(File sprite, String name) {
        Page filePage = new Page("Fichier:" + name, Wiki.POKEPEDIA);
        if(filePage.doesPageExists()) {
            System.err.println("Skipping existing file");
            return false;
        }

        return API.upload(name, sprite, DESCRIPTION, COMMENT);
    }
}
