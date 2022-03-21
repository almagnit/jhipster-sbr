package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class GPSTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GPS.class);
        GPS gPS1 = new GPS();
        gPS1.setId(1L);
        GPS gPS2 = new GPS();
        gPS2.setId(gPS1.getId());
        assertThat(gPS1).isEqualTo(gPS2);
        gPS2.setId(2L);
        assertThat(gPS1).isNotEqualTo(gPS2);
        gPS1.setId(null);
        assertThat(gPS1).isNotEqualTo(gPS2);
    }
}
