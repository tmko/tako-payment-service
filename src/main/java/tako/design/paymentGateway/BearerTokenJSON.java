package tako.design.paymentGateway;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
class BearerTokenJSON {

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

    @JsonIgnore
    private Long createdTime = now();

    public Long lifeInSec () {
        return now() - (createdTime + expires_in);
    }

    private Long now () {
        return TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis());
    }
}
