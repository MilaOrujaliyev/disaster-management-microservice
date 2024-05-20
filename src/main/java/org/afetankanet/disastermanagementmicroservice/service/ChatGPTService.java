package org.afetankanet.disastermanagementmicroservice.service;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class ChatGPTService {

    @Value("${chatgpt.enabled}")
    private boolean isChatGPTEnabled;
    @Value("${chatgpt.apiKey}")
    private String apiKey;

    @Value("${chatgpt.host}")
    private String apiHost;


    private static final String CHATGPT_API_ENDPOINT = "https://cheapest-gpt-4-turbo-gpt-4-vision-chatgpt-openai-ai-api.p.rapidapi.com/v1/chat/completions";

    public boolean isContentAppropriate(String content) {
        if (!isChatGPTEnabled) {
            return true;
        }
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-Rapidapi-Key", apiKey);
        headers.set("X-Rapidapi-Host", apiHost);

        HttpEntity<String> entity = new HttpEntity<>(createRequestBody(content), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(CHATGPT_API_ENDPOINT, entity, String.class);

        return evaluateResponse(response.getBody());
    }

    private String createRequestBody(String content) {
        String formattedContent = "\"" + content.replace("\"", "\\\"") +
                " Bu metin, yardım ve afetlerle ilgili bir web sitemizde kullanıcı tarafından gönderilen " +
                "bir yardım talebi. Sana verdiğim metni inceleyip, içeriğin sadece yardım talebiyle ilgili olup olmadığını, " +
                "herhangi bir küfür ya da alakasız metin içerip içermediğini belirle. Eğer metin, sadece yardım talebiyle ilgiliyse ve uygunsa 'True', " +
                "eğer küfür veya alakasız içerik içeriyorsa 'False' olarak yanıt ver. sadece true ya da false cevap ver. yorum yapma.\"";

        return "{ \"messages\": [{ \"role\": \"user\", \"content\": " + formattedContent + " }], " +
                "\"model\": \"gpt-4-turbo-preview\", \"max_tokens\": 200, \"temperature\": 0.9}";
    }


    private boolean evaluateResponse(String responseBody) {
        JSONObject responseJson = new JSONObject(responseBody);
        String contentValue = responseJson.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        return "True".equals(contentValue);
    }
}
