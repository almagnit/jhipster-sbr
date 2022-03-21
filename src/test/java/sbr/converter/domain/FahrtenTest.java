package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class FahrtenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fahrten.class);
        Fahrten fahrten1 = new Fahrten();
        fahrten1.setId(1L);
        Fahrten fahrten2 = new Fahrten();
        fahrten2.setId(fahrten1.getId());
        assertThat(fahrten1).isEqualTo(fahrten2);
        fahrten2.setId(2L);
        assertThat(fahrten1).isNotEqualTo(fahrten2);
        fahrten1.setId(null);
        assertThat(fahrten1).isNotEqualTo(fahrten2);
    }
}
