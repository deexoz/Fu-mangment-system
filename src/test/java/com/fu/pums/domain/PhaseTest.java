package com.fu.pums.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fu.pums.web.rest.TestUtil;

public class PhaseTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Phase.class);
        Phase phase1 = new Phase();
        phase1.setId(1L);
        Phase phase2 = new Phase();
        phase2.setId(phase1.getId());
        assertThat(phase1).isEqualTo(phase2);
        phase2.setId(2L);
        assertThat(phase1).isNotEqualTo(phase2);
        phase1.setId(null);
        assertThat(phase1).isNotEqualTo(phase2);
    }
}
