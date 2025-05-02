package org.project.servlets;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



import org.project.DB.ChatMessageDB;

import java.io.IOException;
import java.io.PrintWriter;


public class DeleteRoomServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String roomId = (String) req.getSession().getAttribute("roomId");

        if (roomId != null) {
            try {
                System.out.println("Starting");
                ChatMessageDB db = new ChatMessageDB();
                db.deleteMessagesByRoomId(roomId);

                // Optionally clear session
                req.getSession().invalidate();
                System.out.println("session deleted");

                // Redirect to home or index page
                PrintWriter out = resp.getWriter();
                out.println("Room deleted");
                System.out.println("deleted");
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendError(500, "Failed to delete room messages.");
            }
        } else {
            resp.sendError(400, "Room ID not found in session.");
        }
    }
}


