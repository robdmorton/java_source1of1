/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package QMsgBeanPkg;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;

import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 *
 * @author rmorton
 */
@MessageDriven(mappedName = "jms/SimpleQ", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class QMsgBean implements MessageListener {
    
    public QMsgBean() {
    }
    
    @Override
    public void onMessage(Message inMsg) {
        TextMessage tMsg=null;
        
        try {
            if (inMsg instanceof TextMessage) {
                tMsg = (TextMessage) inMsg;
                System.out.println("Q Message Bean received: " + tMsg.getText());
            } else {
                System.out.println("Q Message Bean: WRONG type " + inMsg.getClass().getName());
            } //end if
        } catch (JMSException e) {
            System.out.println("+++ Caught JMSException");
            System.exit(-1);
        }
    } // onMessage
} //end class
