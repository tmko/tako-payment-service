package tako.design.replayPrevention;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface NoncesRepository extends CrudRepository<NonceEntity, Long> {

    Optional<NonceEntity> findFirstByUuid (UUID uuid);


    @Modifying
    @Transactional
    @Query ( "UPDATE nonces SET used = true where used = false and uuid = :uuid" )
    int consumeNonce (@Param("uuid") UUID uuid);


}