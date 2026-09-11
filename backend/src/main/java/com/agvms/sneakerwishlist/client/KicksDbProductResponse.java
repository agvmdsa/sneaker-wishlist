package com.agvms.sneakerwishlist.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KicksDbProductResponse(KicksDbProduct data) {
}
