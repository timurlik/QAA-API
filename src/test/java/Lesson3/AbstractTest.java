package Lesson3;

import org.junit.jupiter.api.BeforeAll;
import sun.security.util.Password;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractTest {
    static Properties properties = new Properties();
    private static InputStream configFile;
    private static String apiKey;
    private static String baseUrl;
    private static String complexSearch;
    private static String recipesCuisine;
    @BeforeAll
    static void initTest() throws IOException {
        configFile = new FileInputStream("src/main/resources/my.properties");
        properties.load(configFile);
        apiKey = properties.getProperty("apiKey");
        baseUrl = properties.getProperty("baseUrl");
        complexSearch = properties.getProperty("complexSearch");
        recipesCuisine = properties.getProperty("recipesCuisine");
    }
    public static String getApiKey() {
        return apiKey;
    }
    public static String getBaseUrl() {
        return baseUrl;
    }
    public static String getComplexSearch() {
        return complexSearch;
    }
    public static String getRecipesCuisine() {
        return recipesCuisine;
    }
}