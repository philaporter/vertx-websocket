package com.philaporter.verticles;

import io.vertx.core.Future;
import io.vertx.reactivex.core.AbstractVerticle;

public class WebsocketServerVerticle extends AbstractVerticle {

  public static final int PORT = 8080;

  @Override
  public void start(Future startFuture) {
    vertx
        .createHttpServer()
        .websocketHandler(
            ws -> {
              ws.endHandler(
                  close -> {
                    System.out.println("Dropped client connection");
                  });
            })
        .listen(PORT);
    startFuture.complete();
  }
}
