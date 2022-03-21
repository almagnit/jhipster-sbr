package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.Fahrten;

/**
 * Spring Data SQL repository for the Fahrten entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FahrtenRepository extends JpaRepository<Fahrten, Long> {}
