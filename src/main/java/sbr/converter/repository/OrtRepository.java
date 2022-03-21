package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.Ort;

/**
 * Spring Data SQL repository for the Ort entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrtRepository extends JpaRepository<Ort, Long> {}
