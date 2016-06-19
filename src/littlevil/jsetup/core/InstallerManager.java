package littlevil.jsetup.core;

import littlevil.jsetup.url.OTAInterpreter;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class InstallerManager {
    public static void install(String cur) {

        try {
            new URL(cur);
            OTAInterpreter.interprets(cur);
        } catch (MalformedURLException e) {

            File file1 = new File(cur);
            if(file1.exists()) {
                Interpreter.interprets(cur);

            } else {
                System.out.println("Input file ("+cur+") was not found or was not readable");
            }
        }

    }

}
