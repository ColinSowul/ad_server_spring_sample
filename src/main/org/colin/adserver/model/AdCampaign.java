package org.colin.adserver.model;

import java.time.Instant;

/* Model class used to deserialize ad campaign JSON */
public class AdCampaign {
  private String partner_id;
  private int duration;
  private Instant expiration;
  private String ad_content;

  public AdCampaign(String partnerId, int duration, String adContent) {
    this.partner_id = partnerId;
    this.duration = duration;
    this.expiration = Instant.now().plusSeconds(duration);
    this.ad_content = adContent;
  }

  public String getPartnerId() {
    return this.partner_id;
  }

  public void setPartnerId(String partnerId) {
    this.partner_id = partnerId;
  }

  public int getDuration() {
    return this.duration;
  }

  public void setDuration(int duration) {
    this.duration  = duration;
    setExpirationFromDuration();
  }

  public Instant getExpiration() {
    return this.expiration;
  }

  public void setExpirationFromDuration() {
    this.expiration = Instant.now().plusSeconds(duration);
  }

  public String getAdContent() {
    return this.ad_content;
  }

  public void setAdContent(String adContent) {
    this.ad_content = adContent;
  }
}