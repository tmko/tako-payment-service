package tako.design.paymentGateway;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tako.design.BackendApplication;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BackendApplication.class)
public class CashierTest {


    @Autowired
    Cashier cashier;

    @Test
    public void simpleCharge () {
        ChargeJSON charge = new ChargeJSON();
        charge.setAmount(12.0f);
        charge.setCard_expiry_month(12);
        charge.setCard_expiry_year(20);
        charge.setCard_number("4111111111111111");
        charge.setCvv2(595);

        Optional<ReceiptJSON> receipt = cashier.ask(charge);
        Assert.assertTrue( receipt.isPresent() );
        Assert.assertFalse( receipt.get().getTransaction_success() );
        Assert.assertEquals( receipt.get().getCard_type(), "VISA" );
        Assert.assertEquals( receipt.get().getTransaction_type(), "SALE" );
    }

}
