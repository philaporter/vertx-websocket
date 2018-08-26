package com.philaporter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class WebSocketServerVerticle extends AbstractVerticle {

  public static final int PORT = 8080;

  @Override
  public void start(Future startFuture) {

    vertx
        .createHttpServer()
        .websocketHandler(
            ws -> {
              // Delayed action to stop PONGs.. Trigger client undeploy
              vertx.setTimer(
                  9000,
                  delayedAction -> {
                    ws.pause();
                  });
              ws.closeHandler(
                  close -> {
                    System.out.println("Dropped client connection");
                    vertx.close();
                  });
            })
        .listen(PORT);
    startFuture.complete();
  }
}
