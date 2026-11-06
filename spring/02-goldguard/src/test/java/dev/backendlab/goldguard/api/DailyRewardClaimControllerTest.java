package dev.backendlab.goldguard.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DailyRewardClaimControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void 로그인한_계정이_유저계정이면_일일_보상_API에_접근이_가능하다() throws Exception{
        // given
        // when // then
        mockMvc.perform(get("/api/v1/users/daily-reward")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isOk());


    }

}