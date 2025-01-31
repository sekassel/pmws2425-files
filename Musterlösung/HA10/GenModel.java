package de.uniks.pmws2425.nopm.model;

import org.fulib.builder.ClassModelDecorator;
import org.fulib.builder.ClassModelManager;
import org.fulib.builder.reflect.Link;

import java.time.Instant;
import java.util.List;

public class GenModel implements ClassModelDecorator {
    class Message {
        String body;
        Instant timestamp;

        @Link("messages")
        Topic topic;

        @Link("messages")
        User sender;
    }

    class Topic {
        String title;

        @Link("topic")
        List<Message> messages;
    }

    class User {
        String nickname;

        @Link("sender")
        List<Message> messages;
    }

    @Override
    public void decorate(ClassModelManager mm) {
        mm.haveNestedClasses(GenModel.class);
    }
}
