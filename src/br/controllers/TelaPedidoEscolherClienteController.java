package br.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.model.Cliente;
import br.persistencia.ClienteDAO;
import br.util.Janela;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class TelaPedidoEscolherClienteController implements Initializable {
//************************ ATRIBUTOS ********************************
	private Janela janelaUtil = new Janela();
	String nomeArquivo = TelaPrincipalController.caminhoTxtBancoDados + "Clientes.csv";
	ClienteDAO clienteDAO = new ClienteDAO(nomeArquivo);
	static Cliente clienteSelecionado;
//*********************** COMPONENTES *******************************	

	@FXML
	private TableView<Cliente> tableView_cliente;

	@FXML
	private TableColumn<Cliente, Integer> tc_ID;

	@FXML
	private TableColumn<Cliente, String> tc_nome;

	@FXML
	private TableColumn<Cliente, String> tc_email;

	@FXML
	private TableColumn<Cliente, String> tc_telefone;

	@FXML
	private ComboBox<String> cb_tipo;

	@FXML
	private Button btn_abrirPedido;

	@FXML
	private Button btn_cancelar;

//*********************** ON-ACTION *********************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prepararTableView();
		cb_tipo.setItems(FXCollections.observableArrayList("Venda", "Locação", "Infraestrutura"));
	}

	@FXML
	void OnClick_btn_abrirPedido(ActionEvent event) {

		if (!cb_tipo.getSelectionModel().isEmpty() && !tableView_cliente.getSelectionModel().isEmpty()) {
			clienteSelecionado = tableView_cliente.getSelectionModel().getSelectedItem();
			if (cb_tipo.getSelectionModel().getSelectedItem().toString().equals("Venda")) {
				janelaUtil.novaJanelaComOwner("/br/view/TelaPedidoVenda.fxml", false, "Pedido de venda");
			}
			if (cb_tipo.getSelectionModel().getSelectedItem().toString().equals("Locação")) {
				janelaUtil.novaJanelaComOwner("/br/view/TelaPedidoLoc.fxml", false, "Pedido de locação");
			}
			if (cb_tipo.getSelectionModel().getSelectedItem().toString().equals("Infraestrutura")) {
				janelaUtil.novaJanelaComOwner("/br/view/TelaPedidoInfra.fxml", false, "Pedido de infraestrutura");
			}
			br.util.Janela.fecharJanela(btn_abrirPedido);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText(null);
			if (cb_tipo.getSelectionModel().isEmpty())
				alert.setContentText("Escolha o tipo do pedido!");
			if (tableView_cliente.getSelectionModel().isEmpty())
				alert.setContentText("Escolha um cliente para abrir o pedido!");
			alert.show();
		}
	}

	@FXML
	void OnClick_btn_cancelar(ActionEvent event) {
		br.util.Janela.fecharJanela(btn_cancelar);
	}

//************************** METODOS AUXILIARES *********************
	private void prepararTableView() {
		tc_ID.setCellValueFactory(new PropertyValueFactory<>("codCliente"));
		tc_nome.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
		tc_email.setCellValueFactory(new PropertyValueFactory<>("email1Cliente"));
		tc_telefone.setCellValueFactory(new PropertyValueFactory<>("telefone1Cliente"));

		try {
			ArrayList<Cliente> listaDeClientes;
			listaDeClientes = clienteDAO.listarClientes();
			tableView_cliente.setItems(FXCollections.observableArrayList(listaDeClientes));
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}
}
