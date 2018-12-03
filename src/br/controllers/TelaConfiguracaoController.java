package br.controllers;

import java.net.URL;
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

	}

	@FXML
	void OnClick_btn_aplicar(ActionEvent event) {
		br.util.Janela.fecharJanela(btn_aplicar);
	}

	@FXML
	void OnClick_btn_definir(ActionEvent event) {
		try {
			Runtime.getRuntime().exec("explorer c:\\");
			}catch(Exception e) {
				e.printStackTrace();
			}
	}
	// ************************** METODOS AUXILIARES *********************

}
