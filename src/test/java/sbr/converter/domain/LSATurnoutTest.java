package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class LSATurnoutTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LSATurnout.class);
        LSATurnout lSATurnout1 = new LSATurnout();
        lSATurnout1.setId(1L);
        LSATurnout lSATurnout2 = new LSATurnout();
        lSATurnout2.setId(lSATurnout1.getId());
        assertThat(lSATurnout1).isEqualTo(lSATurnout2);
        lSATurnout2.setId(2L);
        assertThat(lSATurnout1).isNotEqualTo(lSATurnout2);
        lSATurnout1.setId(null);
        assertThat(lSATurnout1).isNotEqualTo(lSATurnout2);
    }
}
