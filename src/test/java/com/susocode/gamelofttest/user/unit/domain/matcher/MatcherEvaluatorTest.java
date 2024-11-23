package com.susocode.gamelofttest.user.unit.domain.matcher;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.susocode.gamelofttest.user.domain.matcher.MatcherEvaluator;
import com.susocode.gamelofttest.user.domain.model.Campaign;
import com.susocode.gamelofttest.user.domain.model.DoesNotHaveMatcher;
import com.susocode.gamelofttest.user.domain.model.HasMatcher;
import com.susocode.gamelofttest.user.domain.model.LevelMatcher;
import com.susocode.gamelofttest.user.domain.model.Matchers;
import com.susocode.gamelofttest.user.domain.model.User;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class MatcherEvaluatorTest {

  @Test
  void levelMatcher_whenUserLevelIsWithinBounds_shouldReturnTrue() {
    // Arrange
    var user = new User();
    user.setLevel(5);

    var campaign = new Campaign();
    campaign.setMatchers(new Matchers(
        new LevelMatcher(1, 7),
        null,
        null));

    // Act
    var result = MatcherEvaluator.levelMatcher.evaluate(user, campaign);

    // Assert
    assertTrue(result);
  }

  @Test
  void levelMatcher_whenUserLevelIsOutOfBounds_shouldReturnFalse() {
    // Arrange
    var user = new User();
    user.setLevel(2);

    var campaign = new Campaign();
    campaign.setMatchers(new Matchers(
        new LevelMatcher(4, 7),
        null,
        null));

    // Act
    var result = MatcherEvaluator.levelMatcher.evaluate(user, campaign);

    // Assert
    assertFalse(result);
  }

  @Test
  void hasCountryMatcher_whenUserCountryIsAllowed_shouldReturnTrue() {
    // Arrange
    var user = new User();
    user.setCountry("ES");

    var campaign = new Campaign();
    campaign.setMatchers(new Matchers(
        null,
        new HasMatcher(Set.of("USA", "ES"), Collections.emptySet()),
        null
    ));

    // Act
    var result = MatcherEvaluator.hasCountryMatcher.evaluate(user, campaign);

    // Assert
    assertTrue(result);
  }

  @Test
  void hasCountryMatcher_whenUserCountryIsNotAllowed_shouldReturnFalse() {
    // Arrange
    var user = new User();
    user.setCountry("ES");

    var campaign = new Campaign();
    campaign.setMatchers(new Matchers(
        null,
        new HasMatcher(Set.of("US", "CA"), Collections.emptySet()),
        null
    ));

    // Act
    var result = MatcherEvaluator.hasCountryMatcher.evaluate(user, campaign);

    // Assert
    assertFalse(result);
  }

  @Test
  void hasItemsMatcher_whenUserHasAllRequiredItems_shouldReturnTrue() {
    // Arrange
    var user = new User();
    user.setInventory(Map.of("item_1", 2, "item_2", 33));

    var campaign = new Campaign();
    campaign.setMatchers(new Matchers(
        null,
        new HasMatcher(Collections.emptySet(), Set.of("item_1")),
        null
    ));

    // Act
    var result = MatcherEvaluator.hasItemsMatcher.evaluate(user, campaign);

    // Assert
    assertTrue(result);
  }

  @Test
  void hasItemsMatcher_whenUserDoesNotHaveAllRequiredItems_shouldReturnFalse() {
    // Arrange
    var user = new User();
    user.setInventory(Map.of("item_2", 33));

    var campaign = new Campaign();
    campaign.setMatchers(new Matchers(
        null,
        new HasMatcher(Collections.emptySet(), Set.of("item_1")),
        null
    ));

    // Act
    var result = MatcherEvaluator.hasItemsMatcher.evaluate(user, campaign);

    // Assert
    assertFalse(result);
  }

  @Test
  void doesNotHaveItemsMatcher_whenUserDoesNotHaveANotAllowedItem_shouldReturnTrue() {
    // Arrange
    var user = new User();
    user.setInventory(Map.of("item_1", 2, "item_2", 33));

    var campaign = new Campaign();
    campaign.setMatchers(new Matchers(
        null,
        null,
        new DoesNotHaveMatcher(Set.of("item_3"))
    ));

    // Act
    var result = MatcherEvaluator.doesNotHaveItemsMatcher.evaluate(user, campaign);

    // Assert
    assertTrue(result);
  }

  @Test
  void doesNotHaveItemsMatcher_whenUserHasANotAllowedItem_shouldReturnFalse() {
    // Arrange
    var user = new User();
    user.setInventory(Map.of("item_1", 2, "item_2", 33));

    var campaign = new Campaign();
    campaign.setMatchers(new Matchers(
        null,
        null,
        new DoesNotHaveMatcher(Set.of("item_2"))
    ));

    // Act
    var result = MatcherEvaluator.doesNotHaveItemsMatcher.evaluate(user, campaign);

    // Assert
    assertFalse(result);
  }

  @Test
  void campaignNotAlreadyExistsMatcher_whenCampaignDoesNotExist_shouldReturnTrue() {
    // Arrange
    var user = new User();
    user.setActiveCampaigns(Set.of("Campaign1", "Campaign2"));

    var campaign = new Campaign();
    campaign.setName("Campaign3");

    // Act
    var result = MatcherEvaluator.campaignNotAlreadyExistsMatcher.evaluate(user, campaign);

    // Assert
    assertTrue(result);
  }

  @Test
  void campaignAlreadyExistsMatcher_whenCampaignExist_shouldReturnFalse() {
    // Arrange
    var user = new User();
    user.setActiveCampaigns(Set.of("Campaign1", "Campaign2"));

    var campaign = new Campaign();
    campaign.setName("Campaign2");

    // Act
    var result = MatcherEvaluator.campaignNotAlreadyExistsMatcher.evaluate(user, campaign);

    // Assert
    assertFalse(result);
  }

  @Test
  void compositeMatcher_whenAllConditionsAreMet_shouldReturnTrue() {
    // Arrange
    var user = new User();
    user.setLevel(2);
    user.setCountry("ES");
    user.setInventory(Map.of("item_1", 22, "item_3", 12));
    user.setActiveCampaigns(Set.of("Campaign1"));

    var campaign = new Campaign();
    campaign.setName("Campaign2");
    campaign.setMatchers(new Matchers(
        new LevelMatcher(1, 5),
        new HasMatcher(Set.of("US", "ES"), Set.of("item_1")),
        new DoesNotHaveMatcher(Set.of("item_4"))
    ));

    // Act
    var result = MatcherEvaluator.compositeMatcher.evaluate(user, campaign);

    // Assert
    assertTrue(result);
  }

  @Test
  void compositeMatcher_whenAllConditionsAreNotMet_shouldReturnFalse() {
    // Arrange
    var user = new User();
    user.setLevel(2);
    user.setCountry("ES");
    user.setInventory(Map.of("item_1", 22, "item_3", 12));
    user.setActiveCampaigns(Set.of("Campaign1"));

    var campaign = new Campaign();
    campaign.setName("Campaign1");
    campaign.setMatchers(new Matchers(
        new LevelMatcher(3, 5),
        new HasMatcher(Set.of("US"), Set.of("item_2")),
        new DoesNotHaveMatcher(Set.of("item_1"))
    ));

    // Act
    var result = MatcherEvaluator.compositeMatcher.evaluate(user, campaign);

    // Assert
    assertFalse(result);
  }

}
