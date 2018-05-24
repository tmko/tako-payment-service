package tako.design.replayProtection;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
public interface NonceRepository extends CrudRepository<NonceEntity, Long> {


    @Modifying
    @Transactional
    @Query(
            value="Update nonce_entity set status = 'U' where nonce = :nonce and status = 'R'",
            nativeQuery=true
    )
    int consumeNonce (@Param("nonce") String nonce);

}
