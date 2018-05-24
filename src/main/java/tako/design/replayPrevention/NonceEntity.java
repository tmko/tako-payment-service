package tako.design.replayPrevention;

import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity(name = "nonces")
public class NonceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private boolean used = false;

    @Column
    private long unixTime = Instant.now().toEpochMilli();

    @Column
    @Convert(converter = UUIDConverter.class)
    private UUID uuid = UUID.randomUUID();
}
