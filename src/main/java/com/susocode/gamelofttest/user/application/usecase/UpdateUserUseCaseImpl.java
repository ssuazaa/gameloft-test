package com.susocode.gamelofttest.user.application.usecase;

import com.susocode.gamelofttest.user.domain.matcher.MatcherEvaluator;
import com.susocode.gamelofttest.user.domain.model.Campaign;
import com.susocode.gamelofttest.user.domain.model.User;
import com.susocode.gamelofttest.user.domain.port.input.usecase.UpdateUserUseCase;
import com.susocode.gamelofttest.user.domain.port.output.persistence.UserRepository;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

  private final UserRepository userRepository;

  @Override
  public Mono<User> addCampaign(User user, Campaign campaign) {
    if (!MatcherEvaluator.campaignNotAlreadyExistsMatcher.evaluate(user, campaign)) {
      return Mono.just(user);
    }
    user.getActiveCampaigns().add(campaign.getName());
    user.setModified(ZonedDateTime.now());
    return userRepository.update(user);
  }

}
