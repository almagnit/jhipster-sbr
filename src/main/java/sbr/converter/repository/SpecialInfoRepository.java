package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.SpecialInfo;

/**
 * Spring Data SQL repository for the SpecialInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialInfoRepository extends JpaRepository<SpecialInfo, Long> {}
