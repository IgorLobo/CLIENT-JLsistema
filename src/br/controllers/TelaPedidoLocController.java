package br.controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.model.Cliente;
import br.model.Pedido;
import br.model.Produto;
import br.persistencia.PedidoAluguelProdutoDAO;
import br.persistencia.ProdutoDAO;
import br.util.Janela;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TelaPedidoLocController implements Initializable {

//************************ ATRIBUTOS ********************************
	private Janela janelaUtil = new Janela();
	static Cliente cliente;
	static ObservableList<Produto> obsProdutos = FXCollections.observableArrayList();
	DecimalFormat df = new DecimalFormat("#.00");
	static int dias;
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
	private TableView<Produto> tv_produtos;

	@FXML
	private TableColumn<Produto, String> tc_nome;

	@FXML
	private TableColumn<Produto, String> tc_tipo;

	@FXML
	private TableColumn<Produto, String> tc_compatibilidade;

	@FXML
	private TableColumn<Produto, Float> tc_precoUnid;

	@FXML
	private TableColumn<Integer, Integer> tc_qnt;

	@FXML
	private TableColumn<Float, Float> tc_precoTotal;

	@FXML
	private TableColumn<Integer, Integer> tc_dias;

	@FXML
	private Label lb_precoTotalPedido;

	@FXML
	private ComboBox<String> cb_pagamento;

	@FXML
	private DatePicker date_fim;

	@FXML
	private DatePicker date_inicio;

//*********************** ON-ACTION *********************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prepararTableView();
		prepararComponentes();
		checkDataAtual();

	}

	@FXML
	void OnClick_btn_adicionarItem(ActionEvent event) {
		janelaUtil.novaJanelaComOwnerWait("/br/view/TelaPedidoEscolherProdutoLoc.fxml", false,
				"Adicionar item ao pedido");
	}

	@FXML
	void OnClick_btn_cancelar(ActionEvent event) {
		br.util.Janela.fecharJanela(btn_cancelar);
	}

	@FXML
	void OnClick_Date(ActionEvent event) {
		dias = (int) ChronoUnit.DAYS.between(date_inicio.getValue(), date_fim.getValue());
		btn_adicionarItem.setDisable(false);
		btn_removerItem.setDisable(false);
		Float temp = 0f;
		for (Produto produto : obsProdutos) {
			produto.setDias(dias);
			produto.setSubtotal(dias * produto.getValorUnitarioLocacao() * produto.getQuantidade());
			temp += produto.getSubtotal();
		}
		lb_precoTotalPedido.setText(df.format(temp));
		tv_produtos.refresh();

	}

	@FXML
	void OnClick_btn_finalizar(ActionEvent event) {
		if (cb_pagamento.getSelectionModel().isEmpty() || obsProdutos.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText(null);
			alert.setContentText("Selecione um método de pagamento.");
			if (obsProdutos.isEmpty())
				alert.setContentText("Adicione produtos á lista");
			alert.show();
		} else {
			try {
				Pedido pedido = new Pedido(cliente, new ArrayList<Produto>(obsProdutos),
						cb_pagamento.getSelectionModel().getSelectedItem().toString(),
						Float.parseFloat(lb_precoTotalPedido.getText()), "Aluguel", pegarData(date_inicio),
						pegarData(date_fim));
				new PedidoAluguelProdutoDAO(TelaPrincipalController.nomeArquivoPedidoLocProdutos).incluirPedido(pedido);
				for (Produto produto : obsProdutos) {
					switch (produto.getTipo()) {
					case "Jogo":
						new ProdutoDAO(TelaPrincipalController.nomeArquivoJogosLoc)
								.decrementarQuantidade(produto.getCodProduto(), produto.getQuantidade());
						break;
					case "Acessorio":
						new ProdutoDAO(TelaPrincipalController.nomeArquivoAcessoriosLoc)
								.decrementarQuantidade(produto.getCodProduto(), produto.getQuantidade());
						break;
					case "Console":
						new ProdutoDAO(TelaPrincipalController.nomeArquivoConsolesLoc)
								.decrementarQuantidade(produto.getCodProduto(), produto.getQuantidade());
						break;
					}
				}
				obsProdutos.clear();
				br.util.Janela.fecharJanela(btn_finalizar);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@FXML
	void OnClick_btn_removerItem(ActionEvent event) {
		if (tv_produtos.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText(null);
			alert.setContentText("Selecione um produto para remover");
			alert.show();
		} else {
			obsProdutos.remove(tv_produtos.getSelectionModel().getSelectedItem());
		}
	}

//************************** METODOS AUXILIARES *********************
	private void prepararTableView() {
		tc_nome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		tc_compatibilidade.setCellValueFactory(new PropertyValueFactory<>("compatibilidade"));
		tc_precoUnid.setCellValueFactory(new PropertyValueFactory<>("valorUnitarioLocacao"));
		tc_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		tc_qnt.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		tc_precoTotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
		tc_dias.setCellValueFactory(new PropertyValueFactory<>("dias"));
		tv_produtos.setItems(obsProdutos);

		obsProdutos.addListener(new ListChangeListener<Produto>() {
			@Override
			public void onChanged(Change<? extends Produto> c) {
				Float temp = 0f;
				for (Produto p : obsProdutos) {
					temp += p.getSubtotal();
				}
				lb_precoTotalPedido.setText(df.format(temp));
			}
		});
	}

	private void checkDataAtual() {

		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(date_inicio.getValue().plusDays(1))) {
							setDisable(true);
							setStyle("-fx-background-color: #333333;");
						}
					}
				};
			}
		};
		date_fim.setDayCellFactory(dayCellFactory);
	}

	private String pegarData(DatePicker date) {
		return date.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	private void prepararComponentes() {
		cb_pagamento.setItems(FXCollections.observableArrayList("Cartão", "Dinheiro"));
		cliente = TelaPedidoEscolherClienteController.clienteSelecionado;
		txf_nome.setText(cliente.getNomeCliente());
		txf_cpf.setText(cliente.getCpfCliente());
		date_inicio.setValue(LocalDate.now());
		date_inicio.setMouseTransparent(true);
		date_fim.requestFocus();
		btn_adicionarItem.setDisable(true);
		btn_removerItem.setDisable(true);

		date_fim.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});

		date_inicio.setConverter(new StringConverter<LocalDate>() {
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			@Override
			public String toString(LocalDate date) {
				if (date != null) {
					return dateFormatter.format(date);
				} else {
					return "";
				}
			}

			@Override
			public LocalDate fromString(String string) {
				if (string != null && !string.isEmpty()) {
					return LocalDate.parse(string, dateFormatter);
				} else {
					return null;
				}
			}
		});
	}
}