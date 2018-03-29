/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplemsgclientproject;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 *
 * @author rmorton
 */
public class SimpleMessageClient {

    @Resource(mappedName = "jms/SimpleQFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(mappedName = "jms/SimpleQ")
    private static Queue queue;
    
    public static void main(String[] args) {
        Connection connection = null;
        Session session = null;
        MessageProducer messageProducer = null;
        TextMessage message = null;
        ObjectMessage objectMessage = null;
        final int NUM_MSGS = 3;
        
        try {
            connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            messageProducer = session.createProducer(queue);
            message = session.createTextMessage();
            
            objectMessage = session.createObjectMessage();
            objectMessage.setObject("bob");
                    
            for (int i=0; i<NUM_MSGS; i++) {
                message.setText("This is message " + (i + 1));
                System.out.println("Sending message: " + message.getText());
//                messageProducer.send(message);
                messageProducer.send(objectMessage);
            } // end for
            System.out.println("Reminder: to see if messages were received, check GlassFish log");
        } catch (JMSException ex) {
            System.out.println("Caught JMSException: " + ex);
            System.exit(-1);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ex) {
                    System.out.println("Caught JMSException: " + ex);
                    System.exit(-1);
                }
            } // if
            System.exit(0);
        } // end try-catch-finally
    } // main
} // end class
