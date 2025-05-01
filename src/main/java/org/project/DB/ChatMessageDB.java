package org.project.DB;



import org.project.model.ChatMessage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDB {
    private final String jdbcURL = "jdbc:mysql://localhost:3306/secure_chat?useSSL=false&serverTimezone=UTC";
    private final String jdbcUsername = "root";
    private final String jdbcPassword = "admin";

    public ChatMessageDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // For MySQL Connector/J 9.2.0
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    public void save(ChatMessage msg) throws Exception {
        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
            String sql = "INSERT INTO chat_message (room_id, username, message) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, msg.getRoomId());
            ps.setString(2, msg.getUsername());
            ps.setString(3, msg.getMessage());
            ps.executeUpdate();
        }
    }

    public List<ChatMessage> getMessagesByRoom(String roomId) throws Exception {
        List<ChatMessage> messages = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
            String sql = "SELECT * FROM chat_message WHERE room_id = ? ORDER BY timestamp ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, roomId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ChatMessage msg = new ChatMessage();
                msg.setRoomId(roomId);
                msg.setUsername(rs.getString("username"));
                msg.setMessage(rs.getString("message"));
                msg.setTimestamp(rs.getTimestamp("timestamp"));
                messages.add(msg);
            }
        }

        return messages;
    }
}
