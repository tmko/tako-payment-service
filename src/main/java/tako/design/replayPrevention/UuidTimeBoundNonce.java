package tako.design.replayPrevention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UuidTimeBoundNonce {

    @Autowired
    NoncesRepository repository;

    public String create () {
        NonceEntity nonce = new NonceEntity();
        repository.save(nonce);
        return nonce.getUuid().toString();
    }

    public boolean validateAndConsume () {
        return false;
    }
}
