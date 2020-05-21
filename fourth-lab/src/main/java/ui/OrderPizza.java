package ui;

import model.Kernel;
import model.Order;
import model.OrderingEventSource;
import selectors.Drink;
import selectors.Sauce;
import selectors.Size;
import selectors.Title;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OrderPizza extends JFrame {

    private static final long serialVersionUID = -8264996296822012965L;
    JPanel contentPane;
    JTextField TF_Address;
    JTextField TF_Time;

    public static final String TF_ADDRESS = "tfAddress";
    public static final String TF_TIME = "tfTime";
    public static final String CMB_TITLE = "cmbTitle";
    public static final String CMB_SIZE = "cmbSize";
    public static final String CMB_DRINK = "cmbDrink";
    public static final String CMB_SAUCE = "cmdSauce";

    public static final String BTN_SEND = "btnSend";

    public void start() {
        EventQueue.invokeLater(() -> {
            try {
                setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public OrderPizza(Kernel app) {
        final OrderingEventSource event_src = new OrderingEventSource(app);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Electro-пиццерия");

        setBounds(500, 200, 400, 400);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel title = new JLabel(
                "Бесконтактная доставка работает во время пандемии");
        title.setBounds(15, 15, 400, 30);
        contentPane.add(title);

        JLabel address = new JLabel(
                "Куда везти");
        address.setBounds(15, 40, 150, 20);
        contentPane.add(address);

        TF_Address = new JTextField();
        TF_Address.setBounds(135, 40, 240, 22);
        contentPane.add(TF_Address);
        TF_Address.setName(TF_ADDRESS);
        TF_Address.setColumns(10);

        JLabel time = new JLabel(
                "Когда везти");
        time.setBounds(15, 70, 150, 20);
        contentPane.add(time);

        TF_Time = new JTextField();
        TF_Time.setBounds(135, 70, 115, 20);
        contentPane.add(TF_Time);
        TF_Time.setName(TF_TIME);
        TF_Time.setColumns(10);

        JLabel selectPizza = new JLabel(
                "Выберите пиццу");
        selectPizza.setBounds(15, 115, 350, 20);
        contentPane.add(selectPizza);

        JLabel pizzaName = new JLabel(
                "Какую желаете?");
        pizzaName.setBounds(15, 145, 350, 20);
        contentPane.add(pizzaName);

        JComboBox cbPizzaName = new JComboBox();
        cbPizzaName.setName(CMB_TITLE);
        cbPizzaName.setBounds(135, 145, 150, 20);
        contentPane.add(cbPizzaName);
        cbPizzaName.setModel(new DefaultComboBoxModel(Title.values()));
        cbPizzaName.setSelectedItem(Title.Classic);

        JLabel pizzaSize = new JLabel("Размер не главное!");
        pizzaSize.setBounds(15, 190, 350, 20);
        contentPane.add(pizzaSize);

        JComboBox cbPizzaSize = new JComboBox();
        cbPizzaSize.setName(CMB_SIZE);
        cbPizzaSize.setBounds(135, 190, 150, 22);
        contentPane.add(cbPizzaSize);
        cbPizzaSize.setModel(new DefaultComboBoxModel(Size.values()));
        cbPizzaSize.setSelectedItem(Size.Big);

        JLabel drink = new JLabel("Что будете пить?");
        drink.setBounds(15, 230, 350, 20);
        contentPane.add(drink);

        JComboBox cbDrink = new JComboBox();
        cbDrink.setName(CMB_DRINK);
        cbDrink.setBounds(135, 230, 155, 20);
        contentPane.add(cbDrink);
        cbDrink.setModel(new DefaultComboBoxModel(Drink.values()));
        cbDrink.setSelectedItem(Drink.Coffee);

        JLabel sauce = new JLabel("Не забудьте соус!");
        sauce.setBounds(15, 270, 155, 20);
        contentPane.add(sauce);

        JComboBox cbSauce = new JComboBox();
        cbSauce.setName(CMB_SAUCE);
        cbSauce.setBounds(135, 270, 155, 20);
        contentPane.add(cbSauce);
        cbSauce.setModel(new DefaultComboBoxModel(Sauce.values()));
        cbSauce.setSelectedItem(Sauce.Barbecue);

        JButton btnOrder = new JButton("Заказать");
        btnOrder.setName(BTN_SEND);
        btnOrder.setBackground(new Color(154, 205, 50));
        btnOrder.setForeground(new Color(0, 0, 0));
        btnOrder.setBounds(150, 335, 100, 25);
        contentPane.add(btnOrder);

        btnOrder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if (!TF_Address.getText().equals("") && !TF_Time.getText().equals("")) {
                        Order order = new Order(
                                TF_Address.getText(),
                                TF_Time.getText(),
                                cbPizzaName.getSelectedItem().toString(),
                                cbPizzaSize.getSelectedItem().toString(),
                                cbDrink.getSelectedItem().toString(),
                                cbSauce.getSelectedItem().toString());
                        event_src.SendOrder(order);
                    } else {
                        event_src.SendOrder(null);
                    }
                } catch (IllegalArgumentException ex) {
                    event_src.SendOrder(null);
                }
            }
        });
    }

}

