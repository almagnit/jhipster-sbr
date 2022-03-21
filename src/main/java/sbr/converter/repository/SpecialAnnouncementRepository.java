package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.SpecialAnnouncement;

/**
 * Spring Data SQL repository for the SpecialAnnouncement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SpecialAnnouncementRepository extends JpaRepository<SpecialAnnouncement, Long> {}
