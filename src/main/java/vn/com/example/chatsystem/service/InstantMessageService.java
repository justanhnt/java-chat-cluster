package vn.com.example.chatsystem.service;

import java.util.List;

import vn.com.example.chatsystem.model.chatroom.InstantMessage;

public interface InstantMessageService {
	
	void appendInstantMessageToConversations(InstantMessage instantMessage);
	List<InstantMessage> findAllInstantMessagesFor(String username, String chatRoomId);
}
