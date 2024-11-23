package com.susocode.gamelofttest.user.unit.application.usecase;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.susocode.gamelofttest.shared.exception.ObjectNotFoundException;
import com.susocode.gamelofttest.user.application.usecase.FindUserUseCaseImpl;
import com.susocode.gamelofttest.user.domain.model.Campaign;
import com.susocode.gamelofttest.user.domain.model.DoesNotHaveMatcher;
import com.susocode.gamelofttest.user.domain.model.HasMatcher;
import com.susocode.gamelofttest.user.domain.model.LevelMatcher;
import com.susocode.gamelofttest.user.domain.model.Matchers;
import com.susocode.gamelofttest.user.domain.model.User;
import com.susocode.gamelofttest.user.domain.port.input.usecase.UpdateUserUseCase;
import com.susocode.gamelofttest.user.domain.port.output.client.CampaignClient;
import com.susocode.gamelofttest.user.domain.port.output.persistence.UserRepository;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class FindUserUseCaseImplTest {

  @Mock
  private CampaignClient campaignClient;

  @Mock
  private UpdateUserUseCase updateUserUseCase;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private FindUserUseCaseImpl findUserUseCase;

  @Test
  void shouldFindAndUpdateUserWhenCampaignMatches() {
    // Arrange
    var user = new User();
    user.setId(UUID.randomUUID());
    user.setLevel(2);
    user.setCountry("ES");
    user.setInventory(Map.of("item_1", 12));
    user.setActiveCampaigns(new HashSet<>());

    var campaign = new Campaign();
    campaign.setMatchers(new Matchers(
        new LevelMatcher(1, 3),
        new HasMatcher(Set.of("ES"), Set.of("item_1")),
        new DoesNotHaveMatcher(Set.of("item_4"))));

    when(userRepository.findById(user.getId())).thenReturn(Mono.just(user));
    when(campaignClient.getCurrentCampaign()).thenReturn(Mono.just(campaign));
    when(updateUserUseCase.addCampaign(user, campaign))
        .thenReturn(Mono.just(user));

    // Act
    var result = findUserUseCase.findById(user.getId());

    // Assert
    StepVerifier.create(result)
        .expectNextCount(1)
        .verifyComplete();

    verify(userRepository, times(1)).findById(user.getId());
    verify(campaignClient, times(1)).getCurrentCampaign();
    verify(updateUserUseCase, times(1)).addCampaign(user, campaign);
    verifyNoMoreInteractions(userRepository, campaignClient, updateUserUseCase);
  }

  @Test
  void shouldReturnUserWithoutUpdateWhenCampaignDoesNotMatchByLevelMatcher() {
    // Arrange
    var user = new User();
    user.setId(UUID.randomUUID());
    user.setLevel(10);
    user.setActiveCampaigns(new HashSet<>());

    var campaign = new Campaign();
    campaign.setMatchers(new Matchers(new LevelMatcher(1, 3), null, null));

    when(userRepository.findById(user.getId())).thenReturn(Mono.just(user));
    when(campaignClient.getCurrentCampaign()).thenReturn(Mono.just(campaign));

    // Act
    var result = findUserUseCase.findById(user.getId());

    // Assert
    StepVerifier.create(result)
        .expectNextCount(1)
        .verifyComplete();

    verify(userRepository, times(1)).findById(any(UUID.class));
    verify(campaignClient, times(1)).getCurrentCampaign();
    verify(updateUserUseCase, times(0))
        .addCampaign(any(User.class), any(Campaign.class));
    verifyNoMoreInteractions(userRepository, campaignClient);
    verifyNoInteractions(updateUserUseCase);
  }

  @Test
  void shouldHandleObjectNotFoundExceptionWhenUserNotFound() {
    // Arrange
    var userId = UUID.randomUUID();

    when(userRepository.findById(userId)).thenReturn(Mono.empty());

    // Act
    var result = findUserUseCase.findById(userId);

    // Assert
    StepVerifier.create(result)
        .verifyErrorMatches(error -> error.getClass().equals(ObjectNotFoundException.class)
            && ((ObjectNotFoundException) error).getKey().equals("USER_PROFILE_NOT_FOUND"));

    verify(userRepository, times(1)).findById(userId);
    verifyNoMoreInteractions(userRepository);
    verifyNoInteractions(campaignClient, updateUserUseCase);
  }

}