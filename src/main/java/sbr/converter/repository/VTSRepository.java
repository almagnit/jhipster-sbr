package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.VTS;

/**
 * Spring Data SQL repository for the VTS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VTSRepository extends JpaRepository<VTS, Long> {}
