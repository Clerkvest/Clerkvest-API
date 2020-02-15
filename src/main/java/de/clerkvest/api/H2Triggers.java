package de.clerkvest.api;

import org.h2.api.Trigger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class H2Triggers {
    public static class InvestmentInsertTrigger implements Trigger {
        @Override
        public void init(Connection connection, String s, String s1, String s2, boolean b, int i) {

        }

        @Override
        public void fire(Connection connection, Object[] oldRow, Object[] newRow) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE project SET invested_in = invested_in + ? WHERE project_id=?")
            ) {
                stmt.setObject(1, newRow[3]);
                stmt.setObject(2, newRow[1]);

                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE employee SET balance = balance - ? WHERE employee_id=?")
            ) {
                stmt.setObject(1, newRow[3]);
                stmt.setObject(2, newRow[2]);

                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = connection.prepareStatement(
                    "SELECT invested_in,goal FROM project WHERE project_id=?")
            ) {
                stmt.setObject(1, newRow[1]);
                BigDecimal investIn, goal;
                ResultSet resultSet = stmt.executeQuery();
                if (resultSet.next()) {
                    investIn = resultSet.getBigDecimal("invested_in");
                    goal = resultSet.getBigDecimal("goal");
                    if (investIn.compareTo(goal) >= 0) {
                        try (PreparedStatement stmt2 = connection.prepareStatement(
                                "UPDATE project SET reached = TRUE WHERE project_id=?")
                        ) {
                            stmt2.setObject(1, newRow[1]);

                            stmt2.executeUpdate();
                        }
                    }
                }

            }


        }

        @Override
        public void close() {

        }

        @Override
        public void remove() {

        }
    }

    public static class InvestmentDeleteTrigger implements Trigger {
        @Override
        public void init(Connection conn, String schemaName, String triggerName, String tableName, boolean before, int type) {

        }

        @Override
        public void fire(Connection connection, Object[] oldRow, Object[] newRow) throws SQLException {
            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE project SET invested_in = invested_in - ? WHERE project_id=?")
            ) {
                stmt.setObject(1, newRow[3]);
                stmt.setObject(2, newRow[1]);

                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = connection.prepareStatement(
                    "UPDATE employee SET balance = balance + ? WHERE employee_id=?")
            ) {
                stmt.setObject(1, newRow[3]);
                stmt.setObject(2, newRow[2]);

                stmt.executeUpdate();
            }

        }

        @Override
        public void close() {

        }

        @Override
        public void remove() {

        }
    }
}
