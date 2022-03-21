package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class SpecialAnnouncementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialAnnouncement.class);
        SpecialAnnouncement specialAnnouncement1 = new SpecialAnnouncement();
        specialAnnouncement1.setId(1L);
        SpecialAnnouncement specialAnnouncement2 = new SpecialAnnouncement();
        specialAnnouncement2.setId(specialAnnouncement1.getId());
        assertThat(specialAnnouncement1).isEqualTo(specialAnnouncement2);
        specialAnnouncement2.setId(2L);
        assertThat(specialAnnouncement1).isNotEqualTo(specialAnnouncement2);
        specialAnnouncement1.setId(null);
        assertThat(specialAnnouncement1).isNotEqualTo(specialAnnouncement2);
    }
}
