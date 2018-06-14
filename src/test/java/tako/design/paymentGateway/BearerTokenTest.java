package tako.design.paymentGateway;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tako.design.BackendApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class BearerTokenTest {


    @Autowired
    BearerTokenManager tokenManager;

    @Test
    public void simpleGetToken () {
        String token1 = tokenManager.getToken();
        Assert.assertTrue( token1 != null );
        Assert.assertFalse( token1.isEmpty() );

        String token2 = tokenManager.getToken();
        Assert.assertEquals(token1,token2);

        System.out.println(token1);
    }

}
