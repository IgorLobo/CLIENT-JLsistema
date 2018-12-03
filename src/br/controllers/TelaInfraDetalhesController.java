package br.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.model.Infraestrutura;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class TelaInfraDetalhesController implements Initializable {
//************************ ATRIBUTOS ********************************

	static Infraestrutura infra;
//*********************** COMPONENTES *******************************	
	@FXML
	private TextField txf_nome;

	@FXML
	private TextField txf_valor;

	@FXML
	private TextArea txa_descricao;

	@FXML
	private Button btn_ok;

//*********************** ON-ACTION *********************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		txf_nome.setText(infra.getNomeInfraestrutura());
		txf_valor.setText(Float.toString(infra.getPrecoDiaInfraestrutura()));
		txa_descricao.setText(infra.getDescricaoInfraestrutura());
	}

	@FXML
	void OnClick_btn_ok(ActionEvent event) {
		br.util.Janela.fecharJanela(btn_ok);
	}
//************************** METODOS AUXILIARES *********************

}
