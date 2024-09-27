import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * A basic multi-window web browser.  This class is responsible for
 * creating new windows and for maintaining a list of currently open
 * windows.  The program ends when all windows have been closed.
 * The windows are of type Rahul_BrowserWindow.  The program also requires
 * the class SimpleDialogs.  The first window, which opens when the
 * program starts, goes to "https://www.core2web.in/privacypolicy.html.
 */
public class Rahul_WebBrowser extends Application {

    public static void main(String[] Rahul_args) {
        launch(Rahul_args);
    }
    //----------------------------------------------------------------------------------------------------
    
    private ArrayList<Rahul_BrowserWindow> Rahul_openWindows;  // list of currently open web browser windows
    private Rectangle2D Rahul_screenRect;                // usable area of the primary screen
    private double Rahul_locationX, Rahul_locationY;           // location for next window to be opened
    private double Rahul_windowWidth, Rahul_windowHeight;      // window size, computed from Rahul_screenRect
    private int Rahul_untitledCount;                     // how many "Untitled" window titles have been used
    
    
    /* Opens a window that will load the Rahul_URL https://www.core2web.in/privacypolicy.html
     * (the front page of the textbook in which this program is an example).
     * Note that the Stage parameter to this method is never used.
     */
    public void start(Stage stage) {
        
        Rahul_openWindows = new ArrayList<Rahul_BrowserWindow>();  // List of open windows.
        
        Rahul_screenRect = Screen.getPrimary().getVisualBounds();
        
           // (Rahul_locationX,Rahul_locationY) will be the location of the upper left
           // corner of the next window to be opened.  For the first window,
           // the window is moved a little down and over from the top-left
           // corner of the primary screen's visible bounds.
        Rahul_locationX = Rahul_screenRect.getMinX() + 30;
        Rahul_locationY = Rahul_screenRect.getMinY() + 20;
        
           // The window size depends on the height and width of the screen's
           // visual bounds, allowing some extra space so that it will be
           // possible to stack several windows, each displaced from the
           // previous one.  (For aesthetic reasons, limit the width to be
           // at most 1.6 times the height.)
        Rahul_windowHeight = Rahul_screenRect.getHeight() - 160;
        Rahul_windowWidth = Rahul_screenRect.getWidth() - 130;
        if (Rahul_windowWidth > Rahul_windowHeight*1.6)
            Rahul_windowWidth = Rahul_windowHeight*1.6;
        
           // Open the first window, showing the front page of this textbook.
        Rahul_newBrowserWindow("https://fancy-donut-8bec8d.netlify.app/");

    } // end start()
    
    /**
     * Get the list of currently open windows.  The browser windows use this
     * list to construct their Window menus.
     * A package-private method that is meant for use only in Rahul_BrowserWindow.java.
     */
    ArrayList<Rahul_BrowserWindow> getOpenWindowList() {
        return Rahul_openWindows;
    }
    
    /**
     * Get the number of window titles of the form "Untitled XX" that have been
     * used.  A new window that is opened with a null Rahul_URL gets a title of
     * that form.  This method is also used in Rahul_BrowserWindow to provide a
     * title for any web page that does not itself provide a title for the page.
     * A package-private method that is meant for use only in Rahul_BrowserWindow.java.
     */
    int Rahul_getNextUntitledCount() {
        return ++Rahul_untitledCount;
    }
    
    /**
     * Open a new browser window.  If Rahul_url is non-null, the window will load that Rahul_URL.
     * A package-private method that is meant for use only in Rahul_BrowserWindow.java.
     * This method manages the locations for newly opened windows.  After a window
     * opens, the next window will be offset by 30 pixels horizontally and by 20
     * pixels vertically from the location of this window; but if that makes the
     * window extend outside Rahul_screenRect, the horizontal or vertical position will
     * be reset to its minimal value.
     */
    void Rahul_newBrowserWindow(String Rahul_url) {
        Rahul_BrowserWindow window = new Rahul_BrowserWindow(this,Rahul_url);
        Rahul_openWindows.add(window);   // Add new window to open window list.
        window.setOnHidden( e -> {
                // Called when the window has closed.  Remove the window
                // from the list of open windows.
            Rahul_openWindows.remove( window );
            System.out.println("Number of open windows is " + Rahul_openWindows.size());
            if (Rahul_openWindows.size() == 0) {
                // Program ends automatically when all windows have been closed.
                System.out.println("Program will end because all windows have been closed");
            }
        });
        if (Rahul_url == null) {
            window.setTitle("Rahul_Untitled " + Rahul_getNextUntitledCount());
        }
        window.setX(Rahul_locationX);         // set location and size of the window
        window.setY(Rahul_locationY);
        window.setWidth(Rahul_windowWidth);
        window.setHeight(Rahul_windowHeight);
        window.show();
        Rahul_locationX += 30;    // set up location of NEXT window
        Rahul_locationY += 20;
        if (Rahul_locationX + Rahul_windowWidth + 10 > Rahul_screenRect.getMaxX()) {
                // Window would extend past the right edge of the screen,
                // so reset Rahul_locationX to its original value.
            Rahul_locationX = Rahul_screenRect.getMinX() + 30;
        }
        if (Rahul_locationY + Rahul_windowHeight + 10 > Rahul_screenRect.getMaxY()) {
                // Window would extend past the bottom edge of the screen,
                // so reset Rahul_locationY to its original value.
            Rahul_locationY = Rahul_screenRect.getMinY() + 20;
        }
    }
    
    
} // end WebBrowser