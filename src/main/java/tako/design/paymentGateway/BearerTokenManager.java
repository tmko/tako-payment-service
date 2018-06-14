package tako.design.paymentGateway;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Component
public class BearerTokenManager {

    @Value("${payGateway.authentication.token}")
    private String tokenUrl;

    @Value("${payGateway.authentication.id}")
    private String client_id;

    @Value("${payGateway.authentication.pw}")
    private String client_secret;

    @Value("${payGateway.authentication.expiredIn:360}")
    private int expiredIn;

    private static final ScheduledExecutorService renewScheduler = Executors.newScheduledThreadPool(1);

    private static final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    private static BearerTokenJSON tokenObj;


    private void createOrRenew () {
        rwl.writeLock().lock();
        long tokenLife =  Objects.isNull(tokenObj) ? 0 : tokenObj.lifeInSec();
        boolean isTokenAboutExpired = tokenLife <= expiredIn;

        if ( Objects.isNull(tokenObj) || isTokenAboutExpired )
        {
            RestTemplate template = new RestTemplate();
            tokenObj = template.postForObject( tokenUrl, getRequest(), BearerTokenJSON.class);
        }
        rwl.writeLock().unlock();

        long deathTime = tokenObj.getCreatedTime() + tokenObj.getExpires_in() - expiredIn;
        long now = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
        renewScheduler.schedule(this::createOrRenew, now - deathTime, TimeUnit.SECONDS);
    }

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

    public String getToken ()
    {
        if ( Objects.isNull(tokenObj) )
            createOrRenew();

        String result;
        rwl.readLock().lock();
        result = tokenObj.getAccess_token();
        rwl.readLock().unlock();

        return result;
    }
}
