package org.colin.adserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class AdController {

  private static final Map<String, AdCampaign> adDataStore = new ConcurrentHashMap<>();

  @GetMapping("/ad/{partnerId}")
  public AdCampaign fetchAd(@PathVariable String partnerId) {
    AdCampaign campaign = adDataStore.get(partnerId);
    //TODO: Error if ad is expired
    return campaign;
  }

  @GetMapping("/all")
  public Collection<AdCampaign> fetchAllAds() {
    return adDataStore.values();
  }

  @PostMapping("/ad")
  public AdCampaign addAd(@RequestBody AdCampaign newAd) {
    System.out.println("---------------- newAd ----------------");
    System.out.println(newAd);
    if(newAd.getExpiration() == null) newAd.setExpirationFromDuration();
    adDataStore.put(newAd.getPartnerId(), newAd);
    return newAd;
  }
}