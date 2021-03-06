package application;

import java.sql.*;
import java.util.*;

public class Connector {

	private final TreeSet<ConnectionToken> connections = new TreeSet<>();
	private long lastAccessed;
	private int count;
	private final String username;
	private final String password;

	public Connector(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public synchronized ConnectionToken getToken() {
		ConnectionToken token = null;
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=" + username + "&password=" + password);
			count++;
			token = new ConnectionToken(con, count, lastAccessed);
		} catch (SQLException e) {
			e.printStackTrace();
			if (connections.size() > 0) {
				token = connections.first();
			}
		}
		return token;
	}

	public Connection getConnection(ConnectionToken token) {
		Connection con = null;
		synchronized (this) {
			con = token.getConnection(++lastAccessed);
		}
		return con;
	}

	public boolean closeConnection(ConnectionToken token) {
		int count = -1;
		synchronized (this) {
			count = token.closeConnection();
			if (count == 0) {
				connections.remove(token);
			}
		}
		return count >= 0;
	}

	protected void closeAll() {
		Iterator<ConnectionToken> it = connections.iterator();
		while (it.hasNext()) {
			it.next().forceClose();
			it.remove();
		}
	}
}
