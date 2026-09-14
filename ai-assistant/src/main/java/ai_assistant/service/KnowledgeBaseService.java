package ai_assistant.service;

import ai_assistant.client.ServiceOfferingFeignClient;
import ai_assistant.dto.ServiceOffering;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class KnowledgeBaseService {

    private final VectorStore vectorStore;
    private final ServiceOfferingFeignClient serviceOfferingFeignClient;

    public KnowledgeBaseService(
            VectorStore vectorStore,
            ServiceOfferingFeignClient serviceOfferingFeignClient) {

        this.vectorStore = vectorStore;
        this.serviceOfferingFeignClient = serviceOfferingFeignClient;
    }

    public void addSalonKnowledge(Long salonId) {

        // Get real service data from service-offering-service
        Set<ServiceOffering> services =
                serviceOfferingFeignClient.getServicesBySalonId(salonId);

        List<Document> documents = new ArrayList<>();

        // Convert every service into a document
        for (ServiceOffering service : services) {

            String content = """
                    SalonHub service information:

                    Service name: %s
                    Description: %s
                    Price: %s rupees
                    Duration: %s minutes
                    Category ID: %s
                    Salon ID: %s
                    """.formatted(
                    service.getName(),
                    service.getDescription(),
                    service.getPrice(),
                    service.getDuration(),
                    service.getCategoryId(),
                    service.getSalonId()
            );

            Document document = new Document(content);

            documents.add(document);
        }

        // Store documents in pgvector
        if (!documents.isEmpty()) {
            vectorStore.add(documents);
        }
    }
}
