package com.philaporter.verticles;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;

public class WebsocketClientVerticle extends AbstractVerticle {

  public void start(Future startFuture) {
    final String id = this.deploymentID();
    vertx
        .createHttpClient()
        .websocket(
            8080,
            "localhost",
            "/",
            ws -> {
                ws.writeTextMessage("Start");
              ws.handler(
                  data -> {
                    System.out.println(data.toString("ISO-8859-1"));
                    ws.writeTextMessage("Hey there");
                  });
              ws.endHandler(
                  close -> {
                    System.out.println("Connection stopped, undeploying verticle");
                    vertx.undeploy(id);
                    vertx.close();
                  });
            });
  }
}
