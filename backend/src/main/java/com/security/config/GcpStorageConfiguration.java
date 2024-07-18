package com.security.config;

import com.google.api.client.util.Value;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.apache.tomcat.util.file.ConfigurationSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GcpStorageConfiguration {

    @Value("${gcp.storage.json-key}")
    private ConfigurationSource.Resource gcpJsonKey;

    @Bean
    public Storage storage() throws IOException {
        return StorageOptions.newBuilder()
                .setCredentials(ServiceAccountCredentials.fromStream(gcpJsonKey.getInputStream()))
                .build()
                .getService();
    }
}

