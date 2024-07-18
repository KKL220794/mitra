package com.security.config;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfiguration {

    @Bean
    public VertexAI vertexAI(){
        return new VertexAI();
    }

    @Bean
    public GenerativeModel generativeModel(VertexAI vertexAI){
        return new GenerativeModel("gemini-1.5-flash-001", vertexAI);
    }
}
