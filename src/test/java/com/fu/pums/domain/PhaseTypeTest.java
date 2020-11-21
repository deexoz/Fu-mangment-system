package com.fu.pums.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fu.pums.web.rest.TestUtil;

public class PhaseTypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PhaseType.class);
        PhaseType phaseType1 = new PhaseType();
        phaseType1.setId(1L);
        PhaseType phaseType2 = new PhaseType();
        phaseType2.setId(phaseType1.getId());
        assertThat(phaseType1).isEqualTo(phaseType2);
        phaseType2.setId(2L);
        assertThat(phaseType1).isNotEqualTo(phaseType2);
        phaseType1.setId(null);
        assertThat(phaseType1).isNotEqualTo(phaseType2);
    }
}
