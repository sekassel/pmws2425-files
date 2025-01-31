package de.uniks.pmws2425.nopm.model;
import java.util.Objects;
import java.time.Instant;
import java.beans.PropertyChangeSupport;

public class Message
{
   public static final String PROPERTY_BODY = "body";
   public static final String PROPERTY_TIMESTAMP = "timestamp";
   public static final String PROPERTY_SENDER = "sender";
   public static final String PROPERTY_TOPIC = "topic";
   private String body;
   private Instant timestamp;
   private User sender;
   private Topic topic;
   protected PropertyChangeSupport listeners;

   public String getBody()
   {
      return this.body;
   }

   public Message setBody(String value)
   {
      if (Objects.equals(value, this.body))
      {
         return this;
      }

      final String oldValue = this.body;
      this.body = value;
      this.firePropertyChange(PROPERTY_BODY, oldValue, value);
      return this;
   }

   public Instant getTimestamp()
   {
      return this.timestamp;
   }

   public Message setTimestamp(Instant value)
   {
      if (Objects.equals(value, this.timestamp))
      {
         return this;
      }

      final Instant oldValue = this.timestamp;
      this.timestamp = value;
      this.firePropertyChange(PROPERTY_TIMESTAMP, oldValue, value);
      return this;
   }

   public User getSender()
   {
      return this.sender;
   }

   public Message setSender(User value)
   {
      if (this.sender == value)
      {
         return this;
      }

      final User oldValue = this.sender;
      if (this.sender != null)
      {
         this.sender = null;
         oldValue.withoutMessages(this);
      }
      this.sender = value;
      if (value != null)
      {
         value.withMessages(this);
      }
      this.firePropertyChange(PROPERTY_SENDER, oldValue, value);
      return this;
   }

   public Topic getTopic()
   {
      return this.topic;
   }

   public Message setTopic(Topic value)
   {
      if (this.topic == value)
      {
         return this;
      }

      final Topic oldValue = this.topic;
      if (this.topic != null)
      {
         this.topic = null;
         oldValue.withoutMessages(this);
      }
      this.topic = value;
      if (value != null)
      {
         value.withMessages(this);
      }
      this.firePropertyChange(PROPERTY_TOPIC, oldValue, value);
      return this;
   }

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (this.listeners != null)
      {
         this.listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public PropertyChangeSupport listeners()
   {
      if (this.listeners == null)
      {
         this.listeners = new PropertyChangeSupport(this);
      }
      return this.listeners;
   }

   @Override
   public String toString()
   {
      final StringBuilder result = new StringBuilder();
      result.append(' ').append(this.getBody());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.setSender(null);
      this.setTopic(null);
   }
}
