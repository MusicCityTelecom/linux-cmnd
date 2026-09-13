package com.tpvision.smartinstall.api;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint("/api/reception/socket")
public class ReceptionSocket {
   private static final Logger log = LoggerFactory.getLogger(ReceptionSocket.class);
   private static final Set<ReceptionSocket> connections = new CopyOnWriteArraySet<>();
   private Session session;

   public ReceptionSocket() {
      log.info("start init reception websocket endpoint");
   }

   @OnOpen
   public void start(Session session) {
      this.session = session;
      connections.add(this);
   }

   @OnClose
   public void end() {
      connections.remove(this);
   }

   @OnMessage
   public void incoming(String message) {
      log.info("receive message from client: {}", message);
   }

   @OnError
   public void onError(Throwable t) throws Throwable {
      log.error("Chat Error: " + t.toString(), t);
   }

   private static void broadcast(String msg) {
      for (ReceptionSocket client : connections) {
         try {
            synchronized (client) {
               client.session.getBasicRemote().sendText(msg);
            }
         } catch (IOException e) {
            log.debug("Chat Error: Failed to send message to client", e);
            connections.remove(client);

            try {
               client.session.close();
            } catch (IOException var5) {
            }
         }
      }
   }

   public static void notifyPmsGuestInfoUpdate() {
      JSONObject obj = new JSONObject();
      obj.put("type", "guest_update");
      broadcast(obj.toString());
   }

   public static void notifyRoomNotificationUpdate() {
      JSONObject obj = new JSONObject();
      obj.put("type", "notification_update");
      broadcast(obj.toString());
   }
}
