package de.uniks.pmws2425.nopm.service;

import de.uniks.pmws2425.nopm.model.Topic;
import de.uniks.pmws2425.nopm.model.dto.CreateMessageDto;
import de.uniks.pmws2425.nopm.model.dto.LoginDto;
import de.uniks.pmws2425.nopm.model.dto.MessageDto;
import kong.unirest.core.*;
import kong.unirest.core.json.JSONArray;
import kong.unirest.core.json.JSONObject;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.uniks.pmws2425.nopm.util.Constants.API_URL;
import static kong.unirest.core.Unirest.get;
import static kong.unirest.core.Unirest.post;

public class ChatApiService {

    static {
        Unirest.config().addDefaultHeader("Content-Type", "application/json");
    }

    public LoginDto login(LoginDto dto) {
        HttpRequest<?> req = post(API_URL + "/auth/login").body(dto.toJson());
        HttpResponse<JsonNode> res = req.asJson();

        return new LoginDto(res.getBody().getObject().getString("nickname"));
    }

    public void logout(LoginDto dto) {
        post(API_URL + "/auth/logout")
                .body(dto.toJson())
                .asEmpty();
    }

    public List<Topic> getTopics() {
        HttpRequest<GetRequest> req = get(API_URL + "/topics");
        HttpResponse<JsonNode> res = req.asJson();

        JSONArray array = res.getBody().getArray();
        ArrayList<Topic> topics = new ArrayList<>();

        for (Object o : array) {
            JSONObject json = (JSONObject) o;
            topics.add(new Topic().setTitle(json.getString("title")));
        }

        return topics;
    }

    public MessageDto sendMessage(String topic, CreateMessageDto dto) {
        HttpRequest<?> req = post(API_URL + "/topics/" + topic + "/messages").body(dto.toJson());
        HttpResponse<JsonNode> res = req.asJson();
        JSONObject json = res.getBody().getObject();

        return fromJson(json);
    }

    public List<MessageDto> getMessages(String topic, Instant after) {
        HttpRequest<GetRequest> req = get(API_URL + "/topics/" + topic + "/messages");
        if (Objects.nonNull(after)) {
            req.queryString("after", after.toString());
        }
        HttpResponse<JsonNode> res = req.asJson();

        JSONArray array = res.getBody().getArray();
        ArrayList<MessageDto> messages = new ArrayList<>();

        for (Object o : array) {
            JSONObject json = (JSONObject) o;

            messages.add(fromJson(json));
        }
        return messages;
    }

    private MessageDto fromJson(JSONObject json) {
        return new MessageDto(
                json.getString("topic"),
                json.getString("timestamp"),
                json.getString("sender"),
                json.getString("body"));
    }
}
