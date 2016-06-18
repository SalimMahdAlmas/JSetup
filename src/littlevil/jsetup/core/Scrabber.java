package littlevil.jsetup.core;

import java.io.File;
import java.util.ArrayList;

public class Scrabber {
    public static ArrayList<String> getPackagesInstalled() {
        String s = "/usr/local/bin/jsetup";
        ArrayList<String> arrayList = new ArrayList<>();
        File file = new File(s);
        if (file.isDirectory()) {

            for (String s1 : file.list()) {
                if (s1.contains(".info")) {
                    arrayList.add(s1.replace(".info",""));
                }
            }

        }
        return arrayList;
    }
}
