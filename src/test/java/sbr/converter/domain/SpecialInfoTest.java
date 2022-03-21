package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class SpecialInfoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SpecialInfo.class);
        SpecialInfo specialInfo1 = new SpecialInfo();
        specialInfo1.setId(1L);
        SpecialInfo specialInfo2 = new SpecialInfo();
        specialInfo2.setId(specialInfo1.getId());
        assertThat(specialInfo1).isEqualTo(specialInfo2);
        specialInfo2.setId(2L);
        assertThat(specialInfo1).isNotEqualTo(specialInfo2);
        specialInfo1.setId(null);
        assertThat(specialInfo1).isNotEqualTo(specialInfo2);
    }
}
