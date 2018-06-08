package tako.design.order;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import tako.design.replayPrevention.UuidTimeBoundNonce;

import java.util.regex.Pattern;

@Data
public class OrderReq {

    private static Pattern pattern = Pattern.compile("\\d{10}");

    @Autowired
    static UuidTimeBoundNonce nonceCreator;

    @JsonProperty("v")
    private Integer version;

    @JsonProperty("n")
    private String nonce;

    @JsonProperty("p")
    private String phone;

    @JsonProperty("s")
    private String street;

    @JsonProperty("c")
    private String city;

    @JsonProperty("z")
    private String zip;

    @JsonProperty("o")
    private String orderItems;

    public boolean isValid () {
        if ( version == null ) return false;
        if ( nonce == null ) return false;
        if ( phone == null ) return false;
        if ( street == null ) return false;
        if ( city == null ) return  false;
        if ( orderItems == null ) return false;

        //UUID uuid = UUID.fromString(nonce);

        boolean o = true;
        o &= version > 0;
        //o &= nonceCreator.validateAndConsume(uuid);
        o &= pattern.matcher(phone).matches();
        o &= orderItems.length() > 0;
        return o;
    }

}
