package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class OrtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ort.class);
        Ort ort1 = new Ort();
        ort1.setId(1L);
        Ort ort2 = new Ort();
        ort2.setId(ort1.getId());
        assertThat(ort1).isEqualTo(ort2);
        ort2.setId(2L);
        assertThat(ort1).isNotEqualTo(ort2);
        ort1.setId(null);
        assertThat(ort1).isNotEqualTo(ort2);
    }
}
