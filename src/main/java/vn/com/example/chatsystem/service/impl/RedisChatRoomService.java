package vn.com.example.chatsystem.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vn.com.example.chatsystem.model.chatroom.ChatRoom;
import vn.com.example.chatsystem.model.chatroom.ChatRoomUser;
import vn.com.example.chatsystem.model.chatroom.InstantMessage;
import vn.com.example.chatsystem.repository.ChatRoomRepository;
import vn.com.example.chatsystem.service.ChatRoomService;
import vn.com.example.chatsystem.service.InstantMessageService;
import vn.com.example.chatsystem.utils.Destinations;
import vn.com.example.chatsystem.utils.SystemMessages;

@Service
public class RedisChatRoomService implements ChatRoomService {

	@Autowired
	private SimpMessagingTemplate webSocketMessagingTemplate;

	@Autowired
	private ChatRoomRepository chatRoomRepository;

	@Autowired
	private InstantMessageService instantMessageService;

	@Override
	public ChatRoom save(ChatRoom chatRoom) {
		return chatRoomRepository.save(chatRoom);
	}

	@Override
	public ChatRoom findById(String chatRoomId) {
		return chatRoomRepository.findById(chatRoomId).orElse(null);
	}

	@Override
	public ChatRoom join(ChatRoomUser joiningUser, ChatRoom chatRoom) {
		chatRoom.addUser(joiningUser);
		chatRoomRepository.save(chatRoom);

		sendPublicMessage(SystemMessages.welcome(chatRoom.getId(), joiningUser.getUsername()));
		updateConnectedUsersViaWebSocket(chatRoom);
		return chatRoom;
	}

	@Override
	public ChatRoom leave(ChatRoomUser leavingUser, ChatRoom chatRoom) {
		sendPublicMessage(SystemMessages.goodbye(chatRoom.getId(), leavingUser.getUsername()));
		
		chatRoom.removeUser(leavingUser);
		chatRoomRepository.save(chatRoom);
		
		updateConnectedUsersViaWebSocket(chatRoom);
		return chatRoom;
	}

	@Override
	public void sendPublicMessage(InstantMessage instantMessage) {
		webSocketMessagingTemplate.convertAndSend(
				Destinations.ChatRoom.publicMessages(instantMessage.getChatRoomId()),
				instantMessage);

		instantMessageService.appendInstantMessageToConversations(instantMessage);
	}

	@Override
	public void sendPrivateMessage(InstantMessage instantMessage) {
		webSocketMessagingTemplate.convertAndSendToUser(
				instantMessage.getToUser(),
				Destinations.ChatRoom.privateMessages(instantMessage.getChatRoomId()), 
				instantMessage);
		
		webSocketMessagingTemplate.convertAndSendToUser(
				instantMessage.getFromUser(),
				Destinations.ChatRoom.privateMessages(instantMessage.getChatRoomId()), 
				instantMessage);

		instantMessageService.appendInstantMessageToConversations(instantMessage);
	}

	@Override
	public List<ChatRoom> findAll() {
		return (List<ChatRoom>) chatRoomRepository.findAll();
	}
	
	private void updateConnectedUsersViaWebSocket(ChatRoom chatRoom) {
		webSocketMessagingTemplate.convertAndSend(
				Destinations.ChatRoom.connectedUsers(chatRoom.getId()),
				chatRoom.getConnectedUsers());
	}
}
