package com.example.wsd.update;

import com.example.wsd.properties.PropertyManager;
import com.example.wsd.properties.Repository;
import com.example.wsd.update.file_management.Downloader;
import com.example.wsd.update.release.Release;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class UpdateManager {

    public static void update() {
        if (isUpdatePresent()) {
            boolean willUpdate = UpdateAlertManager.showUpdateAlert();
            if (willUpdate) {

                Repository repository = PropertyManager.INSTANCE.getProperties().getRepository();

                String urlString = String.format("https://github.com/%s/%s/releases",
                                                 repository.getOwner(),
                                                 repository.getRepoName());

                openUriInBrowser(URI.create(urlString));
            }
        } else {
            UpdateAlertManager.showNoUpdateAlert();
        }
    }

    private static Boolean isUpdatePresent() {

        double currentVersion = PropertyManager.INSTANCE.getProperties().getVersion();

        Release[] releases = getReleases();

        try {
            for (Release release : releases) {
                double v = Double.parseDouble(release.getTag_name());
                //TODO check logic
                if (v > currentVersion) {
                    return true;
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }
        return false;
    }

    private static Release[] getReleases() {
        InputStream inputStream = Downloader.downloadReleases();
        String text = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                                                                           .collect(Collectors.joining());

        ObjectMapper releaseMapper = new ObjectMapper();

        try {
            return releaseMapper.readValue(text, Release[].class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void openUriInBrowser(URI uri){
        try {
            Desktop.getDesktop().browse(uri);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
