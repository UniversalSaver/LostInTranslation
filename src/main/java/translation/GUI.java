package translation;

import examples.JSONTranslationExample;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

public class GUI {

    public static void main(String[] args) {
        CountryCodeConverter countryConverter = new CountryCodeConverter();
        LanguageCodeConverter converter = new LanguageCodeConverter();

        SwingUtilities.invokeLater(() -> {
            JPanel countryPanel = new JPanel();
            JTextField countryField = new JTextField(10);
            countryField.setText("can");
            countryField.setEditable(true); // we only support the "can" country code for now
            countryPanel.add(new JLabel("Country:"));

            String[] languages = converter.getLanguages().toArray(new String[0]);
            Arrays.sort(languages);
            String[] countries = countryConverter.getCountries().toArray(new String[0]);
            Arrays.sort(countries);

            JPanel languagePanel = new JPanel();

            //Creates the panel for selecting languages with a scrolled list.



            languagePanel.add(new JLabel("Language:"));


            JComboBox<String> languageComboBox = new JComboBox<>(languages);
            languagePanel.add(languageComboBox);

            JList<String> countryList = new JList<>(countries);
            countryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane countryScrollPane = new JScrollPane(countryList);
            countryPanel.add(countryScrollPane);


            JPanel buttonPanel = new JPanel();
            //JButton submit = new JButton("Submit");
            //buttonPanel.add(submit);

            JLabel resultLabelText = new JLabel("Translation:");
            buttonPanel.add(resultLabelText);
            JLabel resultLabel = new JLabel("\t\t\t\t\t\t\t");
            buttonPanel.add(resultLabel);


            // adding listener for when the user clicks the submit button
            countryList.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String languageCode = converter.fromLanguage(languageComboBox.getSelectedItem().toString());

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new JSONTranslator();
                    String countryCode = countryConverter.fromCountry(countryList.getSelectedValue());


                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.

                    String result = translator.translate(countryCode, languageCode);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });
            languageComboBox.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    String languageCode = converter.fromLanguage(languageComboBox.getSelectedItem().toString());

                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.
                    Translator translator = new JSONTranslator();
                    String countryCode = countryConverter.fromCountry(countryList.getSelectedValue());


                    // for now, just using our simple translator, but
                    // we'll need to use the real JSON version later.

                    String result = translator.translate(countryCode, languageCode);
                    if (result == null) {
                        result = "no translation found!";
                    }
                    resultLabel.setText(result);

                }

            });

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

            mainPanel.add(languagePanel);
            mainPanel.add(buttonPanel);
            mainPanel.add(countryPanel);

            JFrame frame = new JFrame("Country Name Translator");
            frame.setContentPane(mainPanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);


        });
    }
}
