package auction.controllers;

import auction.dtos.SocketDTO;
import auction.entities.AuctionItem;
import auction.entities.Bid;
import auction.entities.Message;
import auction.services.SocketService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;

@Controller
public class SocketController extends TextWebSocketHandler {
    ObjectMapper objectMapper = new ObjectMapper();

    private SocketService socketService;
    public void setSocketService(SocketService socketService) {
        this.socketService = socketService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        System.out.println("Received msg: " + message.getPayload());

        SocketDTO socketDTO = objectMapper.readValue(message.getPayload(), SocketDTO.class);

        switch (socketDTO.action) {
            case "message":
                Message msg = convertPayload(socketDTO.payload, Message.class);
                socketService.saveNewMessage(msg);
                break;
            case "auction":
                AuctionItem auctionItem = convertPayload(socketDTO.payload, AuctionItem.class);
                socketService.saveNewAuction(auctionItem);
                break;
            case "bid":
                Bid bid = convertPayload(socketDTO.payload, Bid.class);
                socketService.saveNewBid(bid);
                break;
            case "connection":
                System.out.println("User connected");
                break;
            case "user-status":
                break;
            default:
                System.out.println("Could not read action: " + socketDTO.action);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        socketService.addSession(session);;
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        socketService.removeSession(session);
    }

    private <T> T convertPayload(Object object, Class<T> type) {
        T t = null;
        try {
            t = objectMapper.readValue(objectMapper.writeValueAsString(object), type);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }
}
