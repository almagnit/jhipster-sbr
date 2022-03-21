package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.LSATurnout;

/**
 * Spring Data SQL repository for the LSATurnout entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LSATurnoutRepository extends JpaRepository<LSATurnout, Long> {}
