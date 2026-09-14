package ai_assistant.controller;

import ai_assistant.dto.ChatRequest;
import ai_assistant.dto.ChatResponse;
import ai_assistant.service.AiChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
public class AiChatController {

    private final AiChatService aiChatService;

    public AiChatController(AiChatService aiChatService) {
        this.aiChatService = aiChatService;
    }

    @PostMapping("/chat")
    public ChatResponse chat(@RequestBody ChatRequest request) {

        String response = aiChatService.chat(request.getMessage());

        return new ChatResponse(response);
    }
}