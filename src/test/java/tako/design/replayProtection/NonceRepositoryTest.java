package tako.design.replayProtection;


import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tako.design.BackendApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class NonceRepositoryTest {

    @Autowired
    private NonceApi nonce;



    @Test
    public void simpleNonceApiTest () {
        String n1 = nonce.getNonce();
        String n2 = nonce.getNonce();
        Assert.assertTrue(nonce.checkNonce(n1));
        Assert.assertFalse(nonce.checkNonce(n1));
        Assert.assertNotEquals(n1, n2);
    }

    @Test
    public void NonceApiSpeedTest () {
        long _20ms = 200;
        long startTime = System.currentTimeMillis();

        int testLength = 100;
        String[] nonces = new String[testLength];

        for (int i=0; i<testLength; i++) {
            nonces[i] = nonce.getNonce();
        }

        for (int i=0; i<testLength; i++) {
            Assert.assertTrue(nonce.checkNonce(nonces[i]));
        }

        for (int i=0; i<testLength; i++) {
            Assert.assertFalse(nonce.checkNonce(nonces[i]));
        }

        long endTime = System.currentTimeMillis();
        long avgTime = (endTime - startTime) / testLength;
        Assert.assertTrue( avgTime < _20ms);
    }


}
