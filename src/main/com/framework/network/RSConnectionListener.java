package com.framework.network;

/**
 * The {@code RSConnectionListener} handles the connect and disconnect of a
 * session.
 * 
 * @author Albert Beaupre
 */
public interface RSConnectionListener {

	/**
	 * This method is called when the session is connected.
	 * 
	 * @param session the session connected
	 */
	void onConnect(RSNetworkSession session);

	/**
	 * This method is called when the session is disconnected.
	 * 
	 * @param session disconnected
	 */
	void onDisconnect(RSNetworkSession session);

	/**
	 * The default {@code RSConnectionListener} set
	 */
	static RSConnectionListener DefaultListener = new RSConnectionListener() {
		public void onConnect(RSNetworkSession session) {}

		public void onDisconnect(RSNetworkSession session) {}
	};
}
