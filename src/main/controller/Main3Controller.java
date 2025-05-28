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
import main.model.robos.RoboInteligente;

import java.util.Random;

public class Main3Controller {

    @FXML private GridPane gridPane;
    @FXML private Label statusLabel;
    @FXML private Label infoLabel;
    @FXML private ColorPicker colorPicker1;
    @FXML private ColorPicker colorPicker2;
    @FXML private Button iniciarButton;
    @FXML private Button resetButton;

    private final int tamanho = 4;
    private Robo roboNormal, roboInteligente;
    private int alimentoX = -1, alimentoY = -1;
    private boolean jogoIniciado = false;
    private boolean jogoCompleto = false;
    private boolean roboNormalAchou = false;
    private boolean roboInteligenteAchou = false;
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

        roboNormal = new Robo(toHexString(cor1));
        roboInteligente = new RoboInteligente(toHexString(cor2));

        statusLabel.setText("⚡ Clique no tabuleiro para posicionar a bateria");
        statusLabel.setTextFill(COR_TEXTO_PRINCIPAL);

        jogoIniciado = false;
        jogoCompleto = false;
        roboNormalAchou = false;
        roboInteligenteAchou = false;

        iniciarButton.setDisable(true);
        resetButton.setDisable(false);

        desenharTabuleiro();
        atualizarInfo();
    }

    public void resetarJogo() {
        if (timeline != null) {
            timeline.stop();
        }

        roboNormal = null;
        roboInteligente = null;
        alimentoX = -1;
        alimentoY = -1;
        jogoIniciado = false;
        jogoCompleto = false;
        roboNormalAchou = false;
        roboInteligenteAchou = false;

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
            if (!jogoIniciado && roboNormal != null && roboInteligente != null && !jogoCompleto) {
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

        // Verificar presença dos robôs e alimento
        boolean roboNormalPresente = roboNormal != null && col == roboNormal.getPosicaoX() && gridRow == roboNormal.getPosicaoY();
        boolean roboInteligentePresente = roboInteligente != null && col == roboInteligente.getPosicaoX() && gridRow == roboInteligente.getPosicaoY();
        boolean alimentoPresente = col == alimentoX && gridRow == alimentoY;

        if (roboNormalPresente && roboInteligentePresente) {
            HBox container = new HBox(5);
            container.setAlignment(Pos.CENTER);
            container.getChildren().addAll(criarSpriteRobo(roboNormal.getCor(), true), criarSpriteRobo(roboInteligente.getCor(), true));
            cell.getChildren().add(container);
        } else if (roboNormalPresente) {
            cell.getChildren().add(criarSpriteRobo(roboNormal.getCor(), false));
        } else if (roboInteligentePresente) {
            cell.getChildren().add(criarSpriteRobo(roboInteligente.getCor(), false));
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
                if (!roboNormalAchou) {
                    moverRoboNormal();
                }
                if (!roboInteligenteAchou) {
                    moverRoboInteligente();
                }
                desenharTabuleiro();
                atualizarInfo();

                if (roboNormalAchou && roboInteligenteAchou) {
                    jogoCompleto = true;
                    timeline.stop();
                    Platform.runLater(() -> mostrarResultadoFinal());
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void moverRoboNormal() {
        String direcao = direcoes[random.nextInt(4)];
        try {
            roboNormal.mover(direcao);
            statusLabel.setText("Robô Normal " + roboNormal.getCor() + " moveu para " + direcao);
            statusLabel.setTextFill(COR_TEXTO_PRINCIPAL);

            if (roboNormal.encontrarAlimento(alimentoX, alimentoY)) {
                roboNormalAchou = true;
            }
        } catch (MovimentoInvalidoException e) {
            statusLabel.setText("Robô Normal " + roboNormal.getCor() + " tentou movimento inválido: " + e.getMessage());
            statusLabel.setTextFill(COR_ERRO);
        }
    }
    
    private void moverRoboInteligente() {
        String direcao = direcoes[random.nextInt(4)];
        try {
            boolean moveu = roboInteligente.mover(direcao);
            
            if (moveu) {
                statusLabel.setText("Robô Inteligente " + roboInteligente.getCor() + " moveu para " + direcao);
                statusLabel.setTextFill(COR_TEXTO_PRINCIPAL);
                
                if (roboInteligente.encontrarAlimento(alimentoX, alimentoY)) {
                    roboInteligenteAchou = true;
                }
            } else {
                statusLabel.setText("Robô Inteligente " + roboInteligente.getCor() + " não conseguiu se mover a partir de " + direcao);
                statusLabel.setTextFill(COR_SUCESSO);
            }
        } catch (MovimentoInvalidoException e) {
            statusLabel.setText("Robô Inteligente " + roboInteligente.getCor() + ": " + e.getMessage());
            statusLabel.setTextFill(COR_ERRO);
        }
    }
    
    private void mostrarResultadoFinal() {
        String vencedor = roboNormalAchou && !roboInteligenteAchou ? roboNormal.getCor() :
                !roboNormalAchou && roboInteligenteAchou ? roboInteligente.getCor() :
                        "Empate (ambos encontraram)";

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fim do Jogo");
        alert.setHeaderText("Resultado Final");
        alert.setContentText(
                "Vencedor: " + vencedor + "\n\n" +
                        "Robô Normal " + roboNormal.getCor() + ":\n" +
                        "  Movimentos válidos: " + roboNormal.getMovimentosValidos() + "\n" +
                        "  Movimentos inválidos: " + roboNormal.getMovimentosInvalidos() + "\n\n" +
                        "Robô Inteligente " + roboInteligente.getCor() + ":\n" +
                        "  Movimentos válidos: " + roboInteligente.getMovimentosValidos() + "\n" +
                        "  Movimentos inválidos: " + roboInteligente.getMovimentosInvalidos()
        );
        alert.showAndWait();
    }

    private void atualizarInfo() {
        if (roboNormal != null && roboInteligente != null) {
            String info = String.format(
                    "Robô Normal %s: (%d,%d) | Movimentos validos: %d | Movimentos Invalidos: %d\n" +
                            "Robô Inteligente %s: (%d,%d) | Movimentos validos:: %d | Movimentos Invalidos: %d",
                    roboNormal.getCor(), roboNormal.getPosicaoX(), roboNormal.getPosicaoY(),
                    roboNormal.getMovimentosValidos(), roboNormal.getMovimentosInvalidos(),
                    roboInteligente.getCor(), roboInteligente.getPosicaoX(), roboInteligente.getPosicaoY(),
                    roboInteligente.getMovimentosValidos(), roboInteligente.getMovimentosInvalidos()
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