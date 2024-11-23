package com.susocode.gamelofttest.user.domain.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HasMatcher {

  private Set<String> country;
  private Set<String> items;

}
