package com.philaporter;

import com.philaporter.verticles.WebSocketServerVerticle;
import io.vertx.core.Vertx;

/** @author Philip Porter */
public class Main {

  public static void main(String[] args) {
    printBanner();
    Vertx vertx = Vertx.vertx();

    vertx.deployVerticle(
        WebSocketServerVerticle.class.getName(),
        handler -> {
          if (handler.succeeded()) {
            System.out.println("Deployed websocket server");
          } else {
            System.out.println("Failed to deploy server");
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
