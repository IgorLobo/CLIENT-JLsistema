package br.controllers;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

import br.util.Janela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class TelaConfiguracaoController implements Initializable {
	// ************************ ATRIBUTOS ********************************
	public static String hostName = "";
	public static String ipServer = "";
	public static int porta = 0;
	public static String caminhoArquivoDB = "";
	// *********************** COMPONENTES *******************************
	@FXML
	private TextField txf_IP;

    @FXML
    private Label lb_host;

	@FXML
	private TextField txf_porta;

	@FXML
	private TextField txf_diretorio;

	@FXML
	private Button btn_definir;

	@FXML
	private Button btn_aplicar;

	// *********************** ON-ACTION *********************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			setComponetes();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	@FXML
	void OnClick_btn_aplicar(ActionEvent event) {
		porta = Integer.parseInt(txf_porta.getText());
		TelaPrincipalController.servidorON = true;
		br.util.Janela.fecharJanela(btn_aplicar);
	}

	@FXML
	void OnClick_btn_definir(ActionEvent event) {
		try {
			Runtime.getRuntime().exec("explorer "+txf_diretorio.getText());
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	// ************************** METODOS AUXILIARES *********************
	private void setComponetes() throws UnknownHostException {
		txf_IP.setText("");
		lb_host.setText(InetAddress.getLocalHost().getHostName());
		txf_porta.setText("");
		txf_diretorio.setText("Caminho do servidor");
		txf_diretorio.setDisable(true);
		btn_definir.setDisable(true);		
	}
}
