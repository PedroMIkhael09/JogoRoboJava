# JogoRoboJava

# 🤖 Jogo Robô vs Obstáculos

Projeto em Java com JavaFX onde robôs se movimentam em um tabuleiro 4x4 para encontrar um alimento, desviando de áreas inválidas (coordenadas negativas) e obstáculos.

---

##  Modos de Jogo

- **Main1**: Controle manual de um robô. O usuário define a posição do alimento e comanda os movimentos.
- **Main2**: Dois robôs normais se movimentam aleatoriamente até que um encontre o alimento.
- **Main3**: Um robô normal e um robô inteligente se movem aleatoriamente até ambos encontrarem o alimento.
- **Main4**: Um robô normal e um robô inteligente enfrentam **bombas** (explodem) e **rochas** (impedem o movimento). O jogo termina quando um encontra o alimento ou ambos explodem.

---

## Requisitos

- **Java 17+**
- **JavaFX SDK** (https://openjfx.io/)

---

##  Como Executar

1. **Baixe o JavaFX SDK**:
   - [https://openjfx.io](https://openjfx.io)

2. **Configure o JavaFX no seu projeto**:
   - No IntelliJ IDEA:  
     Vá em `File > Project Structure > Libraries` e adicione a pasta `lib` do JavaFX.

3. **Execute o projeto**:
   - Navegue até `src/main/view`.
   - Escolha uma das classes `Main1`, `Main2`, `Main3` ou `Main4`.
   - Clique com o botão direito e selecione **Run**.

---

## Estrutura do Projeto

```
JogoRoboJava/
├── src/
│   ├── main/
│   │   ├── controller/         # Controladores JavaFX
│   │   ├── exception/          # Exceção MovimentoInvalidoException
│   │   ├── model/              # Classes Robo, RoboInteligente, Obstaculos etc.
│   │   ├── resources/          # Arquivos .fxml
│   │   └── view/               # Classes Main1, Main2, Main3, Main4
├── README.md
└── ...
```

---


## Funcionalidades

- Robôs se movimentam no plano cartesiano (x, y ≥ 0)
- Exceções para movimentos inválidos
- Movimentação manual ou aleatória
- Robô inteligente evita repetir movimentos inválidos
- Obstáculos:
  - **Bomba**: destrói o robô
  - **Rocha**: bloqueia e faz o robô voltar à posição anterior
