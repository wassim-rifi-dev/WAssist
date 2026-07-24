package dev.wassim.wassist.ai.ollama;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OllamaService {
    private final RestClient ollamaRestClient;

    @Value("${ollama.model}")
    private String model;
}
