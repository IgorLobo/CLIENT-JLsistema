package br.connection;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.controllers.TelaPrincipalController;
import br.model.ClientUser;

public class Servidor implements Runnable {

	private ObjectOutputStream objectOutputStream = null;
	private ObjectInputStream objectInputStream = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	private List<ClientUser> clientUserArrayList = null;

	private Thread runServidor = null;
	private ServerSocket serverSocket = null;
	public volatile static boolean statusServidor = false;
	private int portaServidor = 5555;

	public static ClientUser clientUser = null;

	public Servidor(int porta) {
		this.runServidor = null;
		this.serverSocket = null;
		this.statusServidor = false;
		this.portaServidor = porta;

		clientUserArrayList = new ArrayList<ClientUser>();
	}

	public void setClientsUsers(ClientUser clientUser) {
		this.clientUserArrayList.add(clientUser);
	}
	
	public String getClientUsers() {
		String resposta = "";
		for (int i = 0; i < clientUserArrayList.size(); i++) {
			resposta += clientUserArrayList.get(i) + "\r\n";
		}
		return resposta;
	}

	public Thread getRunServidor() {
		return runServidor;
	}

	public void setRunServidor(Thread runServidor) {
		this.runServidor = runServidor;
	}

	public ServerSocket getServerSocket() {
		return serverSocket;
	}

	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public boolean getStatusServidor() {
		return statusServidor;
	}

	public void setStatusServidor(boolean statusServidor) {
		this.statusServidor = statusServidor;
	}

	public int getPortaServidor() {
		return portaServidor;
	}

	public void setPortaServidor(int portaServidor) {
		this.portaServidor = portaServidor;
	}

	public synchronized void startServer() throws IOException {
		if (runServidor == null) {
			this.serverSocket = new ServerSocket(this.portaServidor);
			this.runServidor = new Thread(this, "Thread-Servidor");
			this.runServidor.start();
			this.statusServidor = true;
		}
	}

	public synchronized void stopServer() throws IOException {

		if (serverSocket != null && runServidor != null) {
			this.statusServidor = false;
			this.runServidor.interrupt();
			this.runServidor = null;
			try {
				this.serverSocket.close();

			} catch (IOException e) {
				System.out.println(e.getLocalizedMessage());
			}
		}
	}

	@Override
	public void run() {
		int id = 0;
		String ipClientUser = "";
		clientUser = null;
		try {
			while (true) {
				Socket socket = this.serverSocket.accept();
				System.out.println("Entrou o trodos");

				/*objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

				objectOutputStream.writeUTF("");
				objectOutputStream.flush();*/


				ipClientUser = socket.getInetAddress().getHostAddress();
				clientUser = new ClientUser(socket, id, ipClientUser);
				TelaPrincipalController.servidor.setClientsUsers(clientUser);
				id++;
				/*objectInputStream.close();
				objectOutputStream.close();
				socket.close();*/
			}
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}
}
