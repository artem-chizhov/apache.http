package ru.platon;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        HttpGet request = new HttpGet(
                "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats");
        try {
            CloseableHttpResponse response = httpClient().execute(request);
            String body = new String(response.getEntity().getContent().readAllBytes(), StandardCharsets.UTF_8);
            ObjectMapper mapper = new ObjectMapper();
            FactPojo[] factArray = mapper.readValue(body, FactPojo[].class);
            List<FactPojo> factList = Arrays.asList(factArray);
            factList.stream().filter(value ->
                    value.getUpvotes() != null && value.getUpvotes() > 0);
            for (FactPojo fact : factList) {
                System.out.println(fact.toString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static CloseableHttpClient httpClient() {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
        return httpClient;
    }
}