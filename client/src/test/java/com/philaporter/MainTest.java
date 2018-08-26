package com.philaporter;

import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(VertxUnitRunner.class)
public class MainTest {

  @Rule public RunTestOnContext rule = new RunTestOnContext();

  @Test
  public void testMain(TestContext context) {

    Main.main(null);
    Async async = context.async();
    // Give everything enough time to deploy
    rule.vertx()
        .setTimer(
            5000,
            delayedAction -> {
              async.complete();
            });
  }
}
