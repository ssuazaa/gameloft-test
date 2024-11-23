package com.susocode.gamelofttest.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Matchers {

  private LevelMatcher level;
  private HasMatcher has;
  private DoesNotHaveMatcher doesNotHave;

}
