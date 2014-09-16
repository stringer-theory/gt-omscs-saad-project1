package common;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.lang.Math;

/**
 * A class that demonstrates how to draw temperature grids in Java.
 * Displays a square grid in which the cells are painted with
 *   a random intensity of the Color red.
 * 
 * @author Spencer Rugaber, January, 2009
 * @author Vinod Dinakaran, modified slightly to add a default constructor and change the package name.
 */
public class Grid extends JPanel {

    private double[][] matrix;
    public Grid(double[][] matrix){
        System.out.println("new Grid created");
        this.matrix = matrix;
        
        ulhcX = BORDER_SIZE;
        ulhcY = BORDER_SIZE;
        width = WINDOW_SIZE;
        height = WINDOW_SIZE;
        
        NUMBER_OF_ROWS = matrix[0].length;
        NUMBER_OF_COLUMNS = matrix.length;
        CELL_HEIGHT = GRID_SIZE / NUMBER_OF_COLUMNS;
        CELL_WIDTH = GRID_SIZE / NUMBER_OF_ROWS;
    }
 
    /**
     * Informs Swing of how much space is needed for drawing.
     * 
     * @return Dimension - the size of the drawing area
     * @override getPreferredSize in JPanel
     * @see javax.swing.JComponent#getPreferredSize()
     */
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }
    
    /**
     * Paints one cell of the grid.
     *
     * @param aGraphics Graphics into which painting will be done
     * @param row row number of the grid cell
     * @param col column number of the grid cell
     * @param t intensity of Color red to be painted; a number from 0.0 to 1.0
     */
    private void paintSpot(Graphics aGraphics, int row, int col, double t) {
        int rowPos = ulhcY + row * CELL_HEIGHT;
        int colPos = ulhcX + col * CELL_WIDTH;

        // Overwrite everything that was there previously
        aGraphics.setColor(Color.black);
        aGraphics.fillRect(colPos, rowPos, CELL_WIDTH, CELL_HEIGHT);
        
        // Color in RGB format with green and blue values = 0.0
        // System.out.println(t + "," + (t / 100));
        aGraphics.setColor(new Color((float) (t / 100), 0.f, 0.f));    
        aGraphics.fillRect(colPos, rowPos, CELL_WIDTH, CELL_HEIGHT);
    }

    /**
     * Informs Swing how to render in terms of subcomponents.
     * @param aGraphics Graphics - Graphs context for drawing
     * @override paintComponent in JPanel
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    protected void paintComponent(Graphics aGraphics) {
        super.paintComponent(aGraphics);
        
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(),
                BufferedImage.TYPE_INT_ARGB);
        Graphics anotherGraphics = bi.createGraphics();

        for (int i = 0; i < NUMBER_OF_ROWS; i++)
            for (int j = 0; j < NUMBER_OF_COLUMNS; j++)
                paintSpot(anotherGraphics, i, j, matrix[i][j]);
        
        aGraphics.drawImage(bi, 0, 0, this);
   }
        
    /**
     * Size of the containing window in pixels
     */
    private final static int WINDOW_SIZE = 400;
    
    /**
     * Amount of border space around the DrawnGrid in pixels
     */
    private final static int BORDER_SIZE = 50;

    /**
     * Height and width of the DrawnGrid in pixels 
     */
    private final static int GRID_SIZE = WINDOW_SIZE - 2 * BORDER_SIZE;
    
    /**
     * Number of rows of cells in the DrawnGrid 
     */
    private int NUMBER_OF_ROWS;
    
    /**
     * Number of cells in a row 
     */
    private int NUMBER_OF_COLUMNS;
    
    /**
     * The height of a cell in pixels 
     */
    private int CELL_HEIGHT;

    /**
     * The width of a cell in pixels
     */
    private int CELL_WIDTH;

    /**
     * X-coordinate of the upper left hand corner of the rectangle
     *     within the containing widget
     */    
    private int ulhcX;
    
    /**
     * Y-coordinate of the upper left hand corner of the rectangle
     *     within the containing widget
     */
    private int ulhcY;
    
    /**
     * Width of the rectangle in pixels
     */
    private int width;
    
    /** 
     * Height of the rectangle in pixels
     */
    private int height;
}