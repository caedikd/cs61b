package gitlet;

import java.io.File;
import java.util.LinkedHashMap;

public class Find {

    public static void find(String commitMessage) {

        File commitsFolder = new File(String.valueOf(init.commits));
        File[] listOfFiles = commitsFolder.listFiles();

        String toPrint = "";
        for (File file : listOfFiles) {
            File meta = new File(file, file.getName());
            if (meta.exists()) {
                LinkedHashMap metaData = Utils.readObject(meta, LinkedHashMap.class);
                if (metaData.containsKey(commitMessage)) {
                    toPrint += (file.getName()) + "\n";
                }
            }
        }
        if (toPrint.length() == 0) {
            System.out.println("Found no commit with that message.");
            System.exit(0);
        }
        else {
            System.out.println(toPrint);
            System.exit(0);
        }



    }
}
