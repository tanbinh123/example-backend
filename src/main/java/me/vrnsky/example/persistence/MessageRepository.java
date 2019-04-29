package me.vrnsky.example.persistence;

import me.vrnsky.example.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MessageRepository {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public MessageRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Message addMessage(String text) throws Exception {
        Message message = new Message();
        message.setCreatedAt(LocalDateTime.now());
        message.setText(text);
        message.setId(-1L);
        Connection connection = DataSourceUtils.getConnection(dataSource);
        PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO MESSAGE (TEXT, CREATED_AT) VALUES(?, ?)",
                Statement.RETURN_GENERATED_KEYS
        );
//        statement.setLong(1, message.getId());
        statement.setString(1, message.getText());
        statement.setTimestamp(2, Timestamp.valueOf(message.getCreatedAt()));
        statement.execute();
        ResultSet generatedKeys = statement.getGeneratedKeys();
        while (generatedKeys.next()) {
            message.setId(generatedKeys.getLong("ID"));
        }
        DataSourceUtils.releaseConnection(connection, dataSource);
        return message;
    }

    public List<Message> listMessages() throws Exception {
        Connection connection = DataSourceUtils.getConnection(dataSource);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM MESSAGE");
        List<Message> messages = new ArrayList<>();
        while (resultSet.next()) {
            messages.add(new Message(
                    resultSet.getLong("ID"),
                    resultSet.getString("TEXT"),
                    resultSet.getTimestamp("CREATED_AT").toLocalDateTime()
            ));
        }

        return messages;
    }
}
