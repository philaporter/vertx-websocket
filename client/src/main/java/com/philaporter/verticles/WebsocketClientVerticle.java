package com.philaporter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.buffer.Buffer;

public class WebsocketClientVerticle extends AbstractVerticle {

  public static final String HOST = "localhost";
  public static final String ENDPOINT = "/";
  public static final int PORT = 8080;

  public void start(Future startFuture) {

    vertx
        .createHttpClient()
        .websocket(
            PORT,
            HOST,
            ENDPOINT,
            ws -> {
              // Start the back and forth
              ws.writePing(Buffer.buffer());
              ws.pongHandler(
                  pongHandler -> {
                    System.out.println("Received PONG");
                    vertx.setTimer(
                        2000,
                        delayedAction -> {
                          ws.writePing(Buffer.buffer());
                        });
                  });
              ws.endHandler(
                  close -> {
                    System.out.println("Connection dropped");
                    vertx.close();
                  });
            });
    startFuture.complete();
  }
}
