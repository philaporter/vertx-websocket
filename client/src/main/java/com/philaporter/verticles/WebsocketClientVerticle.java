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
              // Start the back and forth - enforce PONG timeout
              vertx.setPeriodic(
                  2000,
                  periodicAction -> {
                    final long id =
                        vertx.setTimer(
                            2000,
                            delayedAction -> {
                              System.out.println("Undeploy and close");
                              vertx.undeploy(
                                  this.deploymentID(),
                                  undeployed -> {
                                    vertx.close();
                                  });
                            });
                    ws.writePing(Buffer.buffer())
                        .pongHandler(
                            handler -> {
                              System.out.println("GOT ME A PONG - " + System.nanoTime());
                              vertx.cancelTimer(id);
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
