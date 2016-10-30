package frames;

import client.ClientGuiController;
import message.MessageType;
import session.Place;
import session.Session;
import session.StatePlace;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Set;

/**
 * Created by ANYA on 07.10.2016.
 */
public class MainFrame extends AbstractFrame {
    private DefaultListModel<Session> modelSessionList;
    private DefaultListModel<Place> modelPlaceList;
    private Session selectedSession;

    public MainFrame(ClientGuiController controller) {
        super(controller);
    }

    public void initFirstPanel(JPanel firstPanel) {
        firstPanel.setLayout(null);
        firstPanel.setBounds(0, 0, 200, 600);
    }

    public void initSecondPanel(JPanel secondPanel) {
        secondPanel.setLayout(null);
        secondPanel.setBounds(200, 0, 300, 600);
        secondPanel.setVisible(false);
    }

    public void initThirdPanel(JPanel thirdPanel) {
        thirdPanel.setLayout(null);
        thirdPanel.setBounds(500, 0, 200, 600);
    }

    @Override
    protected void initializationWindow() {
        setBackground(Color.PINK);
        setLayout(null);
        JPanel firstPanel = new JPanel();
        JPanel secondPanel = new JPanel();
        JPanel thirdPanel = new JPanel();
        initFirstPanel(firstPanel);
        initSecondPanel(secondPanel);
        initThirdPanel(thirdPanel);
        add(firstPanel);
        add(secondPanel);
        add(thirdPanel);
        JButton allSessions = new JButton("Все сеансы");
        JButton myBookedTickets = new JButton("<html>Мои забронированные<br>билеты</html>");
        JButton myBuyedTickets = new JButton("<html>Мои купленные<br>билеты</html>");
        allSessions.setBounds(30,10,130,50);
        myBookedTickets.setBounds(30,70,130,80);
        myBuyedTickets.setBounds(30,160,130,80);
        thirdPanel.add(allSessions);
        thirdPanel.add(myBookedTickets);
        thirdPanel.add(myBuyedTickets);

        ////////////////////////////////////////////////////////////////////
        Session[] sessionList = controller.getSessionList().toArray(new Session[]{});
        modelSessionList = new DefaultListModel<>();
        modelPlaceList = new DefaultListModel<>();

        if (sessionList != null) {

            for (Session session : sessionList) {
                int flag=0;
                for (Place p:session.getAllPlaces()){
                    if (p.getStatePlace()==StatePlace.AVAILABLE){
                        flag++;
                    }
                }
                if (flag != 0) {
                    modelSessionList.addElement(session);
                }
            }

        }
        if (modelPlaceList==null){
            secondPanel.setVisible(false);
            SwingUtilities.invokeLater(this::revalidate);
        }

        JList list = new JList(modelSessionList);
        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBounds(0, 0, 200, 600);
        firstPanel.add(scrollPane);
        list.setLayoutOrientation(JList.VERTICAL);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                JLabel label = (JLabel) component;
                Session session = (Session) value;
                String result = "<html><style>\n" +
                        "    div.wrapper {\n" +
                        "        border: 4px solid black;\n" +
                        "        width:200px;\n" +
                        "    }\n" +
                        "</style>\n" +
                        "<div class=\"wrapper\">\n" +
                        "    <div>\n" +
                        "<span style='color: blue; font-size: 15px;'>" +
                        (int) session.getId() +
                        " </span><span style='font-size: 15px;'>" +
                        session.getDate().get(Calendar.DAY_OF_MONTH) + "." + session.getDate().get(Calendar.MONTH) + " " + session.getDate().get(Calendar.HOUR)
                        + ":" + session.getDate().get(Calendar.MINUTE) +
                        " </span><br>\n" +
                        "    <span>Свободных мест: " +
                        session.getAllPlaces().size() +
                        "</span><br>" +
                        "    <span>Фильм: " +
                        session.getFilm().getFilmName() +
                        "</span><br>" +
                        "<span>Продолжительность: " +
                        String.format("%.0f", session.getDuration()) + " мин" +
                        "</span>\n" +
                        "</div>" +
                        "</html>";
                label.setText(result);
                return label;
            }
        });
////////////////////////////////////////////////////////////
        JLabel header = new JLabel();
        header.setBounds(20, 0, 300, 50);
        secondPanel.add(header);
        JButton buyAll = new JButton("Купить все");
        JButton bookAll = new JButton("Забронировать все");
        bookAll.setBounds(10, 420, 160, 30);
        buyAll.setBounds(185, 420, 100, 30);
        secondPanel.add(bookAll);
        secondPanel.add(buyAll);

///////////////////////////////////////////////////////////////
        JList placeList = new JList(modelPlaceList);

        placeList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component component = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                JLabel labelu = (JLabel) component;
                Place place = (Place) value;
                String result = "<html><style>\n" +
                        "    div.wrapper {\n" +
                        "        width:225px;\n" +
                        "border: 1px solid red; " +
                        "    }\n" +
                        "</style>\n" +
                        "<div class=\"wrapper\">\n" +
                        "    <div>\n" +
                        "<span>Место №" +
                        place.getPlaceNumber() +
                        "<span>Цена " +
                        place.getPrice() + " руб" +
                        "</span><br>" +
                        " </span><span style='font-size: 16px; color='green'>Статус: " +
                        place.getStatePlace() +
                        "</span><br>" +
                        "</div>" +
                        "</html>";
                labelu.setText(result);
                return labelu;
            }
        });
        JScrollPane scrollPane1 = new JScrollPane(placeList);
        placeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        scrollPane1.setBounds(3, 50, 300, 350);
        secondPanel.add(scrollPane1);

        //////////////////////////////////////////////
        bookAll.addActionListener(e -> {if (!placeList.isSelectionEmpty()) {
            ArrayList<Place> selectedPlaces = (ArrayList<Place>) placeList.getSelectedValuesList();

                handleButtonEvent(true, selectedPlaces, selectedSession);
            }
        });
        buyAll.addActionListener(e -> { if (!placeList.isSelectionEmpty()) {
            ArrayList<Place> selectedPlaces = (ArrayList<Place>) placeList.getSelectedValuesList();

                handleButtonEvent(false, selectedPlaces, selectedSession);
            }
        });
        //////////////////////////////////////////////////////////////////////////////
        list.addListSelectionListener(event -> {
            if (!list.isSelectionEmpty()) {
                //JOptionPane.showMessageDialog(this, "Сеанс выбран");
                selectedSession = (Session) list.getSelectedValue();
                SwingUtilities.invokeLater(() -> {
                    String result = "<html><style>\n" +
                            "    div.wrapper {\n" +
                            "        width:200px;\n" +
                            "    }\n" +
                            "</style>\n" +
                            "<div class=\"wrapper\">\n" +
                            "    <div>\n" +
                            "<span style='color: blue; font-size: 15px;'>" +
                            (int) selectedSession.getId() +
                            " </span><span style='font-size: 15px;'>" +
                            selectedSession.getDate().get(Calendar.DAY_OF_MONTH) + "." + selectedSession.getDate().get(Calendar.MONTH) + " " + selectedSession.getDate().get(Calendar.HOUR)
                            + ":" + selectedSession.getDate().get(Calendar.MINUTE) +
                            " </span><br>\n" +
                            "</div>" +
                            "</html>";
                    header.setText(result);
                });
                if (placeList != null) {
                    modelPlaceList.clear();
                    for (Place pl : selectedSession.getAllPlaces()) {
                        if (pl.getStatePlace() == StatePlace.AVAILABLE)
                            modelPlaceList.addElement(pl);
                    }
                }
               java.util.List<Session> newSessionList = controller.getSessionList();
                if (modelPlaceList.size() == 0){
                    secondPanel.setVisible(false);
                    modelSessionList.clear();
                    for (Session s:newSessionList) {
                        if (s.equals(selectedSession)){
                            newSessionList.remove(s);
                        }
                        modelSessionList.addElement(s);
                    }
                    SwingUtilities.invokeLater(this::repaint);
                }
                else secondPanel.setVisible(true);
                SwingUtilities.invokeLater(this::repaint);
///////////////////////////////////////////////////////////////////////////////

            }
            else secondPanel.setVisible(false);
        });

    }


    @Override
    public void showInfo(MessageType type, Object o) {
        if (type == MessageType.SESSION_LIST) {
            modelSessionList.clear();
            modelPlaceList.clear();
            java.util.List<Session> listfromModel = controller.getSessionList();

            if (listfromModel != null) {
                SwingUtilities.invokeLater(() -> {

                    for (Session session : listfromModel) {
                        int flag=0;
                        for (Place p:session.getAllPlaces()){
                            if (p.getStatePlace()==StatePlace.AVAILABLE){
                                flag++;
                            }
                        }
                        if (flag > 0) {
                            modelSessionList.addElement(session);
                        }
                        if (selectedSession != null) {
                            if (session.getId() == selectedSession.getId()) {
                                selectedSession = session;
                            }
                        }
                    }
                    if (selectedSession != null) {
                        for (Place p : selectedSession.getAllPlaces()) {
                            if (p.getStatePlace() == StatePlace.AVAILABLE)
                                modelPlaceList.addElement(p);
                        }
                    }
                    SwingUtilities.invokeLater(this::revalidate);
                });

            }

        }

    }

    public void handleButtonEvent(boolean flag, ArrayList<Place> selectedPlaces, Session session) {
        if (flag) {
            controller.buyOrBook(true, selectedPlaces, session);
        } else {
            controller.buyOrBook(false, selectedPlaces, session);
        }
    }

    @Override
    public Dimension getDimension() {
        return new Dimension(700, 500);
    }
}