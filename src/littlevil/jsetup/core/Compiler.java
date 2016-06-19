package littlevil.jsetup.core;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class Compiler {

    public static void compile(ArrayList<Property> properties) {
        if (properties == null) {
            return;
        }
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
            runSomething("mkdir /usr/local/bin/jsetup");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (d == properties.size()) {
            if (run && jar) {
                if (!Objects.equals(jar_path, "")) {

                    run_name = run_name.replaceAll(" ", "");
                    jar_path = jar_path.replace(" ", "");
                    writeFile(run_name + ".info", stringBuilder.toString());
                    System.out.println("[*] Installing Packages : " + run_name);
                    if (new File(jar_path).exists()) {
                        System.out.println("[*] We have Found the Jar Path");
                        System.out.println("[*] Checking the root access");
                        if (checkRoot()) {
                            System.out.println("[*] Eurekaa Root Found");
                            try {
                                System.out.println("[*] Copying jar file..");
                                String file_name = "/usr/local/bin" + "/jsetup/" + run_name+".jar";

                                runSomething("cp " + jar_path + " " + file_name);
                                System.out.println("[*] Adding Some Hook to the System");
                                String read_by = Interpreter.read("/usr/local/bin/jsetup/run_by");
                                read_by =read_by.replace("{RUB_BY}","jsetup/"+run_name+".jar");
                                writeFileR("/usr/local/bin/"+run_name,read_by);
                                System.out.println("[*] Setting Permission to the enviornment");
                                runSomething("chmod +x " + "/usr/local/bin/" + run_name);
                                System.out.println("[*] Package Installed . To run it use - "+run_name);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            System.out.println("[ERROR] Root access not found");
                        }
                    } else {
                        System.out.println("[ERROR] No Jar Found : " + jar_path);

                    }
                }
            }else {
                System.out.println("[ERROR] No run or jar_path found in the script");
            }
        }
    }
    public static String runSomething(String cmdLine) throws Exception {
        Process process =
                new ProcessBuilder("bash", "-c", cmdLine)
                        .redirectErrorStream(true)
                        .directory(new File("."))
                        .start();


        BufferedReader br = new BufferedReader(
                new InputStreamReader(process.getInputStream()));
        String line;
        String output= "";
        while ((line = br.readLine()) != null)
            output += line;
            output += "\n";

        return output;

    }

    public static boolean checkRoot() {
        boolean result;
        String path = "/usr/local/bin/dash.text";
        try {
            try (PrintWriter printWriter = new PrintWriter(path,"UTF-8")) {
                printWriter.write("Root Checking");
                printWriter.close();
                result = true;
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ignored) {
            result = false;
        }
        File file = new File(path);
        file.delete();
        return result;
    }
    public static void writeFileR(String file_name, String content) {


        try {
            try (PrintWriter printWriter = new PrintWriter(file_name,"UTF-8")) {
                printWriter.write(content);
                printWriter.close();
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ignored) {
        }
    }

    public static void writeFile(String file_name, String content) {
        file_name =   "/usr/local/bin/jsetup/" + file_name;

        try {
            try (PrintWriter printWriter = new PrintWriter(file_name,"UTF-8")) {
                printWriter.write(content);
                printWriter.close();
            }
        } catch (FileNotFoundException | UnsupportedEncodingException ignored) {
        }
    }



}
