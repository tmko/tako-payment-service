package tako.design.paymentGatway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BearerTokenEntity {

    @JsonProperty
    private String access_token;

    @JsonProperty
    private String token_type;

    @JsonProperty
    private String merchant_id;

    @JsonProperty
    private String scope;

    @JsonProperty
    private Integer expires_in;


}
