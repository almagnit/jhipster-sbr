package sbr.converter.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import sbr.converter.domain.Announcement;

/**
 * Spring Data SQL repository for the Announcement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {}
