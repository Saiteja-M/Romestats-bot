package romestats.bot;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.github.cdimascio.dotenv.Dotenv;
import kong.unirest.json.JSONObject;

public final class ConfigLoader {
	
	public static JSONObject KEY_CONFIG;
	private static final Dotenv dotenv = Dotenv.load();
	
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
