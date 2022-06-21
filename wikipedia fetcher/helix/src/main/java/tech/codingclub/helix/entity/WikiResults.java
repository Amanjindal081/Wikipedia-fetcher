package tech.codingclub.helix.entity;

public class WikiResults {
    private String key;
    private  String response;


    private String url;

    public WikiResults(String key, String response, String url) {
        this.key = key;
        this.response = response;
        this.url = url;
    }

    public String getKey() {
        return key;
    }

    public String getResponse() {
        return response;
    }

    public String getUrl() {
        return url;
    }
}
