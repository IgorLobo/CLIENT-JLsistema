package br.controllers;

import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.model.Cliente;
import br.model.Infraestrutura;
import br.model.Pedido;
import br.model.Produto;
import br.persistencia.PedidoAluguelInfraDAO;
import br.persistencia.PedidoAluguelProdutoDAO;
import br.persistencia.PedidoVendaDAO;
import br.persistencia.ProdutoDAO;
import br.util.Janela;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener.Change;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaPedidoInfraController implements Initializable {

//************************ ATRIBUTOS ********************************
	private Janela janelaUtil = new Janela();
	static Cliente cliente;
	static ObservableList<Infraestrutura> obsSalas = FXCollections.observableArrayList();
	DecimalFormat df = new DecimalFormat("#.00");
//*********************** COMPONENTES *******************************	
	@FXML
	private Button btn_cancelar;

	@FXML
	private Button btn_finalizar;

	@FXML
	private Button btn_removerItem;

	@FXML
	private Button btn_adicionarItem;

	@FXML
	private TextField txf_nome;

	@FXML
	private TextField txf_cpf;

	@FXML
	private TextField txf_data;
//TABELA

	@FXML
	private TableView<Infraestrutura> tv_salas;

	@FXML
	private TableColumn<Infraestrutura, String> tc_nome;

	@FXML
	private TableColumn<Infraestrutura, String> tc_descricao;

	@FXML
	private TableColumn<Infraestrutura, String> tc_dias;

	@FXML
	private TableColumn<Infraestrutura, Float> tc_precoTotal;

	@FXML
	private Label lb_precoTotalPedido;
	@FXML
	private ComboBox<String> cb_pagamento;

//*********************** ON-ACTION *********************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prepararTableView();
		cb_pagamento.setItems(FXCollections.observableArrayList("Cartão", "Dinheiro"));
		cliente = TelaPedidoEscolherClienteController.clienteSelecionado;
		txf_nome.setText(cliente.getNomeCliente());
		txf_cpf.setText(cliente.getCpfCliente());

		txf_data.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));

	}

	@FXML
	void OnClick_btn_adicionarItem(ActionEvent event) {
		janelaUtil.novaJanelaComOwnerWait("/br/view/TelaPedidoEscolherInfra.fxml", false, "Adicionar item ao pedido");
	}

	@FXML
	void OnClick_btn_cancelar(ActionEvent event) {
		br.util.Janela.fecharJanela(btn_cancelar);
	}

	@FXML
	void OnClick_btn_finalizar(ActionEvent event) {
		if (cb_pagamento.getSelectionModel().isEmpty() || obsSalas.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText(null);
			alert.setContentText("Selecione um método de pagamento.");
			if (obsSalas.isEmpty())
				alert.setContentText("Adicione produtos á lista");
			alert.show();
		} else {
			try {
				Pedido pedido = new Pedido(cliente, new ArrayList<Infraestrutura>(obsSalas),
						cb_pagamento.getSelectionModel().getSelectedItem().toString(), "Infra", txf_data.getText(),Float.parseFloat(lb_precoTotalPedido.getText()));
				new PedidoAluguelInfraDAO(TelaPrincipalController.nomeArquivoPedidoLocInfra).incluirPedido(pedido);
				new PedidoAluguelInfraDAO(TelaPrincipalController.nomeArquivoDataLocInfraestrutura).incluirDataAluguelInfra(pedido);
				obsSalas.clear();
				br.util.Janela.fecharJanela(btn_finalizar);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	@FXML
	void OnClick_btn_removerItem(ActionEvent event) {
		if (tv_salas.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText(null);
			alert.setContentText("Selecione um produto para remover");
			alert.show();
		} else {
			obsSalas.remove(tv_salas.getSelectionModel().getSelectedItem());
		}
	}

//************************** METODOS AUXILIARES *********************
	private void prepararTableView() {
		tc_nome.setCellValueFactory(new PropertyValueFactory<>("nomeInfraestrutura"));
		tc_descricao.setCellValueFactory(new PropertyValueFactory<>("descricaoInfraestrutura"));
		tc_dias.setCellValueFactory(new PropertyValueFactory<>("dataLocacao"));
		tc_precoTotal.setCellValueFactory(new PropertyValueFactory<>("precoDiaInfraestrutura"));
		tv_salas.setItems(obsSalas);

		obsSalas.addListener(new ListChangeListener<Infraestrutura>() {
			@Override
			public void onChanged(Change<? extends Infraestrutura> c) {
				Float temp = 0f;
				for (Infraestrutura i : obsSalas) {
					temp += i.getPrecoDiaInfraestrutura();
				}
				lb_precoTotalPedido.setText(df.format(temp));
			}
		});

	}
}
