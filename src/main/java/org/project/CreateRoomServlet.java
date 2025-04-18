package org.project;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.*;
import java.io.PrintWriter;


import java.io.IOException;
import java.net.InetAddress;
import java.util.UUID;

public class CreateRoomServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        ServerSocket.start();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException,IOException {
        String username = req.getParameter("username");
        InetAddress myIP = InetAddress.getLocalHost();
        String roomId = UUID.randomUUID().toString().substring(0, 6); // Short 6-char room code
        System.out.println(roomId);
        HttpSession session = req.getSession();
        session.setAttribute("username", username);
        session.setAttribute("roomId", roomId);
        session.setAttribute("myIP", myIP.getHostAddress());
        ChatUtil.setUserFromRoomId(username, roomId);
        ChatUtil.addIpofUser(username, myIP.getHostAddress());


        resp.sendRedirect("Chat.jsp");
    }
}


