package com.susocode.gamelofttest.user.domain.model;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Campaign {

  private String game;
  private String name;
  private Double priority;
  private Matchers matchers;
  private ZonedDateTime startDate;
  private ZonedDateTime endDate;
  private Boolean enable;
  private ZonedDateTime lastUpdate;

}
