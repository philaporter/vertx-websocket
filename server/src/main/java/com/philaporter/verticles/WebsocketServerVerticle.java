package com.philaporter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class WebsocketServerVerticle extends AbstractVerticle {

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
              ws.endHandler(
                  close -> {
                    System.out.println("Dropped client connection");
                  });
            })
        .listen(PORT);
    startFuture.complete();
  }
}
