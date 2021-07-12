package org.colin.adserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class AdController {

  private static Map<String, AdCampaign> adDataStore = new ConcurrentHashMap<>();

  @GetMapping("/ad/{partnerId}")
  public AdCampaign fetchAd(@PathVariable String partnerId) throws Exception {
    AdCampaign campaign = adDataStore.get(partnerId);
    if(campaign == null) return null;
    if(Instant.now().compareTo(campaign.getExpiration()) > 0) throw new Exception("Ad for partner \"" + partnerId + "\" has expired");
    return campaign;
  }

  @GetMapping("/all")
  public Collection<AdCampaign> fetchAllAds() {
    return adDataStore.values();
  }

  @PostMapping("/ad")
  public String addAd(@RequestBody AdCampaign newAd) throws Exception {
    if(newAd.getExpiration() == null) newAd.setExpirationFromDuration();
    if(adDataStore.containsKey(newAd.getPartnerId()) && Instant.now().compareTo(adDataStore.get(newAd.getPartnerId()).getExpiration()) <= 0) {
      throw new Exception("Ad for partner \"" + newAd.getPartnerId() + "\" already exists");
    }
    adDataStore.put(newAd.getPartnerId(), newAd);
    return "Ad for parter \"" + newAd.getPartnerId() + "\" has been registered successfully";
  }

  /* Utility for populating data store for testing */
  static void dataStorePopulationUtility(Map<String, AdCampaign> mockDataStore) {
    adDataStore = mockDataStore;
  }
}