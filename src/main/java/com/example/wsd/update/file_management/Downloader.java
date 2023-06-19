package com.example.wsd.update.file_management;


import com.example.wsd.properties.Properties;
import com.example.wsd.properties.PropertyManager;
import com.example.wsd.update.file_zipping.FileZipperAPI;
import com.example.wsd.update.http_connections.GitHubApiAccessor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class Downloader {

    public static InputStream downloadReleases() {

        Properties props = PropertyManager.INSTANCE.getProperties();

        String owner = props.getRepository()
                            .getOwner();

        String repoName = props.getRepository()
                               .getRepoName();

        try {
            return GitHubApiAccessor.openReleasesAPI(owner, repoName).getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void downloadFilesToTempDirectory(String zipBallUrl) {
        try {
            FileZipperAPI.unZipFilesToDestination
                                 (
                                         GitHubApiAccessor.openURL(zipBallUrl).getInputStream(),

                                         Files.createDirectories(DirectoryManager.getTempDirectory()
                                                                                 .toPath())
                                              .toFile()
                                 );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
