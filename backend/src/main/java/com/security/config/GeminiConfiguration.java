package com.security.config;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.HarmCategory;
import com.google.cloud.vertexai.api.SafetySetting;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class GeminiConfiguration {

    @Bean
    public VertexAI vertexAI(){
        return new VertexAI("hack-team-mindguardians", "us-central1");
    }

    @Bean
    public GenerativeModel generativeModel(VertexAI vertexAI){
        GenerationConfig generationConfig =
                GenerationConfig.newBuilder()
                        .setMaxOutputTokens(8192)
                        .setTemperature(1F)
                        .setTopP(0.95F)
                        .build();
        List<SafetySetting> safetySettings = Arrays.asList(
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                        .build(),
                SafetySetting.newBuilder()
                        .setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_MEDIUM_AND_ABOVE)
                        .build()
        );

        return new GenerativeModel.Builder()
                .setModelName("gemini-1.5-pro-001")
                .setVertexAi(vertexAI)
                .setGenerationConfig(generationConfig)
                .setSafetySettings(safetySettings)
                .build();
    }
}
