package romestats.bot;

import io.github.cdimascio.dotenv.Dotenv;
import kong.unirest.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public final class Config {

    private static final Dotenv dotenv = Dotenv.load();
    public static JSONObject KEY_CONFIG;
    public static final String STATS_BASEURL = "http://api.emulatornexus.com/v1/rome/persona/";
    public static final String SERVERS_BASEURL = "http://api.emulatornexus.com/v1/rome/servers/";

    static {
        try {
            String jsonString = new JSONParser().parse(new FileReader("config.json")).toString();
            KEY_CONFIG = new JSONObject(jsonString);

        } catch (FileNotFoundException e) {
            System.out.println("weapons_config.json not found");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            System.out.println("weapons_config.json parse exception");
        }
    }

    public static String get(String key) {
        return dotenv.get(key.toUpperCase());
    }

}
