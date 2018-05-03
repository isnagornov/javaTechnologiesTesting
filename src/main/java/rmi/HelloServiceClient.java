package rmi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloServiceClient {

    private Object remoteService;

    public HelloServiceClient(Object remoteService) {
        this.remoteService = remoteService;
    }

    public static void main(String... args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 2099);
        Object service = registry.lookup("sample/HelloService");

        HelloServiceClient helloServiceClient = new HelloServiceClient(service);

        helloServiceClient.showWindow();
    }

    public void showWindow(){
        JFrame frame = new JFrame("Sender");
        frame.setLocation(235, 150);
        frame.setMinimumSize(new Dimension(300, 200));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel jPanel = new JPanel();

        JButton jButton = new JButton("Send");
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    remoteService.getClass().getMethod("sayHello", String.class).invoke(remoteService, "Igor");
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e1) {
                    e1.printStackTrace();
                }
            }
        });

        jPanel.add(jButton);

        frame.getContentPane().add(jPanel);

        frame.pack();
        frame.setVisible(true);

    }

}
