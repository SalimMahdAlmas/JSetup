package littlevil.jsetup.updater;

import littlevil.jsetup.Config;
import littlevil.jsetup.util.ConfigParser;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class Updater {
    private ConfigParser mConfigParser;
    Updater() {
        mConfigParser = new ConfigParser();
    }
    public static Updater makeUpdater() {
        return new Updater();
    }
    public boolean checkUpdate() {
        String ver_as_str = mConfigParser.getValue("VERSION");
        if (!Objects.equals(ver_as_str, "")) {
            float ver = Float.parseFloat(ver_as_str);
            if (ver > Config.VERSION) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        try {
            saveUrl("/usr/local/bin/jsetup.jar",Config.CONFIG_LINK);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveUrl(final String filename, final String urlString)
            throws MalformedURLException, IOException {
        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(urlString).openStream());
            fout = new FileOutputStream(filename);

            final byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }
}
