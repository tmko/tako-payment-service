package tako.design.replayProtection;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tako.design.BackendApplication;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class NonceApiTest {

    @Autowired
    private NonceRepository nonceRepository;

    @Before
    public void setup () {
        long time = System.currentTimeMillis();
        nonceRepository.save(
                NonceEntity.builder()
                .nonce("some_nonce")
                .status('R')
                .unixTime(time)
                .build()
        );
    }

    @After
    public void tireDown () {
        nonceRepository.deleteAll();
    }

    @Test
    public void simpleNonceSaving () {
        int rowUpated;

        rowUpated = nonceRepository.consumeNonce("some_nonce");
        assertEquals(1, rowUpated);

        rowUpated = nonceRepository.consumeNonce("some_nonce");
        assertEquals(0, rowUpated);
    }


}
