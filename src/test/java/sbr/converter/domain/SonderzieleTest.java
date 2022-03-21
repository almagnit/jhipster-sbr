package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class SonderzieleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sonderziele.class);
        Sonderziele sonderziele1 = new Sonderziele();
        sonderziele1.setId(1L);
        Sonderziele sonderziele2 = new Sonderziele();
        sonderziele2.setId(sonderziele1.getId());
        assertThat(sonderziele1).isEqualTo(sonderziele2);
        sonderziele2.setId(2L);
        assertThat(sonderziele1).isNotEqualTo(sonderziele2);
        sonderziele1.setId(null);
        assertThat(sonderziele1).isNotEqualTo(sonderziele2);
    }
}
