package com.example.usedtradeapp.oauth.utils;

import android.content.Context;

import com.example.usedtradeapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    public static Properties loadProperties(Context context) {
        Properties properties = new Properties();
        try (InputStream inputStream = context.getResources().openRawResource(R.raw.config_oauth)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
