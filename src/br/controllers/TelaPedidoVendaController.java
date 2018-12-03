package br.controllers;

import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.model.Cliente;
import br.model.Pedido;
import br.model.Produto;
import br.persistencia.PedidoVendaDAO;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaPedidoVendaController implements Initializable {

//************************ ATRIBUTOS ********************************
	private Janela janelaUtil = new Janela();
	static Cliente cliente;
	static ObservableList<Produto> obsProdutos = FXCollections.observableArrayList();
	DecimalFormat df = new DecimalFormat("#.00");

//*********************** COMPONENTES *******************************	
	@FXML
	private Button btn_cancelar;

	@FXML
	private Button btn_finalizar;

	@FXML
	private Button btn_cliente;

	@FXML
	private Button btn_removerItem;

	@FXML
	private Button btn_adicionarItem;

	@FXML
	private TextField txf_nome;

	@FXML
	private TextField txf_cpf;

	@FXML
	private ComboBox<String> cb_tipo;

	@FXML
	private ComboBox<String> cb_pagamento;

	@FXML
	private TextField txf_data;

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
	private TableColumn<Produto, Integer> tc_qnt;

	@FXML
	private TableColumn<Produto, Float> tc_precoTotal;

	@FXML
	private Label lb_precoTotalPedido;

//*********************** ON-ACTION *********************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prepararTableView();
		cb_tipo.setPromptText("Venda");
		cb_tipo.setDisable(true);
		cb_pagamento.setItems(FXCollections.observableArrayList("Cartão", "Dinheiro"));
		cliente = TelaPedidoEscolherClienteController.clienteSelecionado;
		txf_nome.setText(cliente.getNomeCliente());
		txf_cpf.setText(cliente.getCpfCliente());
		txf_data.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date()));
	}

	@FXML
	void OnClick_btn_adicionarItem(ActionEvent event) {
		janelaUtil.novaJanelaComOwnerWait("/br/view/TelaPedidoEscolherProdutoVenda.fxml", false,
				"Adicionar item ao pedido");
	}

	@FXML
	void OnClick_btn_cancelar(ActionEvent event) {
		br.util.Janela.fecharJanela(btn_cancelar);
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
						Float.parseFloat(lb_precoTotalPedido.getText()), txf_data.getText());
				new PedidoVendaDAO(TelaPrincipalController.nomeArquivoPedidoVenda).incluirPedido(pedido);
				for (Produto produto : obsProdutos) {
					switch (produto.getTipo()) {
					case "Jogo":
						new ProdutoDAO(TelaPrincipalController.nomeArquivoJogos)
								.decrementarQuantidade(produto.getCodProduto(), produto.getQuantidade());
						break;
					case "Acessorio":
						new ProdutoDAO(TelaPrincipalController.nomeArquivoAcessorios)
								.decrementarQuantidade(produto.getCodProduto(), produto.getQuantidade());
						break;
					case "Console":
						new ProdutoDAO(TelaPrincipalController.nomeArquivoConsoles)
								.decrementarQuantidade(produto.getCodProduto(), produto.getQuantidade());
						break;
					}
				}
				obsProdutos.clear();
				br.util.Janela.fecharJanela(btn_finalizar);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Atenção", JOptionPane.INFORMATION_MESSAGE);
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
		tc_precoUnid.setCellValueFactory(new PropertyValueFactory<>("valorUnitarioVenda"));
		tc_tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
		tc_qnt.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
		tc_precoTotal.setCellValueFactory(new PropertyValueFactory<>("subtotal"));
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

}
