package tako.design.replayPrevention;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity(name = "nonces")
public class NonceDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Boolean used = false;

    @Column
    private Long unixTime = Instant.now().toEpochMilli();

    @Column
    @Convert(converter = UUIDConverter.class)
    private UUID uuid = UUID.randomUUID();
}
