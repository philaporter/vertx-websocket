package com.philaporter.verticles;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class WebSocketServerVerticleTest {

  @Rule public RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testDeployServer(TestContext context) {
    Vertx vertx = rule.vertx();
    vertx.deployVerticle(
        WebSocketServerVerticle.class.getName(),
        handler -> {
          context.asyncAssertSuccess();
        });
  }
}
