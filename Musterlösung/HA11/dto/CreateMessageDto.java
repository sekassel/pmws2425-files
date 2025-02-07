package de.uniks.pmws2425.nopm.model.dto;

import kong.unirest.core.json.JSONObject;

public record CreateMessageDto(
        String sender,
        String body
) {
    public String toJson() {
        return new JSONObject().put("sender", sender).put("body", body).toString();
    }
}
