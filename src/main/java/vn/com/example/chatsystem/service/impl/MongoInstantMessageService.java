package vn.com.example.chatsystem.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.com.example.chatsystem.model.chatroom.ChatRoom;
import vn.com.example.chatsystem.model.chatroom.InstantMessage;
import vn.com.example.chatsystem.repository.InstantMessageRepository;
import vn.com.example.chatsystem.service.ChatRoomService;
import vn.com.example.chatsystem.service.InstantMessageService;

@Service
public class MongoInstantMessageService implements InstantMessageService {

	@Autowired
	private InstantMessageRepository instantMessageRepository;
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Override
	public void appendInstantMessageToConversations(InstantMessage instantMessage) {
		if (instantMessage.isFromAdmin() || instantMessage.isPublic()) {
			ChatRoom chatRoom = chatRoomService.findById(instantMessage.getChatRoomId());
			
			chatRoom.getConnectedUsers().forEach(connectedUser -> {
				instantMessage.setUsername(connectedUser.getUsername());
				instantMessageRepository.save(instantMessage);
			});
		} else {
			instantMessage.setUsername(instantMessage.getFromUser());
			instantMessageRepository.save(instantMessage);
			
			instantMessage.setUsername(instantMessage.getToUser());
			instantMessageRepository.save(instantMessage);
		}
	}

	@Override
	public List<InstantMessage> findAllInstantMessagesFor(String username, String chatRoomId) {
		return instantMessageRepository.findByUsernameAndChatRoomId(username, chatRoomId);
	}
}
