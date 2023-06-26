package com.example.wsd.update.http_connections;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GitHubApiAccessor {

    public static HttpURLConnection openReleasesAPI(String repositoryOwner, String repositoryName) {

        String urlString = String.format("https://api.github.com/repos/%s/%s/releases",
                                         repositoryOwner, repositoryName);

        return getHttpURLConnection(urlString);
    }

    private static HttpURLConnection getHttpURLConnection(String urlString) {
        try {
            URL url = new URL(urlString);
            return new HttpConnector().openHttpConnection(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
