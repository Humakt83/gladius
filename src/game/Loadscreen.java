package game;
import java.awt.*;

import javax.swing.*;
public class Loadscreen extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4550022765307786702L;
	private ImageIcon bg = new ImageIcon("res/Gladiusload.png");
	private ImageIcon gladius = new ImageIcon("Gladius.png");
	private JPanel container = new JPanel(){
		static final long serialVersionUID = 1;
		protected void paintComponent(Graphics g)
		{
			g.drawImage(bg.getImage(), 0, 0, null);
			Dimension d = new Dimension(215,319);
			g.drawImage(bg.getImage(), 0, 0, d.width, d.height, null);
			super.paintComponent(g);
		}};
	
	public Loadscreen(){
		add(container);
		this.setBackground(Color.RED);
		container.setBackground(Color.RED);
		container.setOpaque(false);
		container.setPreferredSize(new Dimension(205,308));
		setTitle("Gladius");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        //center frame to screen
        setLocation((screenSize.width - frameSize.width) / 2,
                          (screenSize.height - frameSize.height)/4);
		pack();
		this.setIconImage(gladius.getImage());
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}
	public void closeLoadscreen(){
		dispose();
	}
}
