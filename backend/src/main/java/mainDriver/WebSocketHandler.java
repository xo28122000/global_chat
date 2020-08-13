package mainDriver;

import DTO.*;
import Processor.MessageProcessor;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class WebSocketHandler {
  // Store sessions if you want to, for example, broadcast a message to all users
  static Map<Session, Session> sessionMap = new ConcurrentHashMap<>();
  Gson gson = new Gson();


  public void broadcast(BroadcastDTO responseDto){
    sessionMap.keySet().forEach( (session) ->{
      try{
        //send out responseDto to each active session.
        session.getRemote().sendString(gson.toJson(responseDto, BroadcastDTO.class));
      }
      catch (Exception e){
        //prints the call stack for error tracing.
        e.printStackTrace();
      }
    });
  }

  @OnWebSocketConnect
  public void connected(Session session) throws IOException {
    session.getRemote().sendString("Connected");
    sessionMap.put(session, session);
  }

  @OnWebSocketClose
  public void closed(Session session, int statusCode, String reason) throws IOException {
    sessionMap.remove(session);
    System.out.println("A session disconnected");
  }

  @OnWebSocketMessage
  public void message(Session session, String message) throws IOException {
    System.out.println("Got: " + message);   // Print message
    BroadcastDTO responseDto = new MessageProcessor(message).process();
    System.out.println("Broadcasting: " + gson.toJson(responseDto));   // Print message

    broadcast(responseDto);
  }
}