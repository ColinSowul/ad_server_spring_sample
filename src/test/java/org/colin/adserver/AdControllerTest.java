package org.colin.adserver;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.HashMap;

class AdControllerTest {
  private static Map<String, AdCampaign> mockAdDataStore = new HashMap<>();
  private static final AdController adController = new AdController();

  @BeforeAll
  public static void setupAll() {
    adController.dataStorePopulationUtility(mockAdDataStore);
  }

  @BeforeEach
  public void setup() {
    mockAdDataStore.clear();
  }

  @Test
  public void fetchAdWhenAdIsPresent() throws Exception {
    AdCampaign testCampaign = new AdCampaign("test", 60000, "test content");
    mockAdDataStore.put("test", testCampaign);
    assertEquals(testCampaign, adController.fetchAd("test"));
  }

  @Test
  public void fetchAdWhenAdIsNotPresent() throws Exception {
    assertEquals(null, adController.fetchAd("test"));
  }

  @Test
  public void fetchAdFailsWhenAdIsExpired() throws Exception {
    AdCampaign testCampaign = new AdCampaign("test", -60000, "test content");
    mockAdDataStore.put("test", testCampaign);
    assertThrows(Exception.class, () -> adController.fetchAd("test"));
  }

  @Test
  public void addAdSuccessfullyAddsAd() throws Exception {
    AdCampaign testCampaign = new AdCampaign("test", 60000, "test content");
    adController.addAd(testCampaign);
    assertTrue(mockAdDataStore.containsKey("test"));
    assertEquals(mockAdDataStore.get("test"), testCampaign);
  }

  @Test
  public void addAdOverwritesExpiredAd() throws Exception {
    AdCampaign expired = new AdCampaign("test", -6000, "test content");
    mockAdDataStore.put("test", expired);
    AdCampaign testCampaign = new AdCampaign("test", 60000, "test content");
    adController.addAd(testCampaign);
    assertTrue(mockAdDataStore.containsKey("test"));
    assertEquals(mockAdDataStore.get("test"), testCampaign);
  }

  @Test
  public void addAdFailsWhenActiveAdIsPresent() throws Exception {
    AdCampaign testCampaign = new AdCampaign("test", 60000, "test content");
    mockAdDataStore.put("test", testCampaign);
    assertThrows(Exception.class, () -> adController.addAd(testCampaign));
  }

  @Test
  public void fetchAllAdsSucceeds() throws Exception {
    mockAdDataStore.put("test1", new AdCampaign("test1", 30000, "test content 1"));
    mockAdDataStore.put("test2", new AdCampaign("test2", -3000, "test content 2"));
    mockAdDataStore.put("test3", new AdCampaign("test3", 36000, "test content 3"));
    assertEquals(mockAdDataStore.values(), adController.fetchAllAds());
  }
}
