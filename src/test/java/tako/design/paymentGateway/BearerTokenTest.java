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
        tokenManager.getToken();

        Assert.assertTrue( tokenManager != null );
        Assert.assertFalse( tokenManager.getToken().getAccess_token().isEmpty() );
    }

}
