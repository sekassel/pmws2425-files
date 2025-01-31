package de.uniks.pmws2425.nopm.service;

import de.uniks.pmws2425.nopm.model.Message;
import de.uniks.pmws2425.nopm.model.Topic;
import de.uniks.pmws2425.nopm.model.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ChatService {
    private final List<Topic> topics = new ArrayList<>();
    private final List<Message> messages = new ArrayList<>();
    private User self;

    public void login(String nickname) {
        this.self = new User().setNickname(nickname);
        System.out.println("Login " + nickname);
    }

    public void logout() {
        System.out.println("Login " + self.getNickname());
        this.self = null;
    }

    public List<Topic> getTopics() {
        if (topics.isEmpty()) {
            topics.add(new Topic().setTitle("Hello there ...."));
            topics.add(new Topic().setTitle("... General Kenobi"));
        }

        return topics;
    }

    public void sendMessage(Topic topic, String text) {
        Message message = new Message();
        this.messages.add(message);
        message.setBody(text).setSender(this.self).setTimestamp(Instant.now()).setTopic(topic);
    }

    public List<Message> getMessagesForTopic(Topic topic) {
        return this.messages.stream().filter(message -> message.getTopic().equals(topic)).toList();
    }

    public User getSelf() {
        return self;
    }
}
