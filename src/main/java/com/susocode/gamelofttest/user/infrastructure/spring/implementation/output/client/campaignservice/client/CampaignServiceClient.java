package com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.client;

import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.dto.CampaignResponseDto;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.dto.DoesNotHaveMatcherResponseDto;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.dto.HasMatcherResponseDto;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.dto.LevelMatcherResponseDto;
import com.susocode.gamelofttest.user.infrastructure.spring.implementation.output.client.campaignservice.dto.MatchersResponseDto;
import java.time.ZonedDateTime;
import java.util.Set;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CampaignServiceClient {

  private final WebClient webClient;

  public CampaignServiceClient(WebClient.Builder webClientBuilder) {
    this.webClient = webClientBuilder
        .baseUrl("http://localhost:8081/api")
        .build();
  }

  public Mono<CampaignResponseDto> getCurrentCampaign() {
    return webClient.get()
        .uri("/v1/campaigns/current")
        .retrieve()
        .bodyToMono(CampaignResponseDto.class)
        .onErrorResume(this::getFallbackCampaign);
  }

  public Mono<CampaignResponseDto> getFallbackCampaign(Throwable throwable) {
    var levelMatcherResponseDto = new LevelMatcherResponseDto(1, 3);
    var hasMatcherResponseDto = new HasMatcherResponseDto(
        Set.of("US", "RO", "CA"),
        Set.of("item_1"));
    var doesNotHaveMatcherResponseDto = new DoesNotHaveMatcherResponseDto(
        Set.of("item_4"));

    var matchersResponseDto = new MatchersResponseDto(
        levelMatcherResponseDto,
        hasMatcherResponseDto,
        doesNotHaveMatcherResponseDto);

    var campaignResponseDto = new CampaignResponseDto(
        "mygame",
        "mycampaign",
        10.5,
        matchersResponseDto,
        ZonedDateTime.parse("2022-01-25T00:00:00Z"),
        ZonedDateTime.parse("2022-02-25T00:00:00Z"),
        true,
        ZonedDateTime.parse("2021-07-13T11:46:58Z"));

    return Mono.just(campaignResponseDto);
  }

}
