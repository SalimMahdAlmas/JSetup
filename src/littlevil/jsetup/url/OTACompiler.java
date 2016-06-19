package littlevil.jsetup.url;

import littlevil.jsetup.core.Interpreter;
import littlevil.jsetup.core.Property;
import littlevil.jsetup.updater.Updater;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;

import static littlevil.jsetup.core.Compiler.*;

public class OTACompiler {
    public static void compile(ArrayList<Property> properties) {
        boolean run = false;
        String run_name = "";
        boolean jar = false;
        String jar_path = "";
        StringBuilder stringBuilder = new StringBuilder();
        int d = 0;
        for (Property pr : properties) {

            if (pr.getName().startsWith("run") || pr.getName().contains("run")) {
                run = true;
                run_name = pr.getValue();

            }
            if (pr.getName().startsWith("jar_path") || pr.getName().contains("jar_path")) {
                jar = true;

                jar_path = pr.getValue();
            }
            ++d;
            stringBuilder.append(pr.getName().toUpperCase()).append(" : ").append(pr.getValue()).append("\n");
        }

        try {
            littlevil.jsetup.core.Compiler.runSomething("mkdir /usr/local/bin/jsetup");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (d == properties.size()) {
            if (run && jar) {
                if (!Objects.equals(jar_path, "")) {

                    try {
                        URL url = new URL(jar_path);



                        run_name = run_name.replaceAll(" ", "");
                        jar_path = jar_path.replace(" ", "");
                        writeFile(run_name + ".info", stringBuilder.toString());

                        System.out.println("[*] Installing Packages : " + run_name);

                        if (checkRoot()) {
                            System.out.println("[*] Downloading Jar File");
                            Updater.saveUrl("/usr/local/bin/"+run_name+".jar",jar_path);

                            System.out.println("[*] Adding Some Hook to the System");


                            String read_by = Interpreter.read("/usr/local/bin/jsetup/run_by");
                            read_by =read_by.replace("{RUB_BY}","jsetup/"+run_name+".jar");
                            writeFileR("/usr/local/bin/" + run_name, read_by);

                            System.out.println("[*] Setting Permission to the enviornment");
                            runSomething("chmod +x " + "/usr/local/bin/" + run_name);
                            System.out.println("[*] Package Installed . To run it use - " + run_name);

                        }else {
                            System.out.println("[ERROR] Root access not found");
                        }
                    } catch (MalformedURLException e) {
                        System.out.println("[ERROR] Malformed URL Found in the script");
                    } catch (IOException e) {
                        System.out.println("[ERROR] No Internet Connection");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }else {
                System.out.println("[ERROR] No run or jar_path found in the script");
            }
        }
    }
}
