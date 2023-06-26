package com.company;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class Main {


    @Test



    public static void testGetDataFromAPI() {
        try {
            String url = "http://dummyjson.com/users";
            HttpURLConnection connection;

            do {
                connection = (HttpURLConnection) new URL(url).openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
                    url = connection.getHeaderField("Location");
                } else {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    reader.close();
                    connection.disconnect();

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Definir o caminho completo para o arquivo de saída
                        String directoryPath = "C:\\Users\\maril\\Documents\\desafio";
                        String fileName = "response.txt";
                        String filePath = Paths.get(directoryPath, fileName).toString();

                        FileWriter fileWriter = new FileWriter(filePath);
                        fileWriter.write(response.toString());
                        fileWriter.close();

                        // Imprimir o caminho do arquivo de resposta
                        System.out.println("Resposta salva no arquivo: " + filePath);
                        // Imprimir o código de resposta
                        System.out.println("Código de Resposta: " + responseCode);
                        // Imprimir a resposta do servidor
                        System.out.println("Resposta do Servidor:");
                        System.out.println(response.toString());

                        // Teste para verificar se a resposta não está vazia
                        assertTrue("A resposta não deve estar vazia", response.length() > 0);
                        // Teste para verificar se o código de resposta é 200 (OK)
                        assertEquals("Código de resposta incorreto", HttpURLConnection.HTTP_OK, responseCode);
                    } else {
                        // Falha no teste se não for possível obter a resposta
                        fail("Erro ao obter a resposta. Código de resposta: " + responseCode);
                    }
                }
            } while (connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_PERM || connection.getResponseCode() == HttpURLConnection.HTTP_MOVED_TEMP);
        } catch (IOException e) {
            e.printStackTrace();
            // Falha no teste se ocorrer uma exceção de E/S
            fail("Exceção lançada: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Método main para executar o teste de forma independente
        // Chamando o teste
        testGetDataFromAPI();
    }
}