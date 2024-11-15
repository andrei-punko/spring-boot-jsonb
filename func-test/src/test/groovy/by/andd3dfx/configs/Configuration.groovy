package by.andd3dfx.configs

import groovyx.net.http.RESTClient

class Configuration {
    private static final String host = "localhost"
    private static final String serviceUrl = "http://$host:9080"

    public static final RESTClient restClient = new RESTClient(serviceUrl)
}
