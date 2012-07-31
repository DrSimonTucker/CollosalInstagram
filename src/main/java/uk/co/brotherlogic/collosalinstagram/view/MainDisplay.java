package uk.co.brotherlogic.collosalinstagram.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import uk.co.brotherlogic.collosalinstagram.core.InstagramUser;
import uk.co.brotherlogic.collosalinstagram.core.Room;

class ImagePanel extends JPanel
{
   private BufferedImage image;

   public ImagePanel(BufferedImage img)
   {
      image = img;
   }

   @Override
   public Dimension getPreferredSize()
   {
      return new Dimension(image.getHeight(), image.getWidth());
   }

   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      g.drawImage(image, 0, 0, null); // see javadoc for more info on the
                                      // parameters
   }

   public void setImage(BufferedImage img)
   {
      image = img;
   }

}

public class MainDisplay extends JPanel
{
   public static void main(String[] args)
   {
      InstagramUser user = new InstagramUser("brotherlogic");
      MainDisplay display = new MainDisplay(user.buildRooms());
      JFrame framer = new JFrame();
      framer.add(display);
      framer.pack();
      framer.setLocationRelativeTo(null);
      framer.setVisible(true);
   }

   Room currRoom;

   JTextArea field;

   ImagePanel mainPanel;

   public MainDisplay(Room baseRoom)
   {
      currRoom = baseRoom;
      initGUI();
   }

   private void initGUI()
   {
      this.setLayout(new BorderLayout());
      try
      {
         mainPanel = new ImagePanel(currRoom.getInstagramImage());
         this.add(mainPanel, BorderLayout.NORTH);
         field = new JTextArea(currRoom.getDescription());
         field.setEditable(false);
         this.add(field, BorderLayout.CENTER);

         final JTextField entryField = new JTextField();
         this.add(entryField, BorderLayout.SOUTH);
         entryField.addActionListener(new ActionListener()
         {
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
               currRoom = currRoom.traverse(entryField.getText());
               setupForRoom(currRoom);
               entryField.setText("");
            }
         });
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
   }

   private void setupForRoom(Room rm)
   {
      try
      {
         mainPanel.setImage(rm.getInstagramImage());
         field.setText(rm.getDescription());
         repaint();
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }

   }
}
