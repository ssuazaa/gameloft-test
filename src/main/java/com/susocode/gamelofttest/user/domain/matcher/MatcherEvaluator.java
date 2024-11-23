package com.susocode.gamelofttest.user.domain.matcher;

import com.susocode.gamelofttest.user.domain.model.Campaign;
import com.susocode.gamelofttest.user.domain.model.User;

@FunctionalInterface
public interface MatcherEvaluator {

  MatcherEvaluator levelMatcher = (user, campaign) -> {
    var userLevel = user.getLevel();
    var minLevel = campaign.getMatchers().getLevel().getMin();
    var maxLevel = campaign.getMatchers().getLevel().getMax();
    return userLevel >= minLevel && userLevel <= maxLevel;
  };

  MatcherEvaluator hasCountryMatcher = (user, campaign) -> {
    var userCountry = user.getCountry();
    var allowedCountries = campaign.getMatchers().getHas().getCountry();
    return allowedCountries.contains(userCountry);
  };

  MatcherEvaluator hasItemsMatcher = (user, campaign) -> {
    var userItems = user.getInventory().keySet();
    var requiredItems = campaign.getMatchers().getHas().getItems();
    return userItems.containsAll(requiredItems);
  };

  MatcherEvaluator doesNotHaveItemsMatcher = (user, campaign) -> {
    var userItems = user.getInventory().keySet();
    var excludedItems = campaign.getMatchers().getDoesNotHave().getItems();
    return excludedItems.stream().noneMatch(userItems::contains);
  };

  MatcherEvaluator campaignNotAlreadyExistsMatcher = (user, campaign) ->
      user.getActiveCampaigns().stream()
          .noneMatch(activeCampaign -> activeCampaign.equalsIgnoreCase(campaign.getName()));

  MatcherEvaluator compositeMatcher = (user, campaign) ->
      levelMatcher.evaluate(user, campaign)
          && hasCountryMatcher.evaluate(user, campaign)
          && hasItemsMatcher.evaluate(user, campaign)
          && doesNotHaveItemsMatcher.evaluate(user, campaign)
          && campaignNotAlreadyExistsMatcher.evaluate(user, campaign);

  boolean evaluate(User user, Campaign campaign);

}
