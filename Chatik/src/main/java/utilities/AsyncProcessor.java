package utilities;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import entity.Message;

/**
 * Created by Mary on 12-May-15.
 */
public class AsyncProcessor {
    static Queue<AsyncContext> asyncContextQueue = new ConcurrentLinkedDeque<>();

    public static void add(AsyncContext ac)
    {
        ac.addListener(new AsyncListener() {
            @Override
            public void onComplete(AsyncEvent asyncEvent) throws IOException {

            }

            @Override
            public void onTimeout(AsyncEvent asyncEvent) throws IOException {
                asyncContextQueue.remove(asyncEvent.getAsyncContext());
            }

            @Override
            public void onError(AsyncEvent asyncEvent) throws IOException {
                asyncContextQueue.remove(asyncEvent.getAsyncContext());
            }

            @Override
            public void onStartAsync(AsyncEvent asyncEvent) throws IOException {

            }
        });
        asyncContextQueue.add(ac);
    }
    public static void startCharityCampaign(List<Message> messages) throws IOException {
        for(AsyncContext ac:asyncContextQueue)
        {
            OutputStream os = ac.getResponse().getOutputStream();
            os.write(new MessageExchange().getServerResponse(messages).getBytes("UTF-8"));
            os.flush();
            os.close();
            ac.complete();
            asyncContextQueue.remove(ac);
        }
    }
}
