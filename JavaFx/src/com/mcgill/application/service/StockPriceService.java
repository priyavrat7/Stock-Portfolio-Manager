package com.mcgill.application.service;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mcgill.application.model.Stock;
import javafx.collections.ObservableList;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class StockPriceService {
    private static final String BASE_URL = "https://query1.finance.yahoo.com/v8/finance/chart/";
    private static final String QUOTE_URL = "https://query1.finance.yahoo.com/v7/finance/quote?symbols=";
    private final HttpClient client;

    public StockPriceService() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
    }

    public double getCurrentPrice(String symbol) {
        try {
            String url = BASE_URL + symbol.toUpperCase();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                return -1;
            }

            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(response.body());

            double price = root.getAsJsonObject()
                    .get("chart")
                    .getAsJsonObject()
                    .get("result")
                    .getAsJsonArray()
                    .get(0)
                    .getAsJsonObject()
                    .get("meta")
                    .getAsJsonObject()
                    .get("regularMarketPrice")
                    .getAsDouble();

            System.out.println("✓ " + symbol + ": $" + price);
            return price;
        } catch (Exception e) {
            System.err.println("✗ Error fetching " + symbol);
            return -1;
        }
    }

    /**
     * Fetch prices for multiple symbols using Yahoo quote endpoint
     * This reduces rate limiting by batching requests.
     */
    public java.util.Map<String, Double> getBatchPrices(java.util.List<String> symbols) {
        java.util.Map<String, Double> result = new java.util.HashMap<>();
        if (symbols == null || symbols.isEmpty()) return result;
        try {
            String csv = String.join(",", symbols.stream().map(String::toUpperCase).toList());
            String url = QUOTE_URL + csv;
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0")
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                System.err.println("Quote API status: " + response.statusCode());
                return result;
            }

            JsonParser parser = new JsonParser();
            JsonElement root = parser.parse(response.body());
            var arr = root.getAsJsonObject()
                .get("quoteResponse").getAsJsonObject()
                .get("result").getAsJsonArray();
            for (var el : arr) {
                var obj = el.getAsJsonObject();
                if (obj.has("symbol") && obj.has("regularMarketPrice") && !obj.get("regularMarketPrice").isJsonNull()) {
                    String sym = obj.get("symbol").getAsString().toUpperCase();
                    double price = obj.get("regularMarketPrice").getAsDouble();
                    result.put(sym, price);
                }
            }
        } catch (Exception e) {
            System.err.println("Batch quote error: " + e.getMessage());
        }
        return result;
    }

    public int updateAllPrices(ObservableList<Stock> stocks) {
        if (stocks == null || stocks.isEmpty()) return 0;
        int updated = 0;
        // Build unique symbol list
        java.util.List<String> all = new java.util.ArrayList<>();
        for (Stock s : stocks) {
            String sym = s.getSymbol();
            if (sym != null && !sym.isBlank()) all.add(sym.toUpperCase());
        }
        all = all.stream().distinct().toList();

        // Yahoo allows long lists; still chunk to be safe (<= 50 per call)
        int chunkSize = 50;
        for (int i = 0; i < all.size(); i += chunkSize) {
            var sub = all.subList(i, Math.min(i + chunkSize, all.size()));
            var map = getBatchPrices(sub);
            if (!map.isEmpty()) {
                for (Stock s : stocks) {
                    Double p = map.get(s.getSymbol().toUpperCase());
                    if (p != null && p > 0) {
                        s.setCurrentPrice(p);
                        updated++;
                    }
                }
            }
            try { Thread.sleep(300); } catch (InterruptedException e) {}
        }
        return updated;
    }
}