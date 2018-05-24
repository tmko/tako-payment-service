package tako.design.replayProtection;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class NonceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false, length = 100)
    private String nonce;

    @Column(nullable = false)
    private Character status;

    @Column(nullable = false)
    private Long unixTime;
}
