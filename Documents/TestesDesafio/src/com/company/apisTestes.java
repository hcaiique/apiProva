package com.company;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.Assert.assertNotNull;

public class apisTestes {

    @Test
    public void testGetToken() throws IOException {
        String token = getToken();
        assertNotNull("Token should not be null", token);
        System.out.println("Obtained Token: " + token);
    }

    @Test
    public void testGetProductsWithToken() throws IOException {
        String token = getToken();
        assertNotNull("Token should not be null", token);

        String products = getProductsWithToken(token);
        assertNotNull("Products should not be null", products);
        System.out.println("Products Response: " + products);
    }

    @Test
    public void testCreateProductAndGetProducts() throws IOException {
        // Obtenha o token
        String token = getToken();
        assertNotNull("Token should not be null", token);

        // Crie o produto e obtenha seu ID
        String productId = createProduct(token);
        assertNotNull("Product ID should not be null", productId);
        System.out.println("Created Product ID: " + productId);

        // Obtenha a lista de produtos 2
        String products = getProductsWithToken(token);
        assertNotNull("Products should not be null", products);
        System.out.println("Products Response: " + products);

        // Verifique se o produto criado está na lista
        JsonObject jsonProducts = new JsonParser().parse(products).getAsJsonObject();
        JsonObject product = jsonProducts.getAsJsonObject(productId);
        if (product != null) {
            System.out.println("Created Product Details: " + product.toString());
        } else {
            System.out.println("Created Product not found in the list.");
        }
    }

    @Test
    public void testGetProductById() throws IOException {
        String productId = "1";
        String productDetails = getProductById(productId);
        assertNotNull("Product details should not be null", productDetails);
        System.out.println("Product Details for ID " + productId + ": " + productDetails);
    }

    private String getToken() throws IOException {
        String url = "https://dummyjson.com/auth/login";
        String jsonData = "{\"username\":\"kminchelle\",\"password\":\"0lelplR\"}";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        connection.getOutputStream().write(jsonData.getBytes());
        connection.getOutputStream().flush();
        connection.getOutputStream().close();

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            String responseString = response.toString();
            String token = extractTokenFromResponse(responseString);
            System.out.println("Token Response: " + responseString);
            System.out.println("Requested Token: " + token);
            return token;
        } else {
            throw new IOException("Failed to get token. Response code: " + responseCode);
        }
    }

    private String extractTokenFromResponse(String response) {
        JsonParser parser = new JsonParser();
        JsonObject jsonResponse = parser.parse(response).getAsJsonObject();
        String token = jsonResponse.get("token").getAsString();
        return token;
    }

    private String getProductsWithToken(String token) throws IOException {
        String url = "https://dummyjson.com/auth/products";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", token);

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String products = response.toString();
            System.out.println("Products Response: " + products);
            return products;
        } else {
            throw new IOException("Failed to get products. Response code: " + responseCode);
        }
    }

    private String createProduct(String token) throws IOException {
        String url = "https://dummyjson.com/products/add";
        String jsonData = "{\n" +
                "    \"title\": \"Perfume Oil\",\n" +
                "    \"description\": \"Mega Discount, Impression of A...\",\n" +
                "    \"price\": 13,\n" +
                "    \"discountPercentage\": 8.4,\n" +
                "    \"rating\": 4.26,\n" +
                "    \"stock\": 65,\n" +
                "    \"brand\": \"Impression of Acqua Di Gio\",\n" +
                "    \"category\": \"fragrances\",\n" +
                "    \"thumbnail\": \"https://i.dummyjson.com/data/products/11/thumnail.jpg\"\n" +
                "}";

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", token);
        connection.setDoOutput(true);

        connection.getOutputStream().write(jsonData.getBytes());
        connection.getOutputStream().flush();
        connection.getOutputStream().close();

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String responseString = response.toString();
            JsonObject jsonResponse = new JsonParser().parse(responseString).getAsJsonObject();
            String productId = jsonResponse.get("id").getAsString();
            System.out.println("Create Product Response: " + responseString);
            return productId;
        } else {
            throw new IOException("Failed to create product. Response code: " + responseCode);
        }
    }

    private String getProductById(String productId) throws IOException {
        String url = "https://dummyjson.com/products/" + productId;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            String productDetails = response.toString();
            System.out.println("Product Details Response: " + productDetails);
            return productDetails;
        } else {
            throw new IOException("Failed to get product details. Response code: " + responseCode);
        }
    }
}