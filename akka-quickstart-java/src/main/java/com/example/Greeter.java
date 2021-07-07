package com.example;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.*;

import java.util.Objects;

// #greeter

/**
 * Receives commands to Greet someone
 * and responds with a Greeted to confirm the greeting has taken place
 */
public class Greeter extends AbstractBehavior<Greeter.Greet> {
    /**
     * command sent to the Greeter actor to greet
     */
    public static final class Greet {
        public final String whom;
        /**
         * the sender of the message supplies so that the Greeter Actor can send back the confirmation message.
         */
        public final ActorRef<Greeted> replyTo;

        public Greet(String whom, ActorRef<Greeted> replyTo) {
            this.whom = whom;
            this.replyTo = replyTo;
        }
    }

    /**
     * reply from the Greeter actor to confirm the greeting has happened
     */
    public static final class Greeted {
        public final String whom;
        public final ActorRef<Greet> from;

        public Greeted(String whom, ActorRef<Greet> from) {
            this.whom = whom;
            this.from = from;
        }

        // #greeter
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Greeted greeted = (Greeted) o;
            return Objects.equals(whom, greeted.whom) &&
                    Objects.equals(from, greeted.from);
        }

        @Override
        public int hashCode() {
            return Objects.hash(whom, from);
        }

        @Override
        public String toString() {
            return "Greeted{" +
                    "whom='" + whom + '\'' +
                    ", from=" + from +
                    '}';
        }
// #greeter
    }

    public static Behavior<Greet> create() {
        return Behaviors.setup(Greeter::new);
    }

    private Greeter(ActorContext<Greet> context) {
        super(context);
    }

    /**
     * The behavior of the Actor is defined as the Greeter AbstractBehavior
     * with the help of the newReceiveBuilder behavior factory.
     * @return
     */
    @Override
    public Receive<Greet> createReceive() {
        return newReceiveBuilder().onMessage(Greet.class, this::onGreet).build();
    }

    /**
     * processing the next message then results in a new behavior that can potentially be different from this one.
     * The state can be updated by modifying the current instance as it is mutable. In this case we don’t need to update any state,
     * so we return this without any field updates, which means the next behavior is “the same as the current one
     *
     * The type of the messages handled by this behavior is declared to be of class Greet. Typically, an actor handles
     * more than one specific message type and then there is one common interface that all messages that the actor can handle implements
     * @param command
     * @return
     */
    private Behavior<Greet> onGreet(Greet command) {
        getContext().getLog().info("Hello {}!", command.whom);
        //#greeter-send-message
        /**
         * is an asynchronous operation that doesn’t block the caller’s thread.
         */
        command.replyTo.tell(new Greeted(command.whom, getContext().getSelf()));
        //#greeter-send-message
        return this;
    }
}
// #greeter

