package com.tpvision.smartinstall.servlet;

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

@ServerEndpoint("/websocket/polling")
public class PollingWebSocket {
   private static final Logger log = LoggerFactory.getLogger(PollingWebSocket.class);
   private static final Set<PollingWebSocket> connections = new CopyOnWriteArraySet<>();
   private Session session;

   public PollingWebSocket() {
      log.info("start polling websocket endpoint");
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
      for (PollingWebSocket client : connections) {
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

   public static void notifyMessageUpdate() {
      JSONObject obj = new JSONObject();
      obj.put("refreshFlag", true);
      JSONObject result = new JSONObject();
      result.put("type", "PmsPooling");
      result.put("data", obj);
      broadcast(result.toString());
   }

   public static void notifyGuestInfoUpdate(String guestId) {
      JSONObject obj = new JSONObject();
      obj.put("guestinfos", guestId);
      JSONObject result = new JSONObject();
      result.put("type", "PmsPooling");
      result.put("data", obj);
      broadcast(result.toString());
   }

   public static void notifyDeviceUpdate(JSONObject jsonNotice) {
      JSONObject result = new JSONObject();
      result.put("type", "IPTVPooling");
      result.put("data", jsonNotice);
      broadcast(result.toString());
   }
}
