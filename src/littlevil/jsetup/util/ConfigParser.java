package littlevil.jsetup.util;

import littlevil.jsetup.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Pattern;

public class ConfigParser {
    public String Content;
    public ConfigParser() {
        try {
            Content = getContent(Config.CONFIG_LINK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getValue(String name) {
        String return_value = "";
        for (String line : Content.split("\n")) {
            if (line.contains(name)) {
                return_value = line.split(Pattern.quote(":"))[1];
            }
        }
        return return_value;
    }
    public String getContent(String link) throws IOException {
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
