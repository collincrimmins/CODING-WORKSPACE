package Projects.urlshortener;

public class _Main {
    public static void main(String[] args) throws InterruptedException {
        UrlShortenerService system = new UrlShortenerService(new StrategyURLBase62());

        // custom alias
        String url1 = system.encodeURL("google.com", "g", 7);
        System.out.println("Generated Short Key: " + url1);

        // No custom alias
        String url2 = system.encodeURL("microsoft.com", null, 7);
        System.out.println("Generated Short Key: " + url2);

        // Resolve URLs
        System.out.println("Resolved key1: " + system.decodeURL(url1));
        System.out.println("Resolved customKey: " + system.decodeURL(url2));

        // Test Expiration (1 second)
        URL URL1 = system.getURLObject(url1);
        System.out.println(URL1.isExpired());

        Thread.sleep(1500);
        
        System.out.println(URL1.isExpired());
        try {
            System.out.println(system.decodeURL(URL1.getShortKey()));
        } catch (RuntimeException e) {
            System.err.println("Exception TEST: " + e.getMessage());
        }

    }

    /*
        Design a URL shortener
        (Gemini: "java low level design prompt: design a url shortener service")

        Requirements:
        - Convert URL to shorturl, and shorturl to originalurl
        - optional "custom alias"
        - optional expiration time
        - analytics (clickcount)

        Class Design

            class URLShortenerService
            - Map<String, URL> database
            - URLStrategy strategy
            + encodeUrl(originalURL, customAlias, numDaysExpiration)
            + decodeUrl(shortURL)

            class URL
            - String originalUrl
            - String shortUrl
            - Instant createdAt
            - Instant expiresAt
            + isExpired() -> boolean

            interface StrategyURL -> StrategyURLBase62, StrategyURLHash
            + generateKey(shortURL)
    */
}
