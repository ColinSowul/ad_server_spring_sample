package org.colin.adserver;

import java.lang.StringBuilder;
import java.time.Instant;

/* Model class used to deserialize ad campaign JSON */
public class AdCampaign {
  private String partner_id;
  private int duration;
  private Instant expiration;
  private String ad_content;

  public AdCampaign() {}

  public AdCampaign(String partner_id, int duration, String ad_content) {
    this.partner_id = partner_id;
    this.duration = duration;
    this.expiration = Instant.now().plusSeconds(duration);
    this.ad_content = ad_content;
  }

  public String getPartnerId() {
    return this.partner_id;
  }

  public void setPartnerId(String partnerId) {
    this.partner_id = partnerId;
  }

  public void setPartner_id(String partnerId) {
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

  public void setAd_content(String adContent) {
    this.ad_content = adContent;
  }

  @Override
  public String toString() {
    StringBuilder toStringBuilder = new StringBuilder();
    toStringBuilder.append("{\n  \"partner_id\": \"");
    toStringBuilder.append(this.partner_id);
    toStringBuilder.append("\",\n  \"duration\": ");
    toStringBuilder.append(this.duration);
    toStringBuilder.append(",\n  \"ad_content\": \"");
    toStringBuilder.append(this.ad_content);
    toStringBuilder.append("\"\n}");
    return toStringBuilder.toString();
  }
}