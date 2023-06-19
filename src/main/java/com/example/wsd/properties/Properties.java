package com.example.wsd.properties;

public class Properties {

      private double version;

      private String tempDirectoryName;
      private String updaterFolderName;
      private String updaterExeName;

      private Repository repository;

    public double getVersion() {
        return version;
    }

    public void setVersion(double version) {
        this.version = version;
    }

    public String getTempDirectoryName() {
        return tempDirectoryName;
    }

    public void setTempDirectoryName(String tempDirectoryName) {
        this.tempDirectoryName = tempDirectoryName;
    }

    public String getUpdaterFolderName() {
        return updaterFolderName;
    }

    public void setUpdaterFolderName(String updaterFolderName) {
        this.updaterFolderName = updaterFolderName;
    }

    public String getUpdaterExeName() {
        return updaterExeName;
    }

    public void setUpdaterExeName(String updaterExeName) {
        this.updaterExeName = updaterExeName;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }
}

