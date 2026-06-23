package edu.pucmm.eict.ormjpa.servicios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class LogAccesoServices {

    private static String getUrl() {
        return System.getenv("JDBC_DATABASE_URL");
    }

    public static void inicializar() {
        String url = getUrl();
        if (url == null || url.isBlank()) {
            System.out.println("JDBC_DATABASE_URL no configurada. El log de accesos esta desactivado.");
            return;
        }
        String sql = "create table if not exists log_acceso (usuario varchar(255), fecha_hora timestamp)";
        try (Connection con = DriverManager.getConnection(url);
             Statement st = con.createStatement()) {
            st.execute(sql);
            System.out.println("Tabla log_acceso lista en la base de datos externa.");
        } catch (SQLException e) {
            System.out.println("Error inicializando log de accesos: " + e.getMessage());
        }
    }

    public static void registrar(String usuario) {
        String url = getUrl();
        if (url == null || url.isBlank()) {
            return;
        }
        String sql = "insert into log_acceso (usuario, fecha_hora) values (?, ?)";
        try (Connection con = DriverManager.getConnection(url);
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            ps.executeUpdate();
            System.out.println("Acceso registrado para " + usuario);
        } catch (SQLException e) {
            System.out.println("Error registrando acceso: " + e.getMessage());
        }
    }
}
