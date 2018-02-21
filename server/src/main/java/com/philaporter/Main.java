package com.philaporter;

import com.philaporter.verticles.WebsocketServerVerticle;
import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.json.JsonObject;
//import io.vertx.reactivex.core.Vertx;

import java.io.IOException;

import io.vertx.core.Vertx;

/** @author Philip Porter */
public class Main {

  public static JsonObject config;

  public static void main(String[] args) throws IOException {
    printBanner();
    Vertx vertx = Vertx.vertx();
    ConfigStoreOptions fileStore =
        new ConfigStoreOptions()
            .setType("file")
            .setConfig(new JsonObject().put("path", "vertx-config.json"));

    ConfigRetrieverOptions options = new ConfigRetrieverOptions().addStore(fileStore);
    ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
    retriever.getConfig(
        ar -> {
          if (ar.failed()) {
            System.out.println("Failed to load the config file; exiting");
            vertx.close();
          } else {
            config = ar.result();
            WebsocketServerVerticle server1 = new WebsocketServerVerticle(8080);
            //WebsocketServerVerticle server2 = new WebsocketServerVerticle(8081);

            //            vertx.rxDeployVerticle(server1, new
            // DeploymentOptions().setWorkerPoolSize(2)).toObservable().subscribe();

            vertx.deployVerticle(server1);
            //vertx.deployVerticle(server2);
          }
        });
  }

  public static void printBanner() {
    System.out.println("                        _              ");
    System.out.println("                       / |_            ");
    System.out.println(" _   __  .---.  _ .--.`| |-'   _   __  ");
    System.out.println("[ \\ [  ]/ /__\\\\[ `/'`\\]| |    [ \\ [  ] ");
    System.out.println(" \\ \\/ / | \\__., | |    | |, _  > '  <  ");
    System.out.println("  \\__/   '.__.'[___]   \\__/(_)[__]`\\_] ");
    System.out.println("        __        _                    ");
    System.out.println("       [  |      (_)                   ");
    System.out.println(" .--.   | |--.   __   ____             ");
    System.out.println("( (`\\]  | .-. | [  | [_   ]            ");
    System.out.println(" `'.'.  | | | |  | |  .' /_            ");
    System.out.println("[\\__) )[___]|__][___][_____]           ");
    System.out.println("                                       ");
  }
}
