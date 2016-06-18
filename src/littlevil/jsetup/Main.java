package littlevil.jsetup;

import littlevil.jsetup.core.*;
import littlevil.jsetup.core.Compiler;

import java.io.File;

public class Main  {


    public static void main(String[] args) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("JSetup v1.0.1 - a tool for installing Java Jar Packages").append("\n");
        stringBuilder.append("Copyright 2016 Sahid Almas <sahidalmas@gmail.com>\n\n");
        stringBuilder.append("usage: jse\n");
        stringBuilder.append(" --install,--INSTALL <input>        To install jar packages\n");
        stringBuilder.append(" --remove,--REMOVE <RUN_NAME>       To remove the jar packages that is installed\n");
        stringBuilder.append(" --list,--LIST                      To List all the packages installed\n");
        stringBuilder.append(" --info,--INFO <RUN_NAME>           To get info about the packages \n");
        stringBuilder.append(" --help,--HELP                      To see this\n\n");
        stringBuilder.append(
                "For additional info, see: http://github.com/SahidAlmas/JSetup");
        String USAGE = stringBuilder.toString();
        if (args.length >= 1) {


            String command = args[0];

            if (command.contains("help")) {
                System.out.println(USAGE);
            } else if(command.equalsIgnoreCase("--install")) {
                if(args.length >= 2) {
                    String file = args[1];
                    File file1 = new File(args[1]);
                    if(file1.exists()) {
                        Interpreter.interprets(file);

                    } else {
                        System.out.println("Input file ("+file+") was not found or was not readable");
                    }
                } else {System.out.println(USAGE);
                }
            }else if (command.equalsIgnoreCase("--info")) {
                if(args.length >= 2) {
                    String run = args[1];
                   String file_name = "/usr/local/bin/"+"/jsetup/" + run+".info";

                    if (new File(file_name).exists()) {
                        System.out.println(Interpreter.read(file_name));
                    }else {
                        System.out.println("Input packages ("+file_name+") was not found ");
                    }
                } else {System.out.println(USAGE);
                }


            }else if (command.equalsIgnoreCase("--remove")) {

                if(args.length >= 2) {
                    String run = args[1];
                    String file_name = "/usr/local/bin/"+"jsetup/" + run+".info";
                    System.out.println("[*] Package found \n[*] Removing the packages");
                    if (new File(file_name).exists()) {
                        try {
                            if (Compiler.checkRoot()) {
                                littlevil.jsetup.core.Compiler.runSomething("rm /usr/local/bin/jsetup/" + run + ".info");
                                littlevil.jsetup.core.Compiler.runSomething("rm /usr/local/bin/jsetup/" + run + ".jar");
                                littlevil.jsetup.core.Compiler.runSomething("rm /usr/local/bin/" + run);

                                System.out.println("[*] Done");
                            }else {
                                System.out.println("[ERROR] Root access not found");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }else {
                        System.out.println("Input packages ("+file_name+") was not found ");
                    }
                } else {
                    System.out.println(USAGE);
                }


            }

            else if (command.equalsIgnoreCase("--list")) {
                if (Scrabber.getPackagesInstalled().size() == 0) {
                    System.out.println("No Packages Installed.");
                    return;
                }
                System.out.println("Packages Installed are :");
                for (String s : Scrabber.getPackagesInstalled()) {
                    System.out.println("[*] "+s);
                }

            }

            else {
                System.out.println(USAGE);
            }
        } else {
            System.out.println(USAGE);
            System.exit(0);
        }

    }

}
