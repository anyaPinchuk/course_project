package frames;

import client.ClientGuiController;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by ANYA on 13.10.2016.
 */
public class AddSessionFrame extends AbstractFrame {
    private JComboBox sessionDay;
    private JComboBox sessionMonth;
    private JComboBox sessionHours;
    private JComboBox sessionMinutes;
    private String day;
    private String month;
    private String hour;
    private String minute;
    private String places;
    private String film;
    private String duration;
    private JTextField sessionCountOfPlaces;
    private JTextField filmName;
    private JTextField sessionDuration;

    public AddSessionFrame(ClientGuiController controller) {
        super(controller);
    }

    @Override
    protected void initializationWindow() {
        JLabel title = new JLabel("Заполните данные о сеансе");
        title.setLayout(null);
        title.setBounds(100,10, 200,20);
        add(title);
        JLabel titleDate = new JLabel("1. Выберите время сеанса");
        titleDate.setLayout(null);
        titleDate.setBounds(30,30,200,20);
        add(titleDate);
        JLabel titleMonth = new JLabel("Месяц:");
        titleMonth.setLayout(null);
        titleMonth.setBounds(30,65,50,20);
        add(titleMonth);
        JLabel titleDay = new JLabel("День:");
        titleDay.setLayout(null);
        titleDay.setBounds(130,65,40,20);
        add(titleDay);
        JLabel titleHours = new JLabel("Часы:");
        titleHours.setLayout(null);
        titleHours.setBounds(190,65,40,20);
        add(titleHours);
        JLabel titleMinutes = new JLabel("Минуты:");
        titleMinutes.setLayout(null);
        titleMinutes.setBounds(240,65,60,20);
        add(titleMinutes);
        String[] days = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21",
                "22","23","24","25","26","27","28","29","30","31"};
        String[] hours = {"11","12","13","14","15","16","17","18","19","20","21","22","23"};
        String[] minutes = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21",
                "22","23","24","25","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40","41","42","43",
                "44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"};
        String[] months = {"январь","февраль","март","апрель","май","июнь","июль","август","сентябрь","октябрь","ноябрь","декабрь"};
        sessionMonth = new JComboBox(months);
        sessionMonth.setLayout(null);
        sessionMonth.setBounds(30,90,90,20);
        add(sessionMonth);
        sessionDay = new JComboBox(days);
        sessionDay.setLayout(null);
        sessionDay.setBounds(130,90,40,20);
        add(sessionDay);
        sessionHours = new JComboBox(hours);
        sessionHours.setLayout(null);
        sessionHours.setBounds(190,90,40,20);
        add(sessionHours);
        sessionMinutes = new JComboBox(minutes);
        sessionMinutes.setLayout(null);
        sessionMinutes.setBounds(240,90,40,20);
        add(sessionMinutes);
        sessionMonth.addActionListener(e->{
            String selectedMonth = (String)sessionMonth.getSelectedItem();
            switch (selectedMonth){
                case "январь":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    sessionDay = new JComboBox(days);
                    sessionDay.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
                case "февраль":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    String[] copy = Arrays.stream(days).limit(29).collect(Collectors.toList()).toArray(new String[]{});
                    sessionDay = new JComboBox(copy);
                    sessionDay.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
                case "март":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    sessionDay = new JComboBox(days);
                    sessionMonth.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
                case "апрель":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    String[] copy = Arrays.stream(days).limit(30).collect(Collectors.toList()).toArray(new String[]{});
                    sessionDay = new JComboBox(copy);
                    sessionDay.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
                case "май":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    sessionDay = new JComboBox(days);
                    sessionMonth.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
                case "июнь":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    String[] copy = Arrays.stream(days).limit(30).collect(Collectors.toList()).toArray(new String[]{});
                    sessionDay = new JComboBox(copy);
                    sessionDay.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
                case "июль":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    sessionDay = new JComboBox(days);
                    sessionMonth.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
                case "август":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    sessionDay = new JComboBox(days);
                    sessionMonth.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
                case "сентябрь":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    String[] copy = Arrays.stream(days).limit(30).collect(Collectors.toList()).toArray(new String[]{});
                    sessionDay = new JComboBox(copy);
                    sessionDay.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
                case "октябрь":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    sessionDay = new JComboBox(days);
                    sessionMonth.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
                case "ноябрь":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    String[] copy = Arrays.stream(days).limit(30).collect(Collectors.toList()).toArray(new String[]{});
                    sessionDay = new JComboBox(copy);
                    sessionDay.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
                case "декабрь":{
                    if (sessionDay!=null) {
                        remove(sessionDay);
                    }
                    sessionDay = new JComboBox(days);
                    sessionMonth.setLayout(null);
                    sessionDay.setBounds(130,60,40,20);
                    add(sessionDay);
                    SwingUtilities.invokeLater(this::repaint);
                    break;
                }
            }
            add(sessionDay);
        });
        JLabel titlePlaces = new JLabel("2. Введите количество мест в зале");
        titlePlaces.setBounds(30,120,220,20);
        add(titlePlaces);
        sessionCountOfPlaces = new JTextField(3);
        sessionCountOfPlaces.setLayout(null);
        sessionCountOfPlaces.setBounds(30,140,40,20);
        add(sessionCountOfPlaces);
        JLabel titleFilm = new JLabel("3. Введите название фильма");
        titleFilm.setBounds(30,160,200,20);
        add(titleFilm);
        JButton addSession = new JButton("Добавить");
        setLayout(null);
        setBackground(Color.ORANGE);
        addSession.setBounds(150,500,100,30);
        add(addSession);

    }

    @Override
    public Dimension getDimension() {
        return new Dimension(400,600);
    }
}
