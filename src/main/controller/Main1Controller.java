package main.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import main.exception.MovimentoInvalidoException;
import main.model.robos.Robo;

public class Main1Controller {

    @FXML
    private GridPane gridPane;
    @FXML
    private Label statusLabel;
    @FXML
    private Label movimentosLabel;
    @FXML
    private Label posicaoLabel;
    @FXML
    private ColorPicker colorPicker;
    @FXML
    private TextField direcaoInput;
    @FXML
    private Button iniciarButton;
    @FXML
    private Button resetButton;

    private final int tamanho = 4;
    private Robo robo;
    private int alimentoX = -1;
    private int alimentoY = -1;
    private int movimentos = 0;
    private boolean jogoIniciado = false;
    private boolean jogoCompleto = false;

    // a bct dessas cor
    private final Color COR_FUNDO = Color.web("#f8f9fa");
    private final Color COR_BORDA = Color.web("#6c757d");
    private final Color COR_CELULA = Color.web("#ffffff");
    private final Color COR_HOVER = Color.web("#e9ecef");
    private final Color COR_TEXTO_PRINCIPAL = Color.web("#212529");
    private final Color COR_TEXTO_SECUNDARIO = Color.web("#6c757d");
    private final Color COR_SUCESSO = Color.web("#495057");
    private final Color COR_ERRO = Color.web("#343a40");

    @FXML
    public void initialize() {
        configurarEstilos();
        desenharTabuleiro();
        atualizarLabels();
    }

    private void configurarEstilos() {
        statusLabel.setFont(Font.font("Consolas", FontWeight.BOLD, 14));
        movimentosLabel.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));
        posicaoLabel.setFont(Font.font("Consolas", FontWeight.NORMAL, 12));

        colorPicker.setValue(Color.web("#2c3e50"));

        direcaoInput.setPromptText("up, down, left, right");
    }

    public void iniciarJogo() {
        Color cor = colorPicker.getValue();
        robo = new Robo(toHexString(cor));
        statusLabel.setText("⚡ Clique no tabuleiro para posicionar a bateria");
        statusLabel.setTextFill(COR_TEXTO_PRINCIPAL);
        jogoIniciado = false;
        jogoCompleto = false;
        movimentos = 0;
        robo.setPosicaoX(0);
        robo.setPosicaoY(0);

        iniciarButton.setDisable(true);
        resetButton.setDisable(false);

        desenharTabuleiro();
        atualizarLabels();
    }

    public void resetarJogo() {
        robo = null;
        alimentoX = -1;
        alimentoY = -1;
        jogoIniciado = false;
        jogoCompleto = false;
        movimentos = 0;

        statusLabel.setText("🤖 Configure o robô e inicie o jogo");
        statusLabel.setTextFill(COR_TEXTO_PRINCIPAL);

        iniciarButton.setDisable(false);
        resetButton.setDisable(true);

        desenharTabuleiro();
        atualizarLabels();
        direcaoInput.clear();
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

        cell.setOnMouseEntered(e -> {
            if (!jogoIniciado && robo != null && !jogoCompleto) {
                rect.setFill(COR_HOVER);
                rect.setStroke(COR_TEXTO_PRINCIPAL);
                rect.setStrokeWidth(2);
            }
        });

        cell.setOnMouseExited(e -> {
            if (!jogoIniciado && robo != null && !jogoCompleto) {
                rect.setFill(COR_CELULA);
                rect.setStroke(COR_BORDA);
                rect.setStrokeWidth(1.5);
            }
        });

        cell.setOnMouseClicked(event -> {
            if (!jogoIniciado && robo != null && !jogoCompleto) {
                alimentoX = finalCol;
                alimentoY = (tamanho - 1) - finalRow;
                jogoIniciado = true;
                statusLabel.setText("⚡ Bateria posicionada! Mova o robô para coletá-la");
                statusLabel.setTextFill(COR_SUCESSO);
                desenharTabuleiro();
                atualizarLabels();
            }
        });

        int gridRow = (tamanho - 1) - row;

        cell.getChildren().add(rect);

        if (robo != null && col == robo.getPosicaoX() && gridRow == robo.getPosicaoY()) {
            StackPane roboSprite = criarSpriteRobo(robo.getCor());
            cell.getChildren().add(roboSprite);

        }
        else if (col == alimentoX && gridRow == alimentoY) {
            StackPane bateriaSprite = criarSpriteBateria();
            cell.getChildren().add(bateriaSprite);
        }

        return cell;
    }


    private StackPane criarSpriteBateria() {
        StackPane bateriaContainer = new StackPane();

        Rectangle corpoBateria = new Rectangle(35, 50);
        corpoBateria.setFill(Color.web("#495057"));
        corpoBateria.setStroke(Color.web("#212529"));
        corpoBateria.setStrokeWidth(2);
        corpoBateria.setArcWidth(4);
        corpoBateria.setArcHeight(4);

        Rectangle terminalPositivo = new Rectangle(15, 8);
        terminalPositivo.setFill(Color.web("#6c757d"));
        terminalPositivo.setStroke(Color.web("#212529"));
        terminalPositivo.setStrokeWidth(1);
        terminalPositivo.setTranslateY(-29);

        Rectangle carga1 = new Rectangle(25, 4);
        carga1.setFill(Color.web("#ffffff"));
        carga1.setTranslateY(-10);

        Rectangle carga2 = new Rectangle(25, 4);
        carga2.setFill(Color.web("#e9ecef"));
        carga2.setTranslateY(-2);

        Rectangle carga3 = new Rectangle(25, 4);
        carga3.setFill(Color.web("#dee2e6"));
        carga3.setTranslateY(6);

        Rectangle simboloMais1 = new Rectangle(8, 2);
        simboloMais1.setFill(Color.web("#ffffff"));
        simboloMais1.setTranslateY(-18);

        Rectangle simboloMais2 = new Rectangle(2, 8);
        simboloMais2.setFill(Color.web("#ffffff"));
        simboloMais2.setTranslateY(-18);

        Rectangle simboloMenos = new Rectangle(8, 2);
        simboloMenos.setFill(Color.web("#ffffff"));
        simboloMenos.setTranslateY(15);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0), e -> {
                    carga1.setOpacity(1.0);
                    carga2.setOpacity(0.7);
                    carga3.setOpacity(0.4);
                }),
                new KeyFrame(Duration.seconds(0.5), e -> {
                    carga1.setOpacity(0.7);
                    carga2.setOpacity(1.0);
                    carga3.setOpacity(0.7);
                }),
                new KeyFrame(Duration.seconds(1.0), e -> {
                    carga1.setOpacity(0.4);
                    carga2.setOpacity(0.7);
                    carga3.setOpacity(1.0);
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        bateriaContainer.getChildren().addAll(
                corpoBateria,
                terminalPositivo,
                carga1, carga2, carga3,
                simboloMais1, simboloMais2, simboloMenos
        );

        return bateriaContainer;
    }

    private StackPane criarSpriteRobo(String corHex) {
        StackPane robo = new StackPane();
        Color roboCor = Color.web(corHex);

        Rectangle corpo = new Rectangle(50, 35);
        corpo.setFill(roboCor);
        corpo.setArcWidth(6);
        corpo.setArcHeight(6);

        Rectangle cabeca = new Rectangle(30, 10);
        cabeca.setFill(roboCor);
        cabeca.setTranslateY(-25);

        Rectangle bracoEsq = new Rectangle(8, 25);
        bracoEsq.setFill(roboCor);
        bracoEsq.setTranslateX(-30);

        Rectangle bracoDir = new Rectangle(8, 25);
        bracoDir.setFill(roboCor);
        bracoDir.setTranslateX(30);

        Rectangle pernaEsq = new Rectangle(8, 15);
        pernaEsq.setFill(roboCor);
        pernaEsq.setTranslateX(-10);
        pernaEsq.setTranslateY(25);

        Rectangle pernaDir = new Rectangle(8, 15);
        pernaDir.setFill(roboCor);
        pernaDir.setTranslateX(10);
        pernaDir.setTranslateY(25);

        Rectangle olhoEsq = new Rectangle(5, 5);
        olhoEsq.setFill(Color.web("#FFFFFF"));
        olhoEsq.setTranslateX(-8);
        olhoEsq.setTranslateY(-25);

        Rectangle olhoDir = new Rectangle(5, 5);
        olhoDir.setFill(Color.web("#FFFFFF"));
        olhoDir.setTranslateX(8);
        olhoDir.setTranslateY(-25);

        robo.getChildren().addAll(
                corpo,
                cabeca,
                bracoEsq, bracoDir,
                pernaEsq, pernaDir,
                olhoEsq, olhoDir
        );

        return robo;
    }



    private void atualizarLabels() {
        if (robo != null) {
            posicaoLabel.setText("POSIÇÃO: (" + robo.getPosicaoX() + ", " + robo.getPosicaoY() + ")");
        } else {
            posicaoLabel.setText("POSIÇÃO: --");
        }

        movimentosLabel.setText("MOVIMENTOS: " + movimentos);

        if (alimentoX >= 0 && alimentoY >= 0) {
            movimentosLabel.setText(movimentosLabel.getText() + " | BATERIA: (" + alimentoX + ", " + alimentoY + ")");
        }
    }

    public void moverUp() {
        mover("up");
    }

    public void moverDown() {
        mover("down");
    }

    public void moverLeft() {
        mover("left");
    }

    public void moverRight() {
        mover("right");
    }

    public void moverTexto() {
        String input = direcaoInput.getText().trim().toLowerCase();
        if (input.isEmpty()) {
            mostrarErro("Digite uma direção válida");
            return;
        }

        String[] comandos = input.split(",");
        for (String comando : comandos) {
            comando = comando.trim();
            try {
                int numero = Integer.parseInt(comando);
                moverPorNumero(numero);
            } catch (NumberFormatException e) {
                mover(comando);
            }

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        direcaoInput.clear();
    }

    private void moverPorNumero(int numero) {
        if (!jogoIniciado || jogoCompleto) {
            mostrarErro("Posicione a bateria antes de começar");
            return;
        }

        try {
            boolean sucesso = robo.mover(numero);
            if (sucesso) {
                movimentos++;
                statusLabel.setText("MOVIDO: direção " + numero);
                statusLabel.setTextFill(COR_SUCESSO);
                verificarSeEncontrou();
                desenharTabuleiro();
                atualizarLabels();
            }
        } catch (MovimentoInvalidoException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void mover(String direcao) {
        if (!jogoIniciado || jogoCompleto) {
            mostrarErro("Posicione a bateria antes de começar");
            return;
        }

        try {
            boolean sucesso = robo.mover(direcao);
            if (sucesso) {
                movimentos++;
                statusLabel.setText("MOVIDO: " + direcao.toUpperCase());
                statusLabel.setTextFill(COR_SUCESSO);
                verificarSeEncontrou();
                desenharTabuleiro();
                atualizarLabels();
            }
        } catch (MovimentoInvalidoException e) {
            mostrarErro(e.getMessage());
        }
    }

    private void verificarSeEncontrou() {
        if (robo.encontrarAlimento(alimentoX, alimentoY)) {
            jogoCompleto = true;
            statusLabel.setText("⚡ BATERIA COLETADA! Robô energizado em " + movimentos + " movimentos");
            statusLabel.setTextFill(COR_TEXTO_PRINCIPAL);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Missão Completa");
            alert.setHeaderText("ROBÔ ENERGIZADO");
            alert.setContentText("Bateria coletada com sucesso!\nMovimentos realizados: " + movimentos + "\n\nClique em RESETAR para nova missão.");
            alert.showAndWait();
        }
    }

    private void mostrarErro(String mensagem) {
        statusLabel.setText("ERRO: " + mensagem);
        statusLabel.setTextFill(COR_ERRO);
    }

    private String toHexString(Color color) {
        return String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}