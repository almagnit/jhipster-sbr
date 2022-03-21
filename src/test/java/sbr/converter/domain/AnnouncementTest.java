package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class AnnouncementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Announcement.class);
        Announcement announcement1 = new Announcement();
        announcement1.setId(1L);
        Announcement announcement2 = new Announcement();
        announcement2.setId(announcement1.getId());
        assertThat(announcement1).isEqualTo(announcement2);
        announcement2.setId(2L);
        assertThat(announcement1).isNotEqualTo(announcement2);
        announcement1.setId(null);
        assertThat(announcement1).isNotEqualTo(announcement2);
    }
}
