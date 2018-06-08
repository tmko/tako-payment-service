package tako.design.paymentGatway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChargeEntity {

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