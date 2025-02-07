package de.uniks.pmws2425.nopm.model.dto;

import kong.unirest.core.json.JSONObject;

public record LoginDto(
        String nickname
) {
    public String toJson() {
        return new JSONObject().put("nickname", nickname).toString();
    }
}
