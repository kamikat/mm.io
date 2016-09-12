package moe.banana.mmio;

public interface Configuration {

    /**
     * @return User-Agent string used by this application
     */
    String userAgent();

    /**
     * @return base url of the request endpoint.
     */
    String baseUrl();

    /**
     * @return global default page size
     */
    int pageSize();
}
