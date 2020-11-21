package com.fu.pums.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fu.pums.web.rest.TestUtil;

public class SupervisorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Supervisor.class);
        Supervisor supervisor1 = new Supervisor();
        supervisor1.setId(1L);
        Supervisor supervisor2 = new Supervisor();
        supervisor2.setId(supervisor1.getId());
        assertThat(supervisor1).isEqualTo(supervisor2);
        supervisor2.setId(2L);
        assertThat(supervisor1).isNotEqualTo(supervisor2);
        supervisor1.setId(null);
        assertThat(supervisor1).isNotEqualTo(supervisor2);
    }
}
