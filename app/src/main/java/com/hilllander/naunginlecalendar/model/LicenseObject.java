package com.hilllander.naunginlecalendar.model;

/**
 * Created by khunzohn on 11/21/15.
 */
public class LicenseObject {
    private String dependency, license;

    public LicenseObject(String dependency, String license) {
        this.dependency = dependency;
        this.license = license;
    }

    public String getDependency() {
        return this.dependency;
    }

    public String getLicense() {
        return this.license;
    }
}
