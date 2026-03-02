
package juegos;
import processing.core.PApplet;
import processing.core.PImage;
public class Juegos extends PApplet {
    PImage birdImg;
    
    int score = 0; //Puntuaje
    boolean passedPipe = false; //No contar reiteradas veces el mismo tubo
    
    //Fisicas del pájaro
    float birdX = 150;
    float birdY = 300;
    float birdSpeed = 0;
    float gravity = 0.5f;
    float jumpForce = -8;
    
    //Tubos
    float pipeX = 1920;
    float pipeWidth = 100;
    float pipeSpeed = 6;
    float gapSize = 130;
    float gapY;
    
    //Declaracion de la derrota en caso de TRUE
    boolean gameOver = false;
    
    public static void main(String[] args) {
        PApplet.main("juegos.Juegos");
    }

    @Override
    public void settings() {
        size(800, 500);
    }

    @Override
    public void setup() {
        textAlign(CENTER);
        textSize(32);
        generarHueco();
        birdImg = loadImage("juegos\\FlappyBird.png");

    }

    @Override
    public void draw() {
        pintarFondo();
        if(!gameOver){
            aplicarGravedad();
            moverPajaro();
            moverTubos();
        }
        pintarTubos();
        pintarPajaro();
        comprobarSuelo();
        mostrarMensajes();
        comprobarColisionesTubos();
        comprobarPuntuacion();
        fill(255);
        text("Puntos: " + score, 100, 50);
    }
    
    void aplicarGravedad(){
        birdSpeed += gravity;
    }
    
    void pintarFondo(){
        background(113,197,207);
        
    }
    
 
    void moverPajaro(){
       birdY += birdSpeed;
    }
    
    void comprobarSuelo(){
        if (birdY > height || birdY<0){
            gameOver = true;
        }
    }
    
    void mostrarMensajes(){
        if(gameOver){
            fill(0);
            text("GAME OVER", width / 2, height / 2);
        }
    }
    
    void pintarPajaro(){
        if(birdImg != null){
            image(birdImg, birdX - 20, birdY - 20, 40, 40);
        }else{
            fill(255,200,0);
            ellipse(birdX,birdY,40,40);
        }
    }
    
    void generarHueco(){
        gapY = random(200, height - 200);
    }
    
    void moverTubos(){
        pipeX -= pipeSpeed;
        
        if (pipeX + pipeWidth < 0) {
            pipeX = width;
            generarHueco();
            passedPipe = false;
        }
    }
    
    void pintarTubos(){
        fill(0,200,0);
        
        rect(pipeX, 0 , pipeWidth, gapY - gapSize / 2); //Tubo superior
        
        rect(pipeX, gapY + gapSize / 2, pipeWidth,
             height - (gapY + gapSize/2)); //Tubo inferior
    }
    
    void comprobarColisionesTubos(){
        float birdLeft = birdX - 20;
        float birdRight = birdX + 20;
        float birdTop = birdY - 20;
        float birdBottom = birdY + 20;
        
        float pipeTop = 0;
        float pipeBottom = gapY - gapSize / 2;
        float pipeLeft = pipeX;
        float pipeRight = pipeX + pipeWidth;
        //Colision con el tubo superior
        if (birdRight > pipeLeft && birdLeft < pipeRight && birdTop < pipeBottom) {
            gameOver = true;  
            
        }
        
        pipeTop = gapY + gapSize / 2;
        pipeBottom = height;
        //Colision con el tubo inferior
        if (birdRight > pipeLeft && birdLeft < pipeRight && birdBottom > pipeTop) {
            gameOver = true;
        }
        
    }
    
    void comprobarPuntuacion(){
        //Si el pajaro pasa el tubo obiene un punto
        if (!passedPipe && birdX > pipeX + pipeWidth / 2) {
            score++;
            passedPipe = true;
        }
        
        //El tubo se reinicia y reiniciamos passedPipe a false para poder obtener puntos de nuevo
        if (pipeX + pipeWidth < 0) {
            pipeX = width;
            generarHueco();
            passedPipe = false;
        }
    }
    
    void reiniciarJuego(){
        birdX = 150;
        birdY = 300;
        birdSpeed = 0;
        pipeX = width;
        generarHueco();
        gameOver = false;
        score = 0;
        passedPipe = false;
    }
    
    @Override
    public void keyPressed(){
        if(!gameOver){
            birdSpeed = jumpForce;
        }else if (key == 'r' || key == 'R'){
            reiniciarJuego();
        }
    }
}


