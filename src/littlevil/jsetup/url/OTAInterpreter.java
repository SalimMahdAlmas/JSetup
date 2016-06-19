package littlevil.jsetup.url;

import littlevil.jsetup.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class OTAInterpreter {

    public static void interprets(String link) {
        try {
            String content = getContent(link);
            ArrayList<Property> properties = new ArrayList<>();
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
                OTACompiler.compile(properties);
            }
        } catch (IOException e) {
            System.out.println("[ERROR] No Internet Connection");
        }
    }

    private static String getContent(String link) throws IOException {
        URL url = new URL(link);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String input;
        StringBuilder stringBuilder = new StringBuilder();
        while ( (input = bufferedReader.readLine()) != null) {
            stringBuilder.append(input);
        }
        return stringBuilder.toString();
    }

}
