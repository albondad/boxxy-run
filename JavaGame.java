import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
import javax.swing.*;

public class JavaGame extends Applet implements ActionListener, KeyListener, MouseListener, MouseMotionListener
{   
    //buffering
    Graphics buffer;
    Image bufferImage;
    
    //main stuff
    private Timer timer;
    private int tries;
    private int level;
    private int width, height;


    //LEVEL 0

    //images
    private Image boxxyRunImage, startImage, instructionsImage, instructionsPageImage, quitImage;

    //boxxyRunImage properties
    private int boxxyRunImageXL;
    private int boxxyRunImageYL;
    private int boxxyRunImageXS;
    private int boxxyRunImageYS;

    //startImage properties
    private int startImageXL;
    private int startImageYL;
    private int startImageXS;
    private int startImageYS;
    private int startImageFS;

    //instructionsImage/page properties
    private int instructionsImageXL;
    private int instructionsImageYL;
    private int instructionsImageXS;
    private int instructionsImageYS;
    private int instructionsImageFS;
    private boolean showInstructions;

    //quitImage location
    private int quitImageXL;
    private int quitImageYL;
    private int quitImageXS;
    private int quitImageYS;
    private int quitImageFS;

    //extra startup variables
    private int scaleByX, scaleByY;
    private int shrinkGrow;
    private int timerCtr;

    //center of screen
    private int centerX, centerY;

    //LEVEL 1
    private int keyCode;
    private int keyPressed;
    private boolean rAPressed;
    private boolean lAPressed;
    private boolean uAPressed;
    private boolean showStats;
    
    //images
    private Image sprite;
    private Image labTiles, labWindow, labSpikes, backMist;

    //sprite properties
    private int spriteXL;
    private int spriteYL;
    private int spriteYC;
    private int spriteXS;
    private int spriteYS;
    private int spriteBottom;
    private boolean spriteOnGround;
    private boolean spriteObjectOnLeft;
    private boolean spriteObjectOnRight;
    
    //xScrolling
    private int aXChange; // actual xChange relative to screen size
    private int xChange; // xChange not relative to screen size
    /**
     *This is always called before the first 
     * time that the start method is called.
     */
    public void init()
    {   
        //buffering
        bufferImage = createImage(500, 500);
        buffer = bufferImage.getGraphics();
        
        //LEVEL 0
        //sets up timer
        timer = new Timer(10, this);
        timer.start();
        addMouseListener(this);
        addMouseMotionListener(this);

        //imports images
        boxxyRunImage = getImage(getDocumentBase(), "Java Game Assets/title_boxxyRun.png");
        startImage = getImage(getDocumentBase(), "Java Game Assets/title_start.png");
        instructionsImage = getImage(getDocumentBase(), "Java Game Assets/title_instructions.png");
        quitImage = getImage(getDocumentBase(), "Java Game Assets/title_quit.png");
        instructionsPageImage = getImage(getDocumentBase(), "Java Game Assets/instructions_page.png");

        //boxxyRunImage start properties
        boxxyRunImageXL = 0;
        boxxyRunImageYL = 0;

        //startImage start properties
        startImageXL = 0;
        startImageYL = getHeight()/3;
        startImageXS = getWidth();
        startImageYS = getHeight()/4;
        startImageFS = 5;
        instructionsImageFS = 0;
        
        //quitImage start properties
        quitImageXL = 0;
        quitImageYL = getHeight()/3 * 2;
        quitImageXS = getWidth();
        quitImageYS = getHeight()/4;
        quitImageFS = 1;

        //screen data
        centerX = getWidth();
        centerY = getHeight();

        //extra variables
        shrinkGrow = 1;

        //LEVEL 1
        tries = 0;
        addKeyListener(this);
        requestFocus();
        level = 0;

        //images
        sprite = getImage(getDocumentBase(), "Java Game Assets/main_character.png");
        labTiles = getImage(getDocumentBase(), "Java Game Assets/lab_metal_tile.png");
        backMist = getImage(getDocumentBase(), "Java Game Assets/back_mist.png");
        labSpikes = getImage(getDocumentBase(), "Java Game Assets/lab_spikes.png");
    }
    
    public void actionPerformed(ActionEvent ae) {
      repaint();
        
    }

    //KEY EVENTS
    public void keyReleased(KeyEvent evt) {
        keyCode = evt.getKeyCode();

        if (keyCode == KeyEvent.VK_UP && keyCode != KeyEvent.VK_RIGHT && keyCode != KeyEvent.VK_LEFT) {
            uAPressed = false;
        }
        else if (keyCode == KeyEvent.VK_RIGHT && keyCode != KeyEvent.VK_UP) {
            rAPressed = false;
        }
        else if (keyCode == KeyEvent.VK_LEFT && keyCode != KeyEvent.VK_UP) {
            lAPressed = false;
        }
        else if (keyCode == KeyEvent.VK_1) {
            showStats = false;
        }
    }

    public void keyPressed(KeyEvent evt) {
        keyCode = evt.getKeyCode();

        if (keyCode == KeyEvent.VK_UP) {
            uAPressed = true;
        }
        else if (keyCode == KeyEvent.VK_RIGHT) {
            rAPressed = true;
        }
        else if (keyCode == KeyEvent.VK_LEFT) {
            lAPressed = true;
        }
        else if (keyCode == KeyEvent.VK_1) {
            showStats = true;
        }
        else if (keyCode == KeyEvent.VK_ESCAPE) {
            showInstructions = false;
        }
    }

    public void keyTyped(KeyEvent evt) {

    }

    //MOUSE EVENTS
    public void mouseExited(MouseEvent me) {

    }

    public void mouseEntered(MouseEvent me) {

    }

    public void mouseReleased(MouseEvent me) {

    }

    public void mousePressed(MouseEvent me) {

    }

    public void mouseClicked(MouseEvent me) {
        if (me.getX() > startImageXL && me.getX() < startImageXL + startImageXS && me.getY() > startImageXL && me.getY() < startImageYL + startImageYS) {
            level = 1;
        }
        if (me.getX() > quitImageXL && me.getX() < quitImageXL + quitImageXS && me.getY() > quitImageYL && me.getY() < quitImageYL + quitImageYS) {
            level = 2;
        }
        if (me.getX() > instructionsImageXL && me.getX() < instructionsImageXL + instructionsImageXS && me.getY() > instructionsImageYL && me.getY() < instructionsImageYL + instructionsImageYS) {
            showInstructions = true;
        }
    }
    
    public void mouseMoved(MouseEvent me) {

    }

    public void mouseDragged(MouseEvent me) {

    }
    
    
    //PAINTINg
    /**
     * Paint method for applet.
     * 
     * @param  g   the Graphics object for this applet
     */
    public void paint(Graphics g)
    {   
        if (level != 2) {
            timerCtr += 1;
        }
        width = getWidth();
        height = getHeight();
        centerX = getWidth()/2;
        centerY = getHeight()/2;
        bufferImage = createImage(width, height);
        buffer = bufferImage.getGraphics();
        buffer.clearRect(0,0,width,height);
        if (level == 0) {
            //pre-painting calculations
            centerX = getWidth()/2;
            centerY = getHeight()/2;

            if (startImageFS == 5) {
                shrinkGrow = 2;
            }
            else if (instructionsImageFS == 5) {
                shrinkGrow = 1;
            }
            if (shrinkGrow == 1 && timerCtr%20 == 0) {
                startImageFS += 1;
                instructionsImageFS -= 1;
                quitImageFS += 1;
            }
            else if (shrinkGrow == 2 && timerCtr%20 == 0) {
                startImageFS -= 1;
                instructionsImageFS += 1;
                quitImageFS -= 1;
            }

            startImageXS = getWidth()/2 - (getWidth()/50 * startImageFS);
            startImageYS = (getWidth()/2 - (getWidth()/50 * startImageFS))/4;
            startImageYL = getHeight()/5 * 2 - startImageYS / 2;
            startImageXL = centerX - startImageXS/2;

            instructionsImageXS = getWidth()/2 - (getWidth()/50 * instructionsImageFS);
            instructionsImageYS = (getWidth()/2 - (getWidth()/50 * instructionsImageFS))/4;
            instructionsImageYL = getHeight()/5 * 3 - instructionsImageYS / 2;
            instructionsImageXL = centerX - instructionsImageXS/2;

            quitImageXS = getWidth()/2 - (getWidth()/50 * quitImageFS);
            quitImageYS = (getWidth()/2 - (getWidth()/50 * quitImageFS))/4;
            quitImageYL = getHeight()/5 * 4 - quitImageYS / 2;
            quitImageXL = centerX - quitImageXS/2;

            //painting
            buffer.drawImage(boxxyRunImage, boxxyRunImageXL, 0, getWidth(), getHeight(), this);
            buffer.drawImage(startImage, startImageXL, startImageYL, startImageXS, startImageYS, this);
            buffer.drawImage(instructionsImage, instructionsImageXL, instructionsImageYL, instructionsImageXS, instructionsImageYS, this);
            buffer.drawImage(quitImage, quitImageXL, quitImageYL, quitImageXS, quitImageYS, this);
            
            if (showInstructions == true) {
                buffer.drawImage(instructionsPageImage, 0, 0, width, height, this); // sprite
            }
            
            spriteXL = centerX - spriteXS/2;
            spriteYL = centerY;
            aXChange = 0;
            spriteYC = 0;
            xChange = -45;
        }
        else if (level == 1) {
            //pre-painting calculations
            spriteBottom = spriteYL + spriteYS; // defines spriteBottom
            
            if (rAPressed == true) {
                xChange += 1;
            } // moving left
            if (lAPressed == true) {
                xChange -= 1;
            } // moving right
            if (uAPressed == true && spriteOnGround == true) {
                spriteYL -= height/100;
                spriteYC += (-height/100) * 1.5;
            } // jumping1
            
            //area 1
            if (spriteYL < height/10*9) {
                spriteOnGround = false;
            }
            if (spriteYL >= height/10*8 && aXChange <= width/2 && aXChange > -(width/2)) {
                spriteYL = height/10*8;
                spriteOnGround = true;
            }
            if (spriteYL >= height/10*6 && xChange > 95 && xChange < 115) {
                level = 0;
                tries += 1;
                timerCtr = 0;
            }
            
            //area 2
            if (spriteYL < height/10*7) {
                spriteOnGround = false;
            }
            if (xChange == 46 && spriteYL > height/10*7) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*7 && xChange > 46 && xChange < 166) {
                spriteYL = height/10*7;
                spriteOnGround = true;
            }
            
            //area 3
            if (spriteYL < height/10*7) {
                spriteOnGround = false;
            }
            if (xChange == 155 && spriteYL > height/10*7) {
                spriteObjectOnLeft = true;
            }
            if (spriteYL >= height/10*7 && xChange > 155 && xChange < 165) {
                spriteYL = height/10*7;
                spriteOnGround = true;
            }//pillar 1
            if (xChange == 206 && spriteYL >= height/10*7) {
                spriteObjectOnLeft = true;
            }
            if (xChange == 184 && spriteYL >= height/10*7) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*7 && xChange > 185 && xChange < 205) {
                spriteYL = height/10*7;
                spriteOnGround = true;
            }//pillar 2
            if (xChange == 244 && spriteYL > height/10*7) {
                spriteObjectOnLeft = true;
            }
            if (xChange == 226 && spriteYL > height/10*7) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*7 && xChange > 225 && xChange < 245) {
                spriteYL = height/10*7;
                spriteOnGround = true;
            }//pillar 3
            
            //area 4
            if (spriteYL < height/10*7) {
                spriteOnGround = false;
            }
            if (xChange == 246 && spriteYL > height/10*7) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*7 && xChange > 245 && xChange < 275) {
                spriteYL = height/10*7;
                spriteOnGround = true;
            }
            if (xChange == 266 && spriteYL >= height/10*6) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*6 && xChange > 266 && xChange < 285) {
                spriteYL = height/10*6;
                spriteOnGround = true;
            }
            if (xChange == 276 && spriteYL >= height/10*5) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*5 && xChange > 276 && xChange < 295) {
                spriteYL = height/10*5;
                spriteOnGround = true;
            }
            if (xChange == 286 && spriteYL >= height/10*4) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*4 && xChange > 286 && xChange < 305) {
                spriteYL = height/10*4;
                spriteOnGround = true;
            }
            if (xChange == 296 && spriteYL >= height/10*3) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*3 && xChange > 296 && xChange < 315) {
                spriteYL = height/10*3;
                spriteOnGround = true;
            }
            if (xChange == 306 && spriteYL >= height/10*2) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*2 && xChange > 306 && xChange < 325) {
                spriteYL = height/10*2;
                spriteOnGround = true;
            }
            if (xChange == 316 && spriteYL >= height/10*1) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10 && xChange > 316 && xChange < 355) {
                spriteYL = height/10;
                spriteOnGround = true;
            }
            
            //area 5
            if (xChange == 396 && spriteYL > height/10*7) {
                spriteObjectOnRight = true;
            }
            if (xChange == 396 && spriteYL < height/10*5) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*7 && xChange > 396 && xChange < 470) {
                spriteYL = height/10*7;
                spriteOnGround = true;
            }
            
            //area 6
            if (xChange == 554 && spriteYL > height/10*5) {
                spriteObjectOnLeft = true;
            }
            if (xChange == 446 && spriteYL > height/10*5) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*5 && xChange > 446 && xChange < 554) {
                spriteYL = height/10*5;
                spriteOnGround = true;
            }
            
            //area 7
            if (xChange == 556 && spriteYL > height/10*7) {
                spriteObjectOnRight = true;
            }
            if (spriteYL >= height/10*7 && xChange > 554 && xChange < 654) {
                spriteYL = height/10*7;
                spriteOnGround = true;
            }
            if (spriteYL >= height/10*6 && xChange > 555 && xChange < 565) {
                level = 0;
                tries += 1;
                timerCtr = 0;
            }
            if (spriteYL >= height/10*6 && xChange > 575 && xChange < 595) {
                level = 0;
                tries += 1;
                timerCtr = 0;
            }
            if (spriteYL >= height/10*6 && xChange > 605 && xChange < 625) {
                level = 0;
                tries += 1;
                timerCtr = 0;
            }
            if (spriteYL >= height/10*6 && xChange > 635 && xChange < 654) {
                level = 0;
                tries += 1;
                timerCtr = 0;
            }
            
            //aera 8
            if (spriteYL >= height/10*8 && xChange > 650 && xChange < 755) {
                spriteYL = height/10*8;
                spriteOnGround = true;
            }
            if (spriteYL >= height/10*8 && xChange == 654) {
                spriteObjectOnLeft = true;
            }
             if (xChange >= 745) {
                level = 2;
            }


            
            //ceillling from areas 5 - 7
            if (spriteYL < height/10*4 && xChange > 396 && xChange < 655) {
                spriteYL = height/10*4;
                spriteYC += height/500;
            }
            
            //collision detection
            if (spriteOnGround == true) {
                spriteYC = (spriteYC * -1)/4*3;
            }
            if (spriteOnGround == false) {
                spriteYC += height/500;
            }
            if (spriteObjectOnRight == true) {
                xChange -= 1;
                spriteObjectOnRight = false;
            }
            if (spriteObjectOnLeft == true) {
                xChange += 1;
                spriteObjectOnLeft = false;
            }
            if (spriteYL > height) {
                level = 0;
                tries += 1;
                timerCtr = 0;
            }
            
            
            spriteXS = getWidth()/10;
            spriteYS = getHeight()/10;
            spriteXL = centerX - spriteXS/2;
            spriteYL = spriteYL + spriteYC;
            aXChange = width/100 * - xChange;
            
            
            //painting
            buffer.drawImage(labSpikes, width + width/2 + aXChange, height/10*7, width/10, height/10, this);
            for (int area01XL = 0; area01XL < width/10 * 10; area01XL += width/10) {
                buffer.drawImage(labTiles, area01XL + aXChange, height/10*9, width/10, height/10, this);
            } // area 01
            for (int area02XL = width; area02XL < (width * 2)/10 * 10; area02XL += width/10) {
                buffer.drawImage(labTiles, area02XL + aXChange, height/10*9, width/10, height/10, this);
                buffer.drawImage(labTiles, area02XL + aXChange, height/10*8, width/10, height/10, this);
            } // area 02
            for (int area03XL = width * 2; area03XL < (width * 3)/10 * 10; area03XL += (width/10)*4) {
                buffer.drawImage(labTiles, area03XL + aXChange, height/10*9, width/10, height/10, this);
                buffer.drawImage(labTiles, area03XL + aXChange, height/10*8, width/10, height/10, this);
            } // area 03
            for (int area04XL = width * 3; area04XL < (width * 4)/10  * 10; area04XL += width/10) {
                buffer.drawImage(labTiles, area04XL + aXChange, height/10*9, width/10, height/10, this);
                buffer.drawImage(labTiles, area04XL + aXChange, height/10*8, width/10, height/10, this);
                if (area04XL >= width * 3 + width/10 * 2) {
                    buffer.drawImage(labTiles, area04XL + aXChange, height/10*7, width/10, height/10, this);
                }
                if (area04XL >= width * 3 + width/10 * 3) {
                    buffer.drawImage(labTiles, area04XL + aXChange, height/10*6, width/10, height/10, this);
                }
                if (area04XL >= width * 3 + width/10 * 4) {
                    buffer.drawImage(labTiles, area04XL + aXChange, height/10*5, width/10, height/10, this);
                }
                if (area04XL >= width * 3 + width/10 * 5) {
                    buffer.drawImage(labTiles, area04XL + aXChange, height/10*4, width/10, height/10, this);
                }
                if (area04XL >= width * 3 + width/10 * 6) {
                    buffer.drawImage(labTiles, area04XL + aXChange, height/10*3, width/10, height/10, this);
                }
                if (area04XL >= width * 3 + width/10 * 7) {
                    buffer.drawImage(labTiles, area04XL + aXChange, height/10*2, width/10, height/10, this);
                }
            } // area 04
            for (int area05XL = width * 4 + width/2; area05XL < (width * 5)/10 * 10;  area05XL += (width/10)) {
                buffer.drawImage(labTiles, area05XL + aXChange, height/10*9, width/10, height/10, this);
                buffer.drawImage(labTiles, area05XL + aXChange, height/10*8, width/10, height/10, this);
                buffer.drawImage(labTiles, area05XL + aXChange, height/10*0, width/10, height/10, this);
                buffer.drawImage(labTiles, area05XL + aXChange, height/10*1, width/10, height/10, this);
                buffer.drawImage(labTiles, area05XL + aXChange, height/10*2, width/10, height/10, this);
                buffer.drawImage(labTiles, area05XL + aXChange, height/10*3, width/10, height/10, this);
            } // area 05
            for (int area06XL = width * 5; area06XL < (width * 6)/10 * 10;   area06XL += (width/10)) {
                buffer.drawImage(labTiles, area06XL + aXChange, height/10*9, width/10, height/10, this);
                buffer.drawImage(labTiles, area06XL + aXChange, height/10*8, width/10, height/10, this);
                buffer.drawImage(labTiles, area06XL + aXChange, height/10*7, width/10, height/10, this);
                buffer.drawImage(labTiles, area06XL + aXChange, height/10*6, width/10, height/10, this);
                
                buffer.drawImage(labTiles, area06XL + aXChange, height/10*0, width/10, height/10, this);
                buffer.drawImage(labTiles, area06XL + aXChange, height/10*1, width/10, height/10, this);
                buffer.drawImage(labTiles, area06XL + aXChange, height/10*2, width/10, height/10, this);
                buffer.drawImage(labTiles, area06XL + aXChange, height/10*3, width/10, height/10, this);
            } // area 06
            buffer.drawImage(labSpikes, width * 6 + width/10 * 0 + aXChange, height/10*7, width/10, height/10, this);
            buffer.drawImage(labSpikes, width * 6 + width/10 * 3 + aXChange, height/10*7, width/10, height/10, this);
            buffer.drawImage(labSpikes, width * 6 + width/10 * 6 + aXChange, height/10*7, width/10, height/10, this);
            buffer.drawImage(labSpikes, width * 6 + width/10 * 9 + aXChange, height/10*7, width/10, height/10, this);
            for (int area07XL = width * 6; area07XL < (width * 7)/10 * 10;  area07XL += (width/10)) {
                buffer.drawImage(labTiles, area07XL + aXChange, height/10*9, width/10, height/10, this);
                buffer.drawImage(labTiles, area07XL + aXChange, height/10*8, width/10, height/10, this);
                buffer.drawImage(labTiles, area07XL + aXChange, height/10*0, width/10, height/10, this);
                buffer.drawImage(labTiles, area07XL + aXChange, height/10*1, width/10, height/10, this);
                buffer.drawImage(labTiles, area07XL + aXChange, height/10*2, width/10, height/10, this);
                buffer.drawImage(labTiles, area07XL + aXChange, height/10*3, width/10, height/10, this);
            } // area 07
            for (int area08XL = width * 7; area08XL < (width * 8)/10 * 10; area08XL += width/10) {
                buffer.drawImage(labTiles, area08XL + aXChange, height/10*9, width/10, height/10, this);
            } // area 08
            buffer.drawImage(sprite, spriteXL, spriteYL, spriteXS, spriteYS, this); // sprite
            buffer.drawImage(backMist, 0, 0, width, height, this); // sprite
            
            Font font = new Font("Impact", Font.BOLD, width/25);
            buffer.setFont(font);
            buffer.drawString("Timer: " + timerCtr/100, width/10*8, height/10*1);
            buffer.drawString("Area: " + (xChange + 155)/100, width/10*5, height/10*1);
            buffer.drawString("Tries: " + tries, width/10*2, height/10*1);
            //stats
            if (showStats == true) {
                buffer.drawString("aXChange: " + aXChange, 10, 10);
                buffer.drawString("spriteXS: " + spriteXS, 10, 20);
                buffer.drawString("spriteYS: " + spriteYS, 10, 30);
                buffer.drawString("spriteXL: " + spriteXL, 10, 40);
                buffer.drawString("spriteYL: " + spriteYL, 10, 50);
                buffer.drawString("spriteOnGround: " + spriteOnGround, 10, 60);
                buffer.drawString("xChange: " + xChange, 10, 70);
            }
        }        
        if (level == 2) {
            Font font01 = new Font("Impact", Font.BOLD, width/100 * 12);
            buffer.setFont(font01);
            buffer.drawString("Game Over", centerX - width/100 * 12 * 2, height/10*2);
            Font font02 = new Font("Impact", Font.BOLD, width/100 * 5);
            buffer.setFont(font02);
            buffer.drawString("Your time was: " + timerCtr/100 + " seconds", width/10, height/10*7);
            buffer.drawString("The number of tries were: " + tries, width/10, height/10*5);
        }
        g.drawImage(bufferImage, 0, 0, width, height, this);
    }
   
 
}














