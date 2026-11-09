package dev.backendlab.goldguard.api;

import dev.backendlab.goldguard.application.DailyRewardClaimService;
import dev.backendlab.goldguard.config.SecurityConfig;
import dev.backendlab.goldguard.exception.BusinessException;
import dev.backendlab.goldguard.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DailyRewardClaimController.class)
@Import(SecurityConfig.class)
@ActiveProfiles("test")
class DailyRewardClaimControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    DailyRewardClaimService dailyRewardClaimService;

    @Test
    void 로그인한_계정이_유저계정이면_일일_보상_API에_접근이_가능하다() throws Exception{
        // given
        // when // then
        mockMvc.perform(post("/api/v1/users/daily-reward")
                        .with(httpBasic("user", "password"))
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    void 일일보상을_두_번_받게되면_중복예외가_발생한다() throws Exception {
        // given
        willThrow(new BusinessException(ErrorCode.REWARD_ALREADY_CLAIMED))
                .given(dailyRewardClaimService)
                .claimDailyReward(anyString(), any(LocalDate.class));
        // when // then
        mockMvc.perform(post("/api/v1/users/daily-reward")
                        .with(httpBasic("user", "password"))
                        .with(csrf()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.code").value("REWARD_ALREADY_CLAIMED"));
    }

}