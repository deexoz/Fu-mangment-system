package com.fu.pums.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.fu.pums.web.rest.TestUtil;

public class ResultDataTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResultData.class);
        ResultData resultData1 = new ResultData();
        resultData1.setId(1L);
        ResultData resultData2 = new ResultData();
        resultData2.setId(resultData1.getId());
        assertThat(resultData1).isEqualTo(resultData2);
        resultData2.setId(2L);
        assertThat(resultData1).isNotEqualTo(resultData2);
        resultData1.setId(null);
        assertThat(resultData1).isNotEqualTo(resultData2);
    }
}
