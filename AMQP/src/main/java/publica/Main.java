package publica;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.io.FileWriter;
import java.io.IOException;

public class Main {
    private final static String QUEUE_NAME = "fila";
    private final static String LOG_FILE = "mensagens_enviadas.log";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String mensagem = "lenda";
            
            // Armazenar a mensagem enviada no arquivo de log
            logMessage(mensagem);
            
            channel.basicPublish("", QUEUE_NAME, null, mensagem.getBytes());
            System.out.println(" [x] Enviado '" + mensagem + "'");
        }
    }
    
    private static void logMessage(String mensagem) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(mensagem + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
