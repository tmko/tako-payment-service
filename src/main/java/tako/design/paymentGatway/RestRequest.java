package tako.design.paymentGatway;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Slf4j
public class RestRequest {

    @Value("${payGateway.authentication.token}")
    private String tokenUrl = "https://auth.payfirma.com/oauth/token";

    private String grant_type = "client_credentials";

    private String client_id = "07bd7f5ddb5773483070567e23ee8d89";

    private String client_secret = "40926645863b85a6da32909e5d322cd1";



    public void getToken () {

        String auth = client_id + ":" + client_secret;
        byte[] encodedAuth = Base64.encodeBase64( auth.getBytes(Charset.forName("US-ASCII")) );
        String authHeader = "Basic " + new String( encodedAuth );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add(HttpHeaders.AUTHORIZATION, authHeader);
        headers.add(HttpHeaders.USER_AGENT, "");


        HttpEntity<String> request = new HttpEntity<>("grant_type=client_credentials", headers);



        RestTemplate template = new RestTemplate();
        BearerTokenEntity token;
        try{
            token = template.postForObject(
                    tokenUrl,
                    request,
                    BearerTokenEntity.class);
        }
        catch(Exception e){
            token = null;
            e.printStackTrace();
        }

        log.info("YA working {}", token == null ? "null" : token.getAccess_token());
    }

}
