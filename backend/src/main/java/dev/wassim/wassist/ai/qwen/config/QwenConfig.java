package dev.wassim.wassist.ai.qwen.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class QwenConfig {
    @Bean
    public RestClient qwenRestClient(
            @Value("${ollama.base-url}") String baseUrl
    ) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}
