package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.FunctionAnnouncement;

/**
 * Spring Data SQL repository for the FunctionAnnouncement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FunctionAnnouncementRepository extends JpaRepository<FunctionAnnouncement, Long> {}
