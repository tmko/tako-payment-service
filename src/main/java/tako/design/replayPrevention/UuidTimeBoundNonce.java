package tako.design.replayPrevention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.UUID;

@Component
public class UuidTimeBoundNonce {

    @Autowired
    NoncesRepository repository;

    @Transactional
    public String create () {
        NonceDAO nonce = new NonceDAO();
        repository.save(nonce);
        return nonce.getUuid().toString();
    }

    public boolean validateAndConsume (UUID uuid) {
        int numRowUpated = repository.consumeNonce(uuid) ;
        return numRowUpated > 0;
    }
}
