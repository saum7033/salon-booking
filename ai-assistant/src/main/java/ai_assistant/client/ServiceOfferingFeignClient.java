package ai_assistant.client;

import ai_assistant.dto.ServiceOffering;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

@FeignClient(name = "SERVICE-OFFERING")
public interface ServiceOfferingFeignClient {

    @GetMapping("/api/service-offering/salon/{salonId}")
    Set<ServiceOffering> getServicesBySalonId(
            @PathVariable("salonId") Long salonId
    );
}