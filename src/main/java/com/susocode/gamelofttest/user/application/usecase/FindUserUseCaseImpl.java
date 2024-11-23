package com.susocode.gamelofttest.user.application.usecase;

import com.susocode.gamelofttest.shared.exception.ObjectNotFoundException;
import com.susocode.gamelofttest.user.domain.matcher.MatcherEvaluator;
import com.susocode.gamelofttest.user.domain.model.Campaign;
import com.susocode.gamelofttest.user.domain.model.User;
import com.susocode.gamelofttest.user.domain.port.input.usecase.FindUserUseCase;
import com.susocode.gamelofttest.user.domain.port.input.usecase.UpdateUserUseCase;
import com.susocode.gamelofttest.user.domain.port.output.client.CampaignClient;
import com.susocode.gamelofttest.user.domain.port.output.persistence.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindUserUseCaseImpl implements FindUserUseCase {

  private final CampaignClient campaignClient;
  private final UpdateUserUseCase updateUserUseCase;
  private final UserRepository userRepository;

  @Override
  public Mono<User> findById(UUID id) {
    return userRepository.findById(id)
        .switchIfEmpty(Mono.error(new ObjectNotFoundException("USER_PROFILE_NOT_FOUND",
            String.format("User profile with profile_id '%s' not exists.", id))))
        .flatMap(this::validateAndUpdateUser);
  }

  private Mono<User> validateAndUpdateUser(User user) {
    return campaignClient.getCurrentCampaign()
        .flatMap((Campaign campaign) -> {
          if (MatcherEvaluator.compositeMatcher.evaluate(user, campaign)) {
            return updateUserUseCase.addCampaign(user, campaign)
                .thenReturn(user);
          }
          return Mono.just(user);
        });
  }

}
