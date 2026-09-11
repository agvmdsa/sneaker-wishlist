package com.agvms.sneakerwishlist.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KicksDbSearchResponse(List<KicksDbProduct> data) {
}
