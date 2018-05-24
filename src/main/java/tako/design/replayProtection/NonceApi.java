package tako.design.replayProtection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class NonceApi {

    @Autowired
    NonceRepository nonceRepository;

    public String getNonce() {
        NonceEntity freshNonce = createNonce();
        nonceRepository.save(freshNonce);
        return freshNonce.getNonce();
    }

    private NonceEntity createNonce () {
        int randInt = ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE);
        return NonceEntity
                .builder()
                .nonce("n" + randInt)
                .status('R')
                .unixTime(Instant.now().getEpochSecond())
                .build();
    }


    public boolean checkNonce(String nonce) {
        int rowUpdated = nonceRepository.consumeNonce(nonce);
        return rowUpdated == 1;
    }


}
