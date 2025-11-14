package com.example.task_manager_app.common.service;

import ua_parser.Parser;
import ua_parser.Client;

public class DeviceParser {

    private static final Parser parser = new Parser();

    public static String parseDevice(String userAgent) {
        Client client = parser.parse(userAgent);

        String browser = client.userAgent.family;
        String browserVersion = client.userAgent.major;
        if (browserVersion != null) browser += " " + browserVersion;

        String os = client.os.family;
        String osVersion = client.os.major;
        if (osVersion != null) os += " " + osVersion;

        return browser + " / " + os;
    }
}
