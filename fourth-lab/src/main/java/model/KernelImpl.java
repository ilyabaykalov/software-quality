package model;

import javax.swing.*;
import java.text.MessageFormat;
import java.util.Observable;

public class KernelImpl implements Kernel {

    @Override
    public void ConnectToServer() {
    }

    @Override
    public void DisconnectFromServer() {
    }

    @Override
    public void update(Observable event_source, Object data) {
        if (event_source instanceof OrderingEventSource) {
            Order received = (Order) data;
            if (data != null)
                JOptionPane.showMessageDialog(null, MessageFormat.format("Ваш заказ: {0} питса {1} и {2}\nбудет доставлен по адресу {3} в {4}",
                        received.size, received.title, received.drink, received.address, received.time), "Информэйшн", JOptionPane.INFORMATION_MESSAGE);
            else JOptionPane.showMessageDialog(null, "Походу что-то пошло не так :(", "Ошибочка", JOptionPane.WARNING_MESSAGE);
        } else
            JOptionPane.showMessageDialog(null, "Что-то нереальное случилось, не знаю как быть :(");
    }

}
