package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.Ziel;

/**
 * Spring Data SQL repository for the Ziel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZielRepository extends JpaRepository<Ziel, Long> {}
