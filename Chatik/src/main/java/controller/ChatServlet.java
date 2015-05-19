package controller;

import dbcontext.BdContext;
import entity.Message;
import utilities.AsyncProcessor;
import utilities.MessageExchange;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Mary on 16-May-15.
 */
//@WebServlet(urlPatterns = { "/msgchat" }, asyncSupported = true)
public class ChatServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private MessageExchange messageExchange = new MessageExchange();
    private int lastModification = 0;

    @Override
    public void init() throws ServletException {

       /* try {
            lastModification = new BdContext().getLastModification();
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String token = request.getParameter("token");
            Logger.getLogger(ChatController.class.getName()).info("Get request:token " + token + " recieved");
            if (token != null && !"".equals(token)) {
                int index = messageExchange.getIndex(token);
                List<Message> messages = null;
                try {
                    messages = new BdContext().getMessagesByModification(index);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                sendResponse(response, messageExchange.getServerResponse(messages));
                if(messages.size()>0) {
                    sendResponse(response, messageExchange.getServerResponse(messages));
                }
                else
                {
                    try {
                        AsyncContext asyncContext=request.startAsync(request,response);
                        asyncContext.setTimeout(30000);
                        AsyncProcessor.add(asyncContext);
                    }
                    catch(Throwable e)
                    {
                        System.out.println(e.getStackTrace());
                    }
                }
            }
    }
    private void sendResponse(HttpServletResponse resp, String response) throws IOException {

        OutputStream os = resp.getOutputStream();
        os.write(response.getBytes("UTF-8"));
        os.flush();
        os.close();
    }

}
