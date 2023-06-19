package com.example.wsd.update;

import com.example.wsd.properties.Properties;
import com.example.wsd.properties.PropertyManager;
import com.example.wsd.update.file_management.DirectoryManager;
import com.example.wsd.update.file_management.Downloader;
import com.example.wsd.update.release.Release;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Collectors;

public class UpdateManager {

    public void update() {
        UpdateAlertManager updateAlertManager = new UpdateAlertManager(this);
        Optional<Release> updateIfPresent = getUpdateIfPresent();
        if (updateIfPresent.isPresent()) {
            boolean willUpdate = updateAlertManager.showUpdateAlert();
            if (willUpdate) {
                Release release = updateIfPresent.get();
                Downloader.downloadFilesToTempDirectory(release.getZipball_url());
                updateVersion(release);
                startUpdaterAndExit();
            }
        } else {
            updateAlertManager.showNoUpdateAlert();
        }
    }

    private Optional<Release> getUpdateIfPresent() {

        double currentVersion = PropertyManager.INSTANCE.getProperties().getVersion();

        Release[] releases = getReleases();

        Optional<Release> optionalNewRelease = Optional.empty();


        for (Release release : releases) {
            double v = Double.parseDouble(release.getTag_name());
            //TODO check logic
            if (v > currentVersion) {
                optionalNewRelease = Optional.of(release);
                currentVersion = v;
            }
        }
        return optionalNewRelease;
    }

    private Release[] getReleases() {
        InputStream inputStream = Downloader.downloadReleases();
        String text = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8)).lines()
                                                                           .collect(Collectors.joining());

        ObjectMapper releaseMapper = new ObjectMapper();


        try {
            Release[] releases = releaseMapper.readValue(text, Release[].class);
            return releases;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateVersion(Release release) {
        Properties properties = PropertyManager.INSTANCE.getProperties();
        String tagName = release.getTag_name();
        properties.setVersion(Double.parseDouble(tagName));
        mapPropertiesJson(properties);
    }

    private void mapPropertiesJson(Properties properties) {
        //TODO might throw error when writing to json in deployed environment
        URL propertiesResource = PropertyManager.INSTANCE.getResource();
        File propertiesFile;
        ObjectMapper mapper = new ObjectMapper();

        try {
            propertiesFile = new File(propertiesResource.toURI());
            mapper.writeValue(propertiesFile, properties);
        } catch (URISyntaxException e) {
            throw new RuntimeException();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void startUpdaterAndExit() {
        String updaterExeName = PropertyManager.INSTANCE.getProperties().getUpdaterExeName();

        File updaterDirectory = DirectoryManager.getUpdaterDirectory();
        File[] updaterFiles = updaterDirectory.listFiles();
        if (updaterFiles != null) {
            for (File updaterFile : updaterFiles) {
                if (updaterFile.toPath().endsWith(updaterExeName)) {
                    deployUpdater(updaterFile);
                    break;
                }
            }
        }
        System.exit(0);
    }

    private void deployUpdater(File updaterFile) {
        try {
            Desktop.getDesktop().open(updaterFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
