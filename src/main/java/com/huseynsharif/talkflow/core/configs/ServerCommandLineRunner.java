package com.huseynsharif.talkflow.core.configs;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServerCommandLineRunner implements CommandLineRunner {

    private final SocketIOServer server;

    @Override
    public void run(String... args) throws Exception {
        server.start();
    }
}
