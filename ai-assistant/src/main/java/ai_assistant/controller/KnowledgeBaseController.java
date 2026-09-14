package ai_assistant.controller;

import ai_assistant.service.KnowledgeBaseService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai/knowledge")
public class KnowledgeBaseController {

    private final KnowledgeBaseService knowledgeBaseService;

    public KnowledgeBaseController(KnowledgeBaseService knowledgeBaseService) {
        this.knowledgeBaseService = knowledgeBaseService;
    }

    @PostMapping("/seed/{salonId}")
    public String seedKnowledge(@PathVariable Long salonId) {

        knowledgeBaseService.addSalonKnowledge(salonId);

        return "SalonHub knowledge added successfully for salon " + salonId;
    }
}
