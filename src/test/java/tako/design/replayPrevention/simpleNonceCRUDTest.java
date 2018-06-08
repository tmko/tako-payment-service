package tako.design.replayPrevention;


import org.junit.Assert;
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
        NonceDAO nonce = new NonceDAO();
        repository.save(nonce);

        long id = nonce.getId();

        Assert.assertFalse  ( Objects.isNull(nonce.getUuid().toString())    );
        Assert.assertFalse  ( nonce.getUuid().toString().isEmpty()          );
        Assert.assertTrue   ( nonce.getUnixTime() > 0                       );
        Assert.assertTrue   ( repository.findById(id).isPresent()           );
    }

    @Test
    public void simpleFindAndRead () {
        NonceDAO nonce = new NonceDAO();
        repository.save(nonce);

        UUID uuid = nonce.getUuid();
        Optional<NonceDAO> entry = repository.findFirstByUuid(uuid);

        Assert.assertTrue   ( entry.isPresent() );
        Assert.assertEquals ( nonce, entry.get() );
    }


    @Test
    public void consumingNonce() {
        NonceDAO nonce = new NonceDAO();
        repository.save(nonce);

        UUID uuid = nonce.getUuid();
        int n1 = repository.consumeNonce(uuid);
        int n2 = repository.consumeNonce(uuid);

        Assert.assertEquals( n1, 1);
        Assert.assertEquals( n2, 0);
    }


    @Test
    public void randomNonceSpeedTest () {
        int _10ms = 10;
        int trial = 100;

        NonceDAO[] nonces = new NonceDAO[trial];

        long startTime = System.currentTimeMillis();
        for ( int i=0; i<trial; i++ )
            nonces[i] = new NonceDAO();
        long endTime = System.currentTimeMillis();

        HashSet<String> uniqueTest = new HashSet<>();
        for ( int i=0; i<trial; i++ )
            uniqueTest.add( nonces[i].getUuid().toString() );

        Assert.assertTrue( (endTime - startTime) / trial < _10ms );
        Assert.assertTrue( uniqueTest.size() == trial );
    }

}
