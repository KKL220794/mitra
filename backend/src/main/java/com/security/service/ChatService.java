package com.security.service;

import com.google.cloud.vertexai.api.Content;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.ChatSession;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.util.List;

public class ChatService {
    public List<String> getChatHistory(String text, ChatSession chatSession) throws IOException {
        GenerateContentResponse generateContentResponse = chatSession.sendMessage(text);
        List<Content> history = chatSession.getHistory();
        return history.stream().flatMap(h -> h.getPartsList().stream()).map(part -> part.getText()).toList();
    }
}
