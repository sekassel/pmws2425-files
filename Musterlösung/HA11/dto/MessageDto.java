package de.uniks.pmws2425.nopm.model.dto;

public record MessageDto(
        String topic,
        String timestamp,
        String sender,
        String body
) {
}
