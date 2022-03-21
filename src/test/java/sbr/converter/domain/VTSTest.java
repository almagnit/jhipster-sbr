package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class VTSTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VTS.class);
        VTS vTS1 = new VTS();
        vTS1.setId(1L);
        VTS vTS2 = new VTS();
        vTS2.setId(vTS1.getId());
        assertThat(vTS1).isEqualTo(vTS2);
        vTS2.setId(2L);
        assertThat(vTS1).isNotEqualTo(vTS2);
        vTS1.setId(null);
        assertThat(vTS1).isNotEqualTo(vTS2);
    }
}
