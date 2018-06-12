package tako.design.paymentGateway;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Slf4j
@Component
public class BearerTokenManager {

    @Value("${payGateway.authentication.token}")
    private String tokenUrl;

    @Value("${payGateway.authentication.id}")
    private String client_id;

    @Value("${payGateway.authentication.pw}")
    private String client_secret;

    private BearerTokenJSON token;


    private HttpEntity getRequest () {
        Charset ASCII = Charset.forName("US-ASCII");
        byte[]  id_pw = (client_id + ":" + client_secret).getBytes(ASCII);
        String  auth  = "Basic " + new String( Base64.encodeBase64(id_pw) );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(HttpHeaders.AUTHORIZATION, auth);
        headers.add(HttpHeaders.USER_AGENT, "");

        String body = "grant_type=client_credentials";

        return new HttpEntity<>(body, headers);
    }

    private void postTokenProcessing (BearerTokenJSON token) {

    }

    public BearerTokenJSON getToken () {

        try
        {
            BearerTokenJSON temp;
            RestTemplate template = new RestTemplate();
            temp = template.postForObject( tokenUrl, getRequest(), BearerTokenJSON.class);
            postTokenProcessing(temp);
            token = temp;
        }
        catch(Exception e)
        {
            log.error("Cannot get payfirma oauth token",e);
        }

        log.info("Payfirma oauth token renewed on {} , {}", System.currentTimeMillis(), token);

        return token;
    }

}
