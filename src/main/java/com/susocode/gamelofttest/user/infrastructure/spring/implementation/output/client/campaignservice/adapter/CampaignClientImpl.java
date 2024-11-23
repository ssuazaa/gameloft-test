package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.adapter;

import com.susocode.gamelofttest.user.domain.model.Campaign;
import com.susocode.gamelofttest.user.domain.port.output.client.CampaignClient;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.client.CampaignServiceClient;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.mapper.ClientCampaignMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CampaignClientImpl implements CampaignClient {

  private final CampaignServiceClient client;
  private final ClientCampaignMapper mapper;

  @Override
  public Mono<Campaign> getCurrentCampaign() {
    return client.getCurrentCampaign()
        .map(mapper::toDomain);
  }

}
