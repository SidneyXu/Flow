package com.bookislife.flow.core.domain;

/**
 * Created by SidneyXu on 2016/06/04.
 */
public class Installation extends BaseEntity {

    private Device device;
    private String installToken;
    private String installId;

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public String getInstallToken() {
        return installToken;
    }

    public void setInstallToken(String installToken) {
        this.installToken = installToken;
    }

    public String getInstallId() {
        return installId;
    }

    public void setInstallId(String installId) {
        this.installId = installId;
    }
}
