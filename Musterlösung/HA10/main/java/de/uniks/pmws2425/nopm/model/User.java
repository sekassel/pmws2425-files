package de.uniks.pmws2425.nopm.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;

public class User
{
   public static final String PROPERTY_NICKNAME = "nickname";
   public static final String PROPERTY_MESSAGES = "messages";
   private String nickname;
   private List<Message> messages;
   protected PropertyChangeSupport listeners;

   public String getNickname()
   {
      return this.nickname;
   }

   public User setNickname(String value)
   {
      if (Objects.equals(value, this.nickname))
      {
         return this;
      }

      final String oldValue = this.nickname;
      this.nickname = value;
      this.firePropertyChange(PROPERTY_NICKNAME, oldValue, value);
      return this;
   }

   public List<Message> getMessages()
   {
      return this.messages != null ? Collections.unmodifiableList(this.messages) : Collections.emptyList();
   }

   public User withMessages(Message value)
   {
      if (this.messages == null)
      {
         this.messages = new ArrayList<>();
      }
      if (!this.messages.contains(value))
      {
         this.messages.add(value);
         value.setSender(this);
         this.firePropertyChange(PROPERTY_MESSAGES, null, value);
      }
      return this;
   }

   public User withMessages(Message... value)
   {
      for (final Message item : value)
      {
         this.withMessages(item);
      }
      return this;
   }

   public User withMessages(Collection<? extends Message> value)
   {
      for (final Message item : value)
      {
         this.withMessages(item);
      }
      return this;
   }

   public User withoutMessages(Message value)
   {
      if (this.messages != null && this.messages.remove(value))
      {
         value.setSender(null);
         this.firePropertyChange(PROPERTY_MESSAGES, value, null);
      }
      return this;
   }

   public User withoutMessages(Message... value)
   {
      for (final Message item : value)
      {
         this.withoutMessages(item);
      }
      return this;
   }

   public User withoutMessages(Collection<? extends Message> value)
   {
      for (final Message item : value)
      {
         this.withoutMessages(item);
      }
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
      result.append(' ').append(this.getNickname());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutMessages(new ArrayList<>(this.getMessages()));
   }
}
