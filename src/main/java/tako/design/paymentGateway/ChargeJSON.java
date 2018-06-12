package tako.design.paymentGateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChargeJSON {

    @JsonProperty
    private Float amount;

    @JsonProperty
    private String currency = "CAD";

    @JsonProperty
    private Integer card_expiry_month;

    @JsonProperty
    private Integer card_expiry_year;

    @JsonProperty
    private String card_number;

    @JsonProperty
    private Integer cvv2;
}