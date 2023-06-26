package com.example.wsd.update.file_management;


import com.example.wsd.properties.Properties;
import com.example.wsd.properties.PropertyManager;
import com.example.wsd.update.http_connections.GitHubApiAccessor;

import java.io.IOException;
import java.io.InputStream;

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
}
