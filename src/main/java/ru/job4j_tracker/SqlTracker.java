package ru.job4j_tracker;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class SqlTracker implements Store {

    private Connection cn;

    public SqlTracker() {
    }

    public SqlTracker(Connection connection) {
        this.cn = connection;
    }

    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() {
        if (cn != null) {
            try {
                cn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Item add(Item item) {
        try (PreparedStatement st = cn.prepareStatement("insert into  items (name)  values  (?)", Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, item.getName());
            st.executeUpdate();
            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int key = generatedKeys.getInt(1);
                    item.setId(String.valueOf(key));
                    return item;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(String id, Item item) {
        int whenreplaced = 0;
        try (PreparedStatement st = cn.prepareStatement("update items set name = ? where id = ?")) {
            st.setString(1, item.getName());
            st.setInt(2, Integer.parseInt(id));
            st.executeUpdate();
            whenreplaced++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return whenreplaced != 0;
    }

    @Override
    public boolean delete(String id) {
        int whendelete = 0;
        try (PreparedStatement st = cn.prepareStatement("delete from items where id = ?")) {
            st.setInt(1, Integer.parseInt(id));
            st.executeUpdate();
            whendelete++;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return whendelete != 0;
    }

    @Override
    public List<Item> findAll() {
        List<Item> res = new ArrayList<>();
        try (PreparedStatement st = cn.prepareStatement("select * from items order by id")) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Item item = new Item(rs.getString("name"));
                item.setId(String.valueOf(rs.getInt("id")));
                res.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public List<Item> findByName(String name) {
        List<Item> res = new ArrayList<>();
        try (PreparedStatement st = cn.prepareStatement("select * from items where name = ?")) {
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Item item = new Item(name);
                item.setId(String.valueOf(rs.getInt("id")));
                res.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public Item findById(String id) {
        Item item = null;
        try (PreparedStatement st = cn.prepareStatement("select * from items where id = ?")) {
            st.setInt(1, Integer.parseInt(id));
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                item = new Item(rs.getString("name"));
                item.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }
}

