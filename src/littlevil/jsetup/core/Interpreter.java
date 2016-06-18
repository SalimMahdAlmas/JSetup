package littlevil.jsetup.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class Interpreter {
    public static void interprets(String file) {

        ArrayList<Property> properties = new ArrayList<>();
        String content = read(file);
        for (String line : content.split("\n")) {
            if (line.contains("=")) {

                String[] l = line.split(Pattern.quote("="));
                Property p = new Property();
                p.setName(l[0]);
                p.setValue(l[1]);
                properties.add(p);
            }
        }
        if (properties.size()>2) {
            Compiler.compile(properties);
        }

    }
    public static String read(String path) {
        File file = new File(path);
        StringBuilder stringBuffer = new StringBuilder();
        String line;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file.getAbsolutePath()));
            line = bufferedReader.readLine();
            while (line != null) {
                stringBuffer.append(line);
                stringBuffer.append(System.lineSeparator());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {

        }
        return stringBuffer.toString();
    }
}
