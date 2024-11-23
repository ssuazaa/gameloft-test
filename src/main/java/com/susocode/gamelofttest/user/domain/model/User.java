package com.susocode.gamelofttest.user.domain.model;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User {

  private UUID id;
  private String credential;
  private ZonedDateTime created;
  private ZonedDateTime modified;
  private ZonedDateTime lastSession;
  private Integer totalSpent;
  private Integer totalRefund;
  private Integer totalTransactions;
  private ZonedDateTime lastPurchase;
  private Set<String> activeCampaigns;
  private List<Device> devices;
  private Integer level;
  private Integer xp;
  private Integer totalPlaytime;
  private String country;
  private String language;
  private ZonedDateTime birthdate;
  private String gender;
  private Map<String, Integer> inventory;
  private Clan clan;
  private String customField;

}
