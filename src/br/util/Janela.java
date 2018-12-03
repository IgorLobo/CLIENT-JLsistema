package br.util;

import java.io.IOException;

import br.application.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Janela {

	public void novaJanelaComOwner(String caminhoFXML, boolean resizable, String nomeDaJanela) {
		try {
			Parent root;
			root = FXMLLoader.load(getClass().getResource(caminhoFXML));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/br/view/estilo.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.getIcons().add(new Image("/br/images/JLsistemaLOGO.png"));
			stage.setTitle(nomeDaJanela);
			stage.setResizable(resizable);
			stage.initOwner(Main.mainStage);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void novaJanelaComOwnerWait(String caminhoFXML, boolean resizable, String nomeDaJanela) {
		try {
			Parent root;
			root = FXMLLoader.load(getClass().getResource(caminhoFXML));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("/br/view/estilo.css").toExternalForm());
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle(nomeDaJanela);
			stage.getIcons().add(new Image("/br/images/JLsistemaLOGO.png"));
			stage.setResizable(resizable);
			stage.initOwner(Main.mainStage);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void novaJanela(String caminhoFXML, boolean resizable) {
		try {
			Parent root;
			root = FXMLLoader.load(getClass().getResource(caminhoFXML));
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.getIcons().add(new Image("/br/images/JLsistemaLOGO.png"));
			stage.setResizable(resizable);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void fecharJanela(Node node) {
		Stage cena = (Stage) node.getScene().getWindow();
		cena.close();
	}

}
