package src;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Font;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    // menetukan panjang ular
    private int[] snakexLength = new int[750];
    private int[] snakeyLength = new int[750];

    // arah ular
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    // buat gambar kepala
    private ImageIcon snakeHead;

    private int lengthOfSnake = 5;
    private int moves = 0;

    private Timer timer;
    private int delay = 200;
    private ImageIcon snakeBody;

    // Untuk posisi apple yang akan muncul secara random
    private int[] applexPos = { 25, 35, 45, 55, 65, 75, 85, 95, 105, 115, 125, 135, 145, 155, 165, 175, 185, 195, 205,
            215, 225, 235, 245, 255, 265, 275, 285, 295, 305, 315, 325, 335, 345, 355, 365, 375, 385, 395, 405, 415,
            425, 435, 445, 455, 465, 475, 485, 495, 505, 515, 525, 535, 545, 555, 565, 575, 585, 595, 605, 615, 625,
            635, 645, 655, 665, 675, 685, 695, 705, 715, 725, 735, 745, 755, 765, 775, 785, 795, 805, 815, 825, 835,
            845, 855 };
    private int[] appleyPos = { 75, 85, 95, 105, 115, 125, 135, 145, 155, 165, 175, 185, 195, 205, 215, 225, 235, 245,
            255, 265, 275, 285, 295, 305, 315, 325, 335, 345, 355, 365, 375, 385, 395, 405, 415, 425, 435, 445, 455,
            465, 475, 485, 495, 505, 515, 525, 535, 545, 555, 565, 575, 585, 595, 605, 615, 625 };

    // Buat gambar apple
    private ImageIcon appleImage;

    // Untuk generate random number
    private Random random = new Random();

    private int xPos = random.nextInt(84);
    private int yPos = random.nextInt(56);

    // Buat tittle game
    private ImageIcon titleImage;

    // Buat score game
    Score score = new Score();

    boolean death = false;

    public Gameplay() {

        // buat pas mulai gamenya
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        // cek jika game udah dimulai
        if (moves == 0) {
            // snakexLength[4] = 350;
            // snakexLength[3] = 375;
            // snakexLength[2] = 400;
            // snakexLength[1] = 425;
            // snakexLength[0] = 450;

            // snakeyLength[4] = 350;
            // snakeyLength[3] = 350;
            // snakeyLength[2] = 350;
            // snakeyLength[1] = 350;
            // snakeyLength[0] = 350;
            snakexLength[4] = 355;
            snakexLength[3] = 365;
            snakexLength[2] = 375;
            snakexLength[1] = 385;
            snakexLength[0] = 395;

            snakeyLength[4] = 355;
            snakeyLength[3] = 355;
            snakeyLength[2] = 355;
            snakeyLength[1] = 355;
            snakeyLength[0] = 355;
        }

        // border judul
        g.setColor(Color.WHITE);
        g.drawRect(24, 10, 852, 55);

        // judul
        titleImage = new ImageIcon("images/title.png");
        titleImage.paintIcon(this, g, 25, 11);

        // border untuk gameplay
        g.setColor(Color.WHITE);
        g.drawRect(24, 74, 851, 571);

        // background gameplay
        g.setColor(Color.black);
        g.fillRect(25, 75, 850, 570);

        // border untuk leaderboard
        g.setColor(Color.WHITE);
        g.drawRect(894, 9, 292, 641);

        // background leaderboard
        g.setColor(Color.black);
        g.fillRect(895, 10, 290, 640);

        // Tampilin Score
        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.PLAIN, 16));
        g.drawString("Scores : " + score.getScore(), 760, 42);

        // instansiasi gambar buat kepala ular
        snakeHead = new ImageIcon("images/snakeHead4.png");
        snakeHead.paintIcon(this, g, snakexLength[0], snakeyLength[0]);

        for (int i = 0; i < lengthOfSnake; i++) {
            if (i == 0 && (right || left || up || down)) {
                snakeHead = new ImageIcon("images/snakeHead4.png");
                snakeHead.paintIcon(this, g, snakexLength[i], snakeyLength[i]);
            }
            if (i != 0) {
                snakeBody = new ImageIcon("images/snakeimage4.png");
                snakeBody.paintIcon(this, g, snakexLength[i], snakeyLength[i]);
            }
        }

        appleImage = new ImageIcon("images/apple4.png");

        // Jika snakeya makan apllenya
        if ((applexPos[xPos]) == snakexLength[0] && (appleyPos[yPos] == snakeyLength[0])) {
            lengthOfSnake++;
            score.increaseScore();
            xPos = random.nextInt(84);
            yPos = random.nextInt(57);
        }

        // Sebelum user mencet spacebar, apllenya ga keliatan
        if (moves != 0) {
            appleImage.paintIcon(this, g, applexPos[xPos], appleyPos[yPos]);
        }

        // menampilkan tulisan "Press Spacebar to Start the Game!"
        if (moves == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 30));
            g.drawString("Press Spacebar to Start the Game!", 150, 300);
        }

        // Cek jika kepala menabrak badan
        for (int i = 1; i < lengthOfSnake; i++) {
            // jika tabrakan terjadi
            if (snakexLength[i] == snakexLength[0] && snakeyLength[i] == snakeyLength[0]) {
                // panggil function dead
                dead();
            }
        }

        // Cek jika mati
        if (death) {
            // menampilkan tulisan "Game Over!"
            g.setColor(Color.RED);
            g.setFont(new Font("Courier New", Font.BOLD, 50));
            g.drawString("Game Over!", 300, 300);

            // menampilkan tulisan "Press Spacebar to restart!"
            g.setColor(Color.WHITE);
            g.setFont(new Font("Courier New", Font.BOLD, 20));
            g.drawString("Press Spacebar to restart!", 300, 340);
        }

        g.dispose();
    }

    // function mati biar ga ngulang nulis kode berkali-kali
    public void dead() {
        // membuat ular tidak bisa bergerak
        right = false;
        left = false;
        up = false;
        down = false;
        death = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        timer.start();

        // untuk pergerakan ular
        // menggerakkan ular ke kanan
        if (right) {
            // pindahkan posisi head ke index selanjutnya
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                // pindahkan posisi snakeyLength
                snakeyLength[i + 1] = snakeyLength[i];
            }
            //
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                // pindahkan posisi snakexLength
                if (i == 0) {
                    snakexLength[i] = snakexLength[i] + 10;
                } else {
                    snakexLength[i] = snakexLength[i - 1];
                }
                // jika sudah lewat ujung kanan
                if (snakexLength[0] > 865) {
                    // maot
                    dead();
                }
            }
            // panggil kembali method paint secara otomatis
            repaint();
        }
        // menggerakkan ular ke kiri
        if (left) {
            // pindahkan posisi head ke index selanjutnya
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                // pindahkan posisi snakeyLength
                snakeyLength[i + 1] = snakeyLength[i];
            }
            //
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                // pindahkan posisi snakexLength
                if (i == 0) {
                    snakexLength[i] = snakexLength[i] - 10;
                } else {
                    snakexLength[i] = snakexLength[i - 1];
                }
                // jika sudah lewat ujung kiri
                if (snakexLength[0] < 25) {
                    // maot
                    dead();
                }
            }
            // panggil kembali method paint secara otomatis
            repaint();
        }
        // menggerakkan ular ke atas
        if (up) {
            // pindahkan posisi head ke index selanjutnya
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                // pindahkan posisi snakexLength
                snakexLength[i + 1] = snakexLength[i];
            }
            //
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                // pindahkan posisi snakeyLength
                if (i == 0) {
                    snakeyLength[i] = snakeyLength[i] - 10;
                } else {
                    snakeyLength[i] = snakeyLength[i - 1];
                }
                // jika sudah lewat ujung atas
                if (snakeyLength[0] < 75) {
                    // maot
                    dead();
                }
            }
            // panggil kembali method paint secara otomatis
            repaint();
        }
        // menggerakkan ular ke bawah
        if (down) {
            // pindahkan posisi head ke index selanjutnya
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                // pindahkan posisi snakexLength
                snakexLength[i + 1] = snakexLength[i];
            }
            //
            for (int i = lengthOfSnake - 1; i >= 0; i--) {
                // pindahkan posisi snakeyLength
                if (i == 0) {
                    snakeyLength[i] = snakeyLength[i] + 10;
                } else {
                    snakeyLength[i] = snakeyLength[i - 1];
                }
                // jika sudah lewat ujung bawah
                if (snakeyLength[0] > 635) {
                    // maot
                    dead();
                }
            }
            // panggil kembali method paint secara otomatis
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Jika user pencet spacebar
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            // Untuk mulai game
            if (moves == 0) {
                moves++;
                right = true;
            }
            // Untuk restart game abis mati
            if (death) {
                moves = 0;
                lengthOfSnake = 5;
                score.resetScore();
                repaint();
                death = false;
            }
        }

        // jika user pencet right arrow
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (moves != 0 && !death) {
                moves++;
                if (!left) {
                    right = true;
                } else {
                    right = false;
                    left = true;
                }
                up = false;
                down = false;
            }
        }
        // jika user pencet left arrow
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (moves != 0 && !death) {
                moves++;
                if (!right) {
                    left = true;
                } else {
                    left = false;
                    right = true;
                }
                up = false;
                down = false;
            }
        }
        // jika user pencet up arrow
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (moves != 0 && !death) {
                moves++;
                if (!down) {
                    up = true;
                } else {
                    up = false;
                    down = true;
                }
                left = false;
                right = false;
            }
        }
        // jika user pencet down arrow
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (moves != 0 && !death) {
                moves++;
                if (!up) {
                    down = true;
                } else {
                    down = false;
                    up = true;
                }
                left = false;
                right = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
