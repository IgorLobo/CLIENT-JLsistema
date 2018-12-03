package br.model;

import java.net.Socket;

public class ClientUser {
	private Socket socket = null;
	private int id = 0;
	private String ipClientUser = "";

	public ClientUser(Socket socket, int id, String ipClientUser) {
		this.socket = socket;
		this.id = id;
		this.ipClientUser = ipClientUser;
	}

}
