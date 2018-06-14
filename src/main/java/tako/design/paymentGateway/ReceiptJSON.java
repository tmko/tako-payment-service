package tako.design.paymentGateway;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ReceiptJSON {

    @JsonProperty
    private Float amount;

    @JsonProperty
    private String card_type;

    @JsonProperty
    private String card_suffix;

    @JsonProperty
    private Integer id;

    @JsonProperty
    private String transaction_id;

    @JsonProperty
    private Boolean transaction_success;

    @JsonProperty
    private String transaction_result;

    @JsonProperty
    private String transaction_message;

    @JsonProperty
    private Long transaction_time;

    @JsonProperty
    private String transaction_type;


}