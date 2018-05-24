package tako.design.replayPrevention;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tako.design.BackendApplication;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class simpleNonceCRUDTest {

    @Autowired
    private NoncesRepository repository;

    @Test
    public void simpleSaveAndRead () {
        NonceEntity nonce = new NonceEntity();
        repository.save(nonce);

        long id = nonce.getId();

        Assert.assertFalse  ( Objects.isNull(nonce.getUuid().toString())    );
        Assert.assertFalse  ( nonce.getUuid().toString().isEmpty()          );
        Assert.assertTrue   ( nonce.getUnixTime() > 0                       );
        Assert.assertTrue   ( repository.findById(id).isPresent()           );
    }

    @Test
    public void simpleFindAndRead () {
        NonceEntity nonce = new NonceEntity();
        repository.save(nonce);

        UUID uuid = nonce.getUuid();
        Optional<NonceEntity> entry = repository.findFirstByUuid(uuid);

        Assert.assertTrue   ( entry.isPresent() );
        Assert.assertEquals ( nonce, entry.get() );
    }


    @Test
    public void consumingNonce() {
        NonceEntity nonce = new NonceEntity();
        repository.save(nonce);

        UUID uuid = nonce.getUuid();
        repository.consumeNonce(uuid);
    }


    @Test
    public void randomNonceSpeedTest () {
        int _10ms = 10;
        int trial = 100;

        NonceEntity[] nonces = new NonceEntity[trial];

        long startTime = System.currentTimeMillis();
        for ( int i=0; i<trial; i++ )
            nonces[i] = new NonceEntity();
        long endTime = System.currentTimeMillis();

        HashSet<String> uniqueTest = new HashSet<>();
        for ( int i=0; i<trial; i++ )
            uniqueTest.add( nonces[i].getUuid().toString() );

        Assert.assertTrue( (endTime - startTime) / trial < _10ms );
        Assert.assertTrue( uniqueTest.size() == trial );
    }

}
