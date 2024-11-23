package com.susocode.gamelofttest.user.unit.application.usecase;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.susocode.gamelofttest.user.application.usecase.UpdateUserUseCaseImpl;
import com.susocode.gamelofttest.user.domain.model.Campaign;
import com.susocode.gamelofttest.user.domain.model.User;
import com.susocode.gamelofttest.user.domain.port.output.persistence.UserRepository;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Objects;
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
class UpdateUserUseCaseImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UpdateUserUseCaseImpl updateUserUseCase;

  @Test
  void shouldAddCampaignWhenCampaignNotAlreadyExists() {
    // Arrange
    User user = new User();
    user.setId(UUID.randomUUID());
    user.setActiveCampaigns(new HashSet<>());

    Campaign campaign = new Campaign();
    campaign.setName("New Campaign");

    when(userRepository.update(user)).thenReturn(Mono.just(user));

    // Act
    var result = updateUserUseCase.addCampaign(user, campaign);

    // Assert
    StepVerifier.create(result)
        .expectNextMatches(updatedUser ->
            updatedUser.getActiveCampaigns().contains("New Campaign")
                && Objects.nonNull(updatedUser.getModified()))
        .verifyComplete();

    verify(userRepository, times(1)).update(user);
    verifyNoMoreInteractions(userRepository);
  }

  @Test
  void shouldNotAddCampaignWhenCampaignAlreadyExists() {
    // Arrange
    var user = new User();
    user.setId(UUID.randomUUID());
    user.setModified(ZonedDateTime.parse("2024-11-21T00:00:00Z"));
    user.setActiveCampaigns(Set.of("New Campaign"));

    var campaign = new Campaign();
    campaign.setName("New Campaign");

    // Act
    var result = updateUserUseCase.addCampaign(user, campaign);

    // Assert
    StepVerifier.create(result)
        .expectNextMatches(updatedUser ->
            updatedUser.getActiveCampaigns().size() == 1
                && Objects.equals(user.getModified(), updatedUser.getModified()))
        .verifyComplete();

    verifyNoInteractions(userRepository);
  }

}