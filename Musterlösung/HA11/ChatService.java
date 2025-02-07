package de.uniks.pmws2425.nopm.service;

import de.uniks.pmws2425.nopm.model.Message;
import de.uniks.pmws2425.nopm.model.Topic;
import de.uniks.pmws2425.nopm.model.User;
import de.uniks.pmws2425.nopm.model.dto.CreateMessageDto;
import de.uniks.pmws2425.nopm.model.dto.LoginDto;
import de.uniks.pmws2425.nopm.model.dto.MessageDto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ChatService {
    private final ChatApiService chatApiService;

    private final List<User> users = new ArrayList<>();
    private User self;

    public ChatService(ChatApiService chatApiService) {
        this.chatApiService = chatApiService;
    }

    public User login(String nickname) {
        LoginDto res = this.chatApiService.login(new LoginDto(nickname));

        if (res == null) {
            return null;
        }

        this.self = new User().setNickname(res.nickname());

        return this.self;
    }

    public void logout() {
        if (this.self == null) {
            return;
        }

        this.chatApiService.logout(new LoginDto(this.self.getNickname()));
        this.self = null;
    }

    public List<Topic> getTopics() {
        return this.chatApiService.getTopics();
    }

    public void sendMessage(Topic topic, String body) {
        this.chatApiService.sendMessage(topic.getTitle(), new CreateMessageDto(this.self.getNickname(), body));
    }

    public void loadMessages(Topic topic, Instant after) {
        List<MessageDto> messageDtos = this.chatApiService.getMessages(topic.getTitle(), after);
        if (Objects.isNull(messageDtos) || messageDtos.isEmpty()) {
            return;
        }

        messageDtos.forEach(dto -> topic.withMessages(new Message()
                .setBody(dto.body())
                .setTimestamp(Instant.parse(dto.timestamp()))
                .setSender(findOrCreateUser(dto.sender()))
        ));
    }

    // ===============================================================================================================
    // Helper
    // ===============================================================================================================

    public User findOrCreateUser(String nickname) {
        Optional<User> match = this.users.stream().filter(user -> nickname.equals(user.getNickname())).findFirst();
        return match.orElseGet(() -> {
            User user = new User().setNickname(nickname);
            this.users.add(user);
            return user;
        });
    }

    public User getSelf() {
        return self;
    }
}
