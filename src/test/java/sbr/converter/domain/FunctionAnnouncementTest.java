package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class FunctionAnnouncementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FunctionAnnouncement.class);
        FunctionAnnouncement functionAnnouncement1 = new FunctionAnnouncement();
        functionAnnouncement1.setId(1L);
        FunctionAnnouncement functionAnnouncement2 = new FunctionAnnouncement();
        functionAnnouncement2.setId(functionAnnouncement1.getId());
        assertThat(functionAnnouncement1).isEqualTo(functionAnnouncement2);
        functionAnnouncement2.setId(2L);
        assertThat(functionAnnouncement1).isNotEqualTo(functionAnnouncement2);
        functionAnnouncement1.setId(null);
        assertThat(functionAnnouncement1).isNotEqualTo(functionAnnouncement2);
    }
}
