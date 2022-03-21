package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class ZielTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ziel.class);
        Ziel ziel1 = new Ziel();
        ziel1.setId(1L);
        Ziel ziel2 = new Ziel();
        ziel2.setId(ziel1.getId());
        assertThat(ziel1).isEqualTo(ziel2);
        ziel2.setId(2L);
        assertThat(ziel1).isNotEqualTo(ziel2);
        ziel1.setId(null);
        assertThat(ziel1).isNotEqualTo(ziel2);
    }
}
