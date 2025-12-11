package utez.edu.mx.hotelback.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DBConnection {

    @Value("${db.url}")
    private String url;

    @Value("${db.user}")
    private String user;

    @Value("${db.password}")
    private String pass;

    @Bean
    public DataSource getConnection(){
        DriverManagerDataSource src = new DriverManagerDataSource();
        src.setDriverClassName("com.mysql.cj.jdbc.Driver");
        src.setUrl(url);
        src.setUsername(user);
        src.setPassword(pass);
        return src;
    }
}
