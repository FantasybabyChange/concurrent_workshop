package com.example;

import akka.actor.typed.ActorSystem;

import java.io.IOException;

public class AkkaQuickstart {
    /**
     * The AkkaQuickstart object in Hello World creates ActorSystem with a guardian.
     * The guardian is the top level actor that bootstraps your application.
     * The guardian is typically defined with Behaviors.setup that contains the initial bootstrap.
     *
     * @param args
     */
    public static void main(String[] args) {
        //#actor-system
        final ActorSystem<GreeterMain.SayHello> greeterMain = ActorSystem.create(GreeterMain.create(), "helloakka");
        //#actor-system

        //#main-send-messages
        greeterMain.tell(new GreeterMain.SayHello("Charles"));
        //#main-send-messages

        try {
            System.out.println(">>> Press ENTER to exit <<<");
            System.in.read();
        } catch (IOException ignored) {
        } finally {
            greeterMain.terminate();
        }
    }
}
