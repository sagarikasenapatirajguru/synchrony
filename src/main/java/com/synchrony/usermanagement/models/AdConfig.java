package com.synchrony.usermanagement.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class AdConfig {
    public ArrayList<String> safeFlags;
    public ArrayList<Object> highRiskFlags;
    public ArrayList<String> unsafeFlags;
    public ArrayList<Object> wallUnsafeFlags;
    public boolean showsAds;
    public int showAdLevel;
    public ArrayList<String> safe_flags;
    public ArrayList<Object> high_risk_flags;
    public ArrayList<String> unsafe_flags;
    public ArrayList<Object> wall_unsafe_flags;
    public boolean show_ads;
    public int show_ad_level;
    public int nsfw_score;
}
