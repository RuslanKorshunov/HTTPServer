package View;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame
{
    private final String TITLE="HTTP-Сервер";
    private final int WIDTH=600;
    private final int HEIGHT=600;
    private SpringLayout layout;
    private Container container;
    private JTextArea memo;

    public MainWindow()
    {
        setTitle(TITLE);
        setSize(new Dimension(WIDTH, HEIGHT));
        setLocation(new Point(400, 100));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        container=this.getContentPane();
        layout=new SpringLayout();
        container.setLayout(layout);

        setVisible(true);

        createMenu();
    }

    public void createMenu()
    {
        memo=new JTextArea();
        JScrollPane memoScrollPane=new JScrollPane(memo);

        container.add(memoScrollPane);

        layout.putConstraint(SpringLayout.NORTH, memoScrollPane, 20, SpringLayout.NORTH, container);
        layout.putConstraint(SpringLayout.WEST, memoScrollPane, 20, SpringLayout.WEST, container);
        layout.putConstraint(SpringLayout.SOUTH, memoScrollPane, -20, SpringLayout.SOUTH, container);
        layout.putConstraint(SpringLayout.EAST, memoScrollPane, -20, SpringLayout.EAST, container);

        revalidate();
        repaint();
    }

    public void deliverMessage(String message)
    {
        memo.append(message);
    }
}
