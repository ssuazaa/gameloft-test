package com.susocode.gamelofttest.user.domain.port.input.usecase;

import com.susocode.gamelofttest.user.domain.model.Campaign;
import com.susocode.gamelofttest.user.domain.model.User;
import reactor.core.publisher.Mono;

public interface UpdateUserUseCase {

  Mono<User> addCampaign(User user, Campaign campaign);
  
}
