package sbr.converter.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import sbr.converter.web.rest.TestUtil;

class FunctionTextTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FunctionText.class);
        FunctionText functionText1 = new FunctionText();
        functionText1.setId(1L);
        FunctionText functionText2 = new FunctionText();
        functionText2.setId(functionText1.getId());
        assertThat(functionText1).isEqualTo(functionText2);
        functionText2.setId(2L);
        assertThat(functionText1).isNotEqualTo(functionText2);
        functionText1.setId(null);
        assertThat(functionText1).isNotEqualTo(functionText2);
    }
}
