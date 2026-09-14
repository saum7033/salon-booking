package ai_assistant.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorStore;

    public AiChatService(
            ChatClient.Builder builder,
            VectorStore vectorStore) {

        this.chatClient = builder.build();
        this.vectorStore = vectorStore;
    }

    public String chat(String message) {

        // 1. Search the vector database
        List<Document> documents = vectorStore.similaritySearch(
                SearchRequest.builder()
                        .query(message)
                        .topK(3)
                        .build()
        );

        System.out.println("========== RAG RESULTS ==========");

        for (Document document : documents) {
            System.out.println(document.getText());
            System.out.println("--------------------------------");
        }


        // 2. Convert retrieved documents into text
        String context = documents.stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n\n"));

        // 3. Send the retrieved context to Gemini
        return chatClient
                .prompt()
                .system("""
                        You are the official AI assistant for SalonHub.

                        Answer the user's question using ONLY the
                        information provided in the context.

                        If the context does not contain the answer,
                        say:
                        "I don't have that information yet."

                        Never invent salon names, services, prices,
                        availability, bookings, or customer information.
                        """)
                .user("""
                        Context:
                        %s

                        User question:
                        %s
                        """.formatted(context, message))
                .call()
                .content();
    }
}