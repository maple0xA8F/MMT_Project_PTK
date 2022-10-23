package org.server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class server extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) throws IOException {

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    server frame = new server();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    private static void log(String message) {
        System.out.println(message);
    }

    private static class ServiceThread extends Thread {

        private int clientNumber;
        private Socket socketOfServer;

        public ServiceThread(Socket socketOfServer, int clientNumber) {
            this.clientNumber = clientNumber;
            this.socketOfServer = socketOfServer;

            // Log
            log("New connection with client# " + this.clientNumber + " at " + socketOfServer);
        }
        @Override
        public void run() {

            try {

                // Mở luồng vào ra trên Socket tại Server.
                BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
                BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));

                while (true) {

                    while (true) {
                        String s = "";
                        s = is.readLine();
                        switch (s) {
                        }
                        switch (s) {
                            case "KEYLOG": keylog(); break;
                            case "SHUTDOWN": shutdown(); break;
                            case "REGISTRY": registry(); break;
                            case "TAKEPIC": takepic(); break;
                            case "PROCESS": process(); break;
                            case "APPLICATION": application(); break;
                            default:
                                {
                                    os.write(" >> OK");
                                    os.newLine();
                                    os.flush();
                                    break;
                                }
                        }
                    }

                    // Đọc dữ liệu tới server (Do client gửi tới).
                    // String line = is.readLine();

                    // // Ghi vào luồng đầu ra của Socket tại Server.
                    // // (Nghĩa là gửi tới Client).
                    // os.write(">> " + line);
                    // // Kết thúc dòng
                    // os.newLine();
                    // // Đẩy dữ liệu đi
                    // os.flush();

                    // // Nếu người dùng gửi tới QUIT (Muốn kết thúc trò chuyện).
                    // if (line.equals("QUIT")) {
                    //     os.write(">> OK");
                    //     os.newLine();
                    //     os.flush();
                    //     break;
                    // }
                }

            } catch (IOException e) {
                System.out.println(e);
                e.printStackTrace();
            }
        }
            public void process() {

            }
            public void shutdown() {
                String command = "shutdown -s -t 00";
        
                try {
                    Process process = Runtime.getRuntime().exec(command);
                
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    reader.close();
            
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            public void registry() {

            }
            public void keylog() {

            }
            public void application() {

            }
            public void takepic() {


            }


                                    // case "KEYLOG": keylog(); break;
                                    // case "SHUTDOWN": shutdown(); break;
                                    // case "REGISTRY": registry(); break;
                                    // case "TAKEPIC": takepic(); break;
                                    // case "PROCESS": process(); break;
                                    // case "APPLICATION": application(); break;
    }

    

    private static void openServer() throws IOException{
        ServerSocket listener = null;

        System.out.println("Server is waiting to accept user...");
        int clientNumber = 0;

        // Mở một ServerSocket tại cổng 7777.
        // Chú ý bạn không thể chọn cổng nhỏ hơn 1023 nếu không là người dùng
        // đặc quyền (privileged users (root)).
        try {
            listener = new ServerSocket(5656);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }

        try {
            while (true) {

                // Chấp nhận một yêu cầu kết nối từ phía Client.
                // Đồng thời nhận được một đối tượng Socket tại server.

                Socket socketOfServer = listener.accept();
                new ServiceThread(socketOfServer, clientNumber++).start();
            }
        } finally {
            listener.close();
        }
    }

    public server() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 190, 188);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(50, 5, 5, 5));

        setContentPane(contentPane);

        JButton btnNewButton = new JButton("Mở Server");

        btnNewButton.setMinimumSize(new Dimension(100, 50));
        btnNewButton.setMaximumSize(new Dimension(100, 50));
        btnNewButton.setBackground(new Color(255, 255, 255));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    openServer();
                } catch (IOException e1) {
                    System.out.println(e1);
                    e1.printStackTrace();
                    // TODO Auto-generated catch block
                }
            }
        });
        contentPane.add(btnNewButton);
    }

}
