package com.ayanami.businesslogiclayer;

import com.ayanami.dataaccesslayer.dao.impl.UserDAOImpl;
import javafx.scene.image.Image;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ConfigManager {
    private static final Logger LOGGER = LogManager.getLogger(UserDAOImpl.class);
    private String filePath = "src/main/resources/com/ayanami/config.properties";

    private static String username = "";
    private static String CONFIG_FILE_PATH = "";

    public static String getImagePath() throws IOException {
        Properties properties = loadProperties();
        return properties.getProperty("image");
    }

    public static String getUsername() throws IOException {
        Properties properties = loadProperties();
        return properties.getProperty("username");
    }


    private static Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        CONFIG_FILE_PATH = "src/main/resources/com/ayanami/userconfig/" + username + ".properties";
        FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
        properties.load(fileInputStream);
        fileInputStream.close();
        return properties;
    }

    public static Image loadImage(String imagePath) throws IOException {
        if (imagePath != null && !imagePath.isEmpty()) {
            Image selectedImage;
            if (imagePath.startsWith("file:")) {
                selectedImage = new Image(imagePath);
            } else {
                selectedImage = new Image(new FileInputStream(imagePath));
            }
            return selectedImage;
        }
        return null;
    }

    public void saveUserInProperties(String userName, String password) {
        Properties properties = new Properties();
        properties.setProperty("username", userName);
        properties.setProperty("password", password);
        try {
            properties.store(new FileOutputStream(filePath), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createConfigFile(String userName) {
        username = userName;
        String configFilePath = "src/main/resources/com/ayanami/userconfig/" + username + ".properties";

        File configFile = new File(configFilePath);
        if (configFile.exists()) {
            LOGGER.warn("Config file already exists: {}", username);
            return;
        }

        try {
            if (configFile.createNewFile()) {
                LOGGER.info("Creating config file: {}", username);
                saveImage("src/main/resources/com/ayanami/images/Profile-Icon.png");
            } else {
                LOGGER.error("Failed to create config file: {}", username);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveImage(String path) {
        Properties properties = new Properties();
        properties.setProperty("image", path);
        properties.setProperty("username", username);
        try (OutputStream outputStream = new FileOutputStream("src/main/resources/com/ayanami/userconfig/" + username + ".properties")) {
            properties.store(outputStream, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
