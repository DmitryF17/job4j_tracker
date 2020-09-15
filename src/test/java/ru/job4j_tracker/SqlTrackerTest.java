package ru.job4j_tracker;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SqlTrackerTest {

    public Connection init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Test
    public void createItem() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name"));
            assertThat(tracker.findByName("name").size(), is(1));
        }
    }

    @Test
    public void replaceItem() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item = new Item("name");
            tracker.add(item);
            tracker.replace(item.getId(), new Item("newname"));
            assertThat(tracker.findById(item.getId()).getName(), is("newname"));
        }
    }

    @Test
    public void deleteTest() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item = new Item("name");
            tracker.add(item);
            tracker.delete(item.getId());
            assertThat(tracker.findAll().size(), is(0));
        }
    }

    @Test
    public void findAllItem() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name1"));
            tracker.add(new Item("name2"));
            assertThat(tracker.findAll().size(), is(2));
        }
    }

    @Test
    public void findByName() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            tracker.add(new Item("name1"));
            tracker.add(new Item("name2"));
            assertThat(tracker.findByName("name1").size(), is(1));
        }
    }

    @Test
    public void findById() throws SQLException {
        try (SqlTracker tracker = new SqlTracker(ConnectionRollback.create(this.init()))) {
            Item item1 = new Item("name1");
            Item item2 = new Item("name2");
            tracker.add(item1);
            tracker.add(item2);
            assertThat(tracker.findById(item1.getId()).getName(), is("name1"));
        }
    }
}
