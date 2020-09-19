package com.fantasybaby.concurrent.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.file.*;
import java.util.List;
@Slf4j
public class Watcher {
    public static void main(String[] args) {
        Path this_dir = Paths.get("C:\\workspace\\javaSeWorkspace\\concurrent-test\\build\\classes\\java\\main\\com\\fantasybaby\\basic\\nio");
        System.out.println("Now watching the current directory ...");

        try {
            WatchService watcher = this_dir.getFileSystem().newWatchService();
            this_dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_DELETE);
            WatchKey watckKey = watcher.take();
            while (true){
                List<WatchEvent<?>> events = watckKey.pollEvents();
                for (WatchEvent event : events) {
                    log.info("Someone just created the file,{}:{}",event.kind().name(),event.context().toString());
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
    }
}
