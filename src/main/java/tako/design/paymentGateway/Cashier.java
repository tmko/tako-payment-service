package tako.design.paymentGateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Slf4j
@Component
public class Cashier {

    @Value("${payGateway.payment.url}")
    String paymentURL;

    @Autowired
    BearerTokenManager bearerTokenManager;

    private Optional<HttpEntity> getRequest (ChargeJSON charge){
        String  auth  = "Bearer " + bearerTokenManager.getToken();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add(HttpHeaders.AUTHORIZATION, auth);
        headers.add(HttpHeaders.USER_AGENT, "");

        HttpEntity<?> entity = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String body = objectMapper.writeValueAsString(charge);
            entity = new HttpEntity<>(body, headers);
        } catch (Exception e) {
            log.error("Cannot convert charge to JSON {}", charge.toString(), e);
        }

        return Optional.ofNullable(entity);
    }

    public Optional<ReceiptJSON> ask (ChargeJSON charge) {
        RestTemplate template = new RestTemplate();
        Optional<?> body = getRequest(charge);

        ReceiptJSON receipt = null;
        if ( body.isPresent() ) {
            receipt = template.postForObject(paymentURL, body.get(), ReceiptJSON.class);
        }

        return Optional.of(receipt);
    }

}
