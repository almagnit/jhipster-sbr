package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.Sonderziele;

/**
 * Spring Data SQL repository for the Sonderziele entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SonderzieleRepository extends JpaRepository<Sonderziele, Long> {}
