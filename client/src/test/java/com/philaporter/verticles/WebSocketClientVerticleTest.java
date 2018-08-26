package com.philaporter.verticles;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class WebSocketClientVerticleTest {

  @Rule public RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testDeployClient(TestContext context) {
    Vertx vertx = rule.vertx();
    vertx
        .createHttpServer()
        .websocketHandler(
            ws -> {
              // Delayed action to stop PONGs.. Trigger client undeploy
              vertx.setTimer(
                  3000,
                  delayedAction -> {
                    ws.pause();
                  });
            })
        .listen(8080);

    Async async = context.async();
    vertx.deployVerticle(
        WebSocketClientVerticle.class.getName(),
        handler -> {
          vertx.setTimer(
              7000,
              delayedAction -> {
                async.complete();
                context.asyncAssertSuccess();
              });
        });
  }
}
