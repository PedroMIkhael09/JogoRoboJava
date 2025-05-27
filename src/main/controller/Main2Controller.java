package main.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import main.exception.MovimentoInvalidoException;
import main.model.robos.Robo;

import java.util.Random;

public class Main2Controller {

    @FXML private GridPane gridPane;
    @FXML private Label statusLabel;
    @FXML private Label infoLabel;
    @FXML private ColorPicker colorPicker1;
    @FXML private ColorPicker colorPicker2;
    @FXML private Button iniciarButton;
    @FXML private Button resetButton;

    private final int tamanho = 4;
    private Robo robo1, robo2;
    private int alimentoX = -1, alimentoY = -1;
    private boolean jogoIniciado = false;
    private boolean jogoCompleto = false;
    private Random random = new Random();
    private String[] direcoes = {"up", "down", "left", "right"};
    private Timeline timeline;

    private final Color COR_FUNDO = Color.web("#f8f9fa");
    private final Color COR_BORDA = Color.web("#6c757d");
    private final Color COR_CELULA = Color.web("#ffffff");
    private final Color COR_TEXTO_PRINCIPAL = Color.web("#212529");
    private final Color COR_TEXTO_SECUNDARIO = Color.web("#6c757d");
    private final Color COR_SUCESSO = Color.web("#495057");
    private final Color COR_ERRO = Color.web("#343a40");

    @FXML
    public void initialize() {
        configurarEstilos();
        desenharTabuleiro();
    }

    private void configurarEstilos() {
        statusLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        infoLabel.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        colorPicker1.setValue(Color.web("#2c3e50"));
        colorPicker2.setValue(Color.web("#8e44ad"));
    }

    public void iniciarJogo() {
        Color cor1 = colorPicker1.getValue();
        Color cor2 = colorPicker2.getValue();

        if (toHexString(cor1).equals(toHexString(cor2))) {
            statusLabel.setText("ERRO: Escolha cores diferentes para os robôs");
            statusLabel.setTextFill(COR_ERRO);
            return;
        }

        robo1 = new Robo(toHexString(cor1));
        robo2 = new Robo(toHexString(cor2));
        statusLabel.setText("⚡ Clique no tabuleiro para posicionar a bateria");
        statusLabel.setTextFill(COR_TEXTO_PRINCIPAL);
        jogoIniciado = false;
        jogoCompleto = false;
        iniciarButton.setDisable(true);
        resetButton.setDisable(false);
        desenharTabuleiro();
        atualizarInfo();
    }

    public void resetarJogo() {
        if (timeline != null) {
            timeline.stop();
        }
        robo1 = null;
        robo2 = null;
        alimentoX = -1;
        alimentoY = -1;
        jogoIniciado = false;
        jogoCompleto = false;
        statusLabel.setText("🤖 Configure os robôs e inicie o jogo");
        statusLabel.setTextFill(COR_TEXTO_PRINCIPAL);
        iniciarButton.setDisable(false);
        resetButton.setDisable(true);
        desenharTabuleiro();
        atualizarInfo();
    }

    private void desenharTabuleiro() {
        gridPane.getChildren().clear();

        for (int row = 0; row < tamanho; row++) {
            for (int col = 0; col < tamanho; col++) {
                StackPane cell = criarCelula(col, row);
                gridPane.add(cell, col, row);
            }
        }
    }

    private StackPane criarCelula(int col, int row) {
        StackPane cell = new StackPane();
        Rectangle rect = new Rectangle(80, 80);
        rect.setStroke(COR_BORDA);
        rect.setStrokeWidth(1.5);
        rect.setFill(COR_CELULA);
        rect.setArcWidth(8);
        rect.setArcHeight(8);

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.web("#00000020"));
        shadow.setOffsetX(1);
        shadow.setOffsetY(1);
        shadow.setRadius(3);
        rect.setEffect(shadow);

        int finalCol = col;
        int finalRow = row;

        cell.setOnMouseClicked(event -> {
            if (!jogoIniciado && robo1 != null && robo2 != null && !jogoCompleto) {
                alimentoX = finalCol;
                alimentoY = (tamanho - 1) - finalRow;
                jogoIniciado = true;
                statusLabel.setText("⚡ Bateria posicionada! Jogo iniciado");
                statusLabel.setTextFill(COR_SUCESSO);
                desenharTabuleiro();
                iniciarMovimentacaoAutomatica();
            }
        });

        int gridRow = (tamanho - 1) - row;
        cell.getChildren().add(rect);

        boolean robo1Presente = robo1 != null && col == robo1.getPosicaoX() && gridRow == robo1.getPosicaoY();
        boolean robo2Presente = robo2 != null && col == robo2.getPosicaoX() && gridRow == robo2.getPosicaoY();
        boolean alimentoPresente = col == alimentoX && gridRow == alimentoY;

        if (robo1Presente && robo2Presente) {
            HBox container = new HBox(5);
            container.setAlignment(Pos.CENTER);
            container.getChildren().addAll(criarSpriteRobo(robo1.getCor(), true), criarSpriteRobo(robo2.getCor(), true));
            cell.getChildren().add(container);
        } else if (robo1Presente) {
            cell.getChildren().add(criarSpriteRobo(robo1.getCor(), false));
        } else if (robo2Presente) {
            cell.getChildren().add(criarSpriteRobo(robo2.getCor(), false));
        }

        if (alimentoPresente) {
            cell.getChildren().add(criarSpriteBateria());
        }

        return cell;
    }

    private StackPane criarSpriteRobo(String corHex, boolean pequeno) {
        StackPane robo = new StackPane();
        Color roboCor = Color.web(corHex);
        double size = pequeno ? 0.7 : 1.0;

        Rectangle corpo = new Rectangle(50 * size, 35 * size);
        corpo.setFill(roboCor);
        corpo.setArcWidth(6 * size);
        corpo.setArcHeight(6 * size);

        Rectangle cabeca = new Rectangle(30 * size, 10 * size);
        cabeca.setFill(roboCor);
        cabeca.setTranslateY(-25 * size);

        Rectangle bracoEsq = new Rectangle(8 * size, 25 * size);
        bracoEsq.setFill(roboCor);
        bracoEsq.setTranslateX(-30 * size);

        Rectangle bracoDir = new Rectangle(8 * size, 25 * size);
        bracoDir.setFill(roboCor);
        bracoDir.setTranslateX(30 * size);

        Rectangle pernaEsq = new Rectangle(8 * size, 15 * size);
        pernaEsq.setFill(roboCor);
        pernaEsq.setTranslateX(-10 * size);
        pernaEsq.setTranslateY(25 * size);

        Rectangle pernaDir = new Rectangle(8 * size, 15 * size);
        pernaDir.setFill(roboCor);
        pernaDir.setTranslateX(10 * size);
        pernaDir.setTranslateY(25 * size);

        Rectangle olhoEsq = new Rectangle(5 * size, 5 * size);
        olhoEsq.setFill(Color.WHITE);
        olhoEsq.setTranslateX(-8 * size);
        olhoEsq.setTranslateY(-25 * size);

        Rectangle olhoDir = new Rectangle(5 * size, 5 * size);
        olhoDir.setFill(Color.WHITE);
        olhoDir.setTranslateX(8 * size);
        olhoDir.setTranslateY(-25 * size);

        robo.getChildren().addAll(corpo, cabeca, bracoEsq, bracoDir, pernaEsq, pernaDir, olhoEsq, olhoDir);
        return robo;
    }

    private StackPane criarSpriteBateria() {
        StackPane bateria = new StackPane();
        Rectangle corpo = new Rectangle(35, 50);
        corpo.setFill(Color.web("#495057"));
        corpo.setStroke(Color.BLACK);
        corpo.setStrokeWidth(2);
        corpo.setArcWidth(4);
        corpo.setArcHeight(4);

        Rectangle topo = new Rectangle(15, 8);
        topo.setFill(Color.web("#6c757d"));
        topo.setStroke(Color.BLACK);
        topo.setStrokeWidth(1);
        topo.setTranslateY(-29);

        bateria.getChildren().addAll(corpo, topo);
        return bateria;
    }

    private void iniciarMovimentacaoAutomatica() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
            if (!jogoCompleto) {
                moverRoboAleatoriamente(robo1);
                if (!jogoCompleto) {
                    moverRoboAleatoriamente(robo2);
                }
                desenharTabuleiro();
                atualizarInfo();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moverRoboAleatoriamente(Robo robo) {
        String direcao = direcoes[random.nextInt(4)];

        try {
            boolean movimentoRealizado = robo.tentarMover(direcao);

            if (movimentoRealizado) {
                statusLabel.setText("Robô " + robo.getCor() + " moveu para " + direcao);
                statusLabel.setTextFill(COR_TEXTO_PRINCIPAL);

                if (robo.encontrarAlimento(alimentoX, alimentoY)) {
                    jogoCompleto = true;
                    timeline.stop();
                    Platform.runLater(() -> mostrarVencedor(robo));
                }
            } else {
                statusLabel.setText("Robô " + robo.getCor() + " tentou sair do tabuleiro! Perdeu a vez.");
                statusLabel.setTextFill(COR_ERRO);
            }
        } catch (MovimentoInvalidoException e) {
            statusLabel.setText("Robô " + robo.getCor() + " tentou movimento inválido: " + e.getMessage());
            statusLabel.setTextFill(COR_ERRO);
        }
    }
    private void mostrarVencedor(Robo vencedor) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fim do Jogo");
        alert.setHeaderText("Robô " + vencedor.getCor() + " venceu!");
        alert.setContentText(
                "O robô vencedor foi o de cor: " + vencedor.getCor() + "\n\n" +
                        "Movimentos do robô " + robo1.getCor() + ":\n" +
                        "  Válidos: " + robo1.getMovimentosValidos() + "\n" +
                        "  Inválidos: " + robo1.getMovimentosInvalidos() + "\n\n" +
                        "Movimentos do robô " + robo2.getCor() + ":\n" +
                        "  Válidos: " + robo2.getMovimentosValidos() + "\n" +
                        "  Inválidos: " + robo2.getMovimentosInvalidos()
        );
        alert.showAndWait();
    }

    private void atualizarInfo() {
        if (robo1 != null && robo2 != null) {
            String info = String.format(
                    "Robô %s: (%d,%d) | Válidos: %d | Inválidos: %d\n" +
                            "Robô %s: (%d,%d) | Válidos: %d | Inválidos: %d",
                    robo1.getCor(), robo1.getPosicaoX(), robo1.getPosicaoY(), robo1.getMovimentosValidos(), robo1.getMovimentosInvalidos(),
                    robo2.getCor(), robo2.getPosicaoX(), robo2.getPosicaoY(), robo2.getMovimentosValidos(), robo2.getMovimentosInvalidos()
            );
            infoLabel.setText(info);
        }
    }

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}