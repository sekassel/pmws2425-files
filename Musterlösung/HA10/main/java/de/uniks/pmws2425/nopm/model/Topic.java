package de.uniks.pmws2425.nopm.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Collections;
import java.util.Collection;
import java.beans.PropertyChangeSupport;

public class Topic
{
   public static final String PROPERTY_TITLE = "title";
   public static final String PROPERTY_MESSAGES = "messages";
   private String title;
   private List<Message> messages;
   protected PropertyChangeSupport listeners;

   public String getTitle()
   {
      return this.title;
   }

   public Topic setTitle(String value)
   {
      if (Objects.equals(value, this.title))
      {
         return this;
      }

      final String oldValue = this.title;
      this.title = value;
      this.firePropertyChange(PROPERTY_TITLE, oldValue, value);
      return this;
   }

   public List<Message> getMessages()
   {
      return this.messages != null ? Collections.unmodifiableList(this.messages) : Collections.emptyList();
   }

   public Topic withMessages(Message value)
   {
      if (this.messages == null)
      {
         this.messages = new ArrayList<>();
      }
      if (!this.messages.contains(value))
      {
         this.messages.add(value);
         value.setTopic(this);
         this.firePropertyChange(PROPERTY_MESSAGES, null, value);
      }
      return this;
   }

   public Topic withMessages(Message... value)
   {
      for (final Message item : value)
      {
         this.withMessages(item);
      }
      return this;
   }

   public Topic withMessages(Collection<? extends Message> value)
   {
      for (final Message item : value)
      {
         this.withMessages(item);
      }
      return this;
   }

   public Topic withoutMessages(Message value)
   {
      if (this.messages != null && this.messages.remove(value))
      {
         value.setTopic(null);
         this.firePropertyChange(PROPERTY_MESSAGES, value, null);
      }
      return this;
   }

   public Topic withoutMessages(Message... value)
   {
      for (final Message item : value)
      {
         this.withoutMessages(item);
      }
      return this;
   }

   public Topic withoutMessages(Collection<? extends Message> value)
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
      result.append(' ').append(this.getTitle());
      return result.substring(1);
   }

   public void removeYou()
   {
      this.withoutMessages(new ArrayList<>(this.getMessages()));
   }
}
