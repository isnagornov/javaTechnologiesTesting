package jms;

import javax.jms.*;

public class MessageReceiver implements MessageListener {

    public void consumeMessage() throws InterruptedException {
        try {

            // creating a connection
            ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
            Connection connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createTopic("HelloWorld");
            MessageConsumer consumer = session.createConsumer(destination);
            consumer.setMessageListener(this);
            connection.start();

            System.out.println("Start listen HelloWorld Queue from HelloConsumer.java");

            Thread.sleep(100000);

            System.out.println("End listen HelloWorld Queue from HelloConsumer.java");

            // close everything
            session.close();
            connection.close();
            consumer.close();

        } catch (JMSException ex) {
            System.out.println("Error = " + ex.getMessage());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new MessageReceiver().consumeMessage();
    }

    public void onMessage(Message msg) {
        String msgText;
        try {
            if (msg instanceof TextMessage) {
                msgText = ((TextMessage) msg).getText();
                System.out.println("Got from the queue: " + msgText);
            } else {
                System.out.println("Got a non-text message");
            }
        } catch (JMSException e) {
            System.out.println("Error while consuming a message: " +
                    e.getMessage());
        }
    }
}
