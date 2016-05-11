package application;

import java.sql.*;

public class ConnectionToken implements Comparable {

	private Connection con;
	private int connections;
	private long lastAccessed;
	private int id;

	public ConnectionToken(Connection con, int id, long lastAccessed) {
		this.con = con;
		this.id = id;
		this.lastAccessed = lastAccessed;
	}

	// called before getting a connection
	private void access(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}

	public long getLastAccessed() {return lastAccessed;}

	protected synchronized Connection getConnection(long accessCount) {
		access(accessCount);
		connections++;
		return con;
	}

	protected synchronized int closeConnection() {
		connections--;
		try {
			if (connections == 0)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return connections;
	}

	public void forceClose() {
		synchronized (con) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int getId() {return id;}

	public int getCount() {return connections;}

	public int compareTo(Object object) {
		ConnectionToken token = (ConnectionToken)object;
		return (connections-token.getCount()!=0?connections-token.getCount():lastAccessed-token.getLastAccessed()!=0?(int)(-(token.getLastAccessed()-lastAccessed)):id- token.getId());
	}
}