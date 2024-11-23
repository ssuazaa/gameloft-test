package com.susocode.gamelofttest.user.domain.port.output.client;

import com.susocode.gamelofttest.user.domain.model.Campaign;
import reactor.core.publisher.Mono;

public interface CampaignClient {

  Mono<Campaign> getCurrentCampaign();

}
