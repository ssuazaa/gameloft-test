package com.susocode.gamelofttest.user.infrastructure.spring.config.bean;

import com.susocode.gamelofttest.user.application.usecase.FindUserUseCaseImpl;
import com.susocode.gamelofttest.user.application.usecase.UpdateUserUseCaseImpl;
import com.susocode.gamelofttest.user.domain.port.input.usecase.FindUserUseCase;
import com.susocode.gamelofttest.user.domain.port.input.usecase.UpdateUserUseCase;
import com.susocode.gamelofttest.user.domain.port.output.client.CampaignClient;
import com.susocode.gamelofttest.user.domain.port.output.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfig {

  private final CampaignClient campaignClient;
  private final UserRepository userRepository;

  @Bean
  public FindUserUseCase findUserUseCase() {
    return new FindUserUseCaseImpl(campaignClient, updateUserUseCase(), userRepository);
  }

  @Bean
  public UpdateUserUseCase updateUserUseCase() {
    return new UpdateUserUseCaseImpl(userRepository);
  }

}
