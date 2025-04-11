package cpan228.assignment01.brands.service;

import cpan228.assignment01.brands.model.dto.DistributionCenterDto;
import cpan228.assignment01.brands.model.dto.ItemRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DistributionCenterService {
    @Value("${distribution.center.base-url}")
    private String baseUrl;

    @Value("${distribution.center.username}")
    private String username;

    @Value("${distribution.center.password}")
    private String password;

    private final RestTemplate restTemplate;

    public DistributionCenterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<DistributionCenterDto> getAllDistributionCenters() {
        ResponseEntity<List<DistributionCenterDto>> response = restTemplate.exchange(
                baseUrl + "/distribution-centers",
                HttpMethod.GET,
                createAuthHeader(),
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public boolean requestItemFromDistributionCenter(ItemRequest request) {
        try {
            ResponseEntity<Void> response = restTemplate.exchange(
                    baseUrl + "/distribution-centers/request-item",
                    HttpMethod.POST,
                    new HttpEntity<>(request, createAuthHeader().getHeaders()),
                    Void.class
            );
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            return false;
        }
    }

    private HttpEntity<String> createAuthHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        return new HttpEntity<>(headers);
    }
}
