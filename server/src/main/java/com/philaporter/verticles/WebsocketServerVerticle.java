package com.philaporter.verticles;

import io.vertx.core.Future;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.AbstractVerticle;

public class WebsocketServerVerticle extends AbstractVerticle {

  private int port;

  public WebsocketServerVerticle() {}

  public WebsocketServerVerticle(int port) {
    this.port = port;
  }

  @Override
  public void start(Future startFuture) {
    final String id = this.deploymentID();
    vertx
        .createHttpServer()
        .websocketHandler(
            ws -> {
              ws.handler(
                  data -> {
                    System.out.println(data.toString());
                    ws.writeTextMessage("Server hello");
                  });
              startFuture.succeeded();
              ws.endHandler(
                  close -> {
                    System.out.println("closed connection, undeploying verticle");
                    vertx.undeploy(id);
                    vertx.close();
                  });
            })
        .listen(port);
  }
}
