package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.GPS;

/**
 * Spring Data SQL repository for the GPS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GPSRepository extends JpaRepository<GPS, Long> {}
