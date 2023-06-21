package com.example.wsd.update.http_connections;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class HttpConnector {

    HttpURLConnection openHttpConnection(URL url) {
        HttpURLConnection connection;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Chrome");
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                System.out.println(connection.getResponseMessage());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
