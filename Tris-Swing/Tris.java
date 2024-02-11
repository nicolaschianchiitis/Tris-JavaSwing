import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class Tris extends JFrame implements ActionListener{
    private JPanel jpTitolo;
    private JLabel jlTitolo;
    private JPanel jpGioco;
    private JButton[] bottoni;
    private JPanel jpNuovaPartita;
    private JButton jbRicomincia;
    private int numMossa;
    // -- Strutture dati per un resoconto della situazione --
    // Ordine righe: superiore, centrale, inferiore (3 righe)
    private boolean[] righe;
    // Ordine colonne: sinistra, centrale, destra (3 colonne)
    private boolean[] colonne;
    // Ordine diagonali: sinistra, destra (2 diagonali)
    private boolean[] diagonali;

    public Tris(){
        this.numMossa = 0;
        this.righe = new boolean[]{false, false, false};
        this.colonne = new boolean[]{false, false, false};
        this.diagonali = new boolean[]{false, false};
        this.setTitle("Tris");
        this.setSize(500, 500);
        this.setLocation(250, 250);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        creaGUI();
        this.setVisible(true);
    }

    private void creaGUI(){
        this.jlTitolo = new JLabel("Partita in corso... inizia X");
        this.jlTitolo.setFont(new Font("HelveticaNeue", Font.PLAIN, 16));
        this.jpTitolo = new JPanel();
        this.jpTitolo.add(this.jlTitolo);
        this.add(this.jpTitolo, BorderLayout.NORTH);
        this.jpGioco = new JPanel(new GridLayout(3, 3, 3, 3));
        this.bottoni = new JButton[9];
        for (int i = 0; i < this.bottoni.length; i++){
            this.bottoni[i] = new JButton();
            this.bottoni[i].addActionListener(this);
            this.bottoni[i].setActionCommand("vuoto");
            this.jpGioco.add(this.bottoni[i]);
        }
        this.add(this.jpGioco, BorderLayout.CENTER);
        this.jpNuovaPartita = new JPanel();
        this.jbRicomincia = new JButton("Nuova partita");
        this.jbRicomincia.setVisible(false);
        this.jbRicomincia.addActionListener(new AscoltaBtnNuovaPartita());
        this.jpNuovaPartita.add(this.jbRicomincia);
        this.add(this.jpNuovaPartita, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (((JButton) e.getSource()).getIcon() == null){
            if (this.numMossa % 2 == 0){ // Se tocca alla X
                ((JButton) e.getSource()).setIcon(new ImageIcon("img/x.png"));
                ((JButton) e.getSource()).setActionCommand("x");
                this.jlTitolo.setText("Partita in corso... tocca a O");
            } else{ // Se tocca alla O
                ((JButton) e.getSource()).setIcon(new ImageIcon("img/o.png"));
                ((JButton) e.getSource()).setActionCommand("y");
                this.jlTitolo.setText("Partita in corso... tocca a X");
            }
            this.numMossa = (this.numMossa + 1) % 2;
        }
        
        switch (checkVincitore()) {
            case "x":
                JOptionPane.showMessageDialog(this, "Ha vinto la X!", "Partita conclusa", JOptionPane.INFORMATION_MESSAGE);
                this.jlTitolo.setText("Partita conclusa. Ha vinto la X!");
                this.jbRicomincia.setVisible(true);
                break;
            case "y":
                JOptionPane.showMessageDialog(this, "Ha vinto la O!", "Partita conclusa", JOptionPane.INFORMATION_MESSAGE);
                this.jlTitolo.setText("Partita conclusa. Ha vinto la O!");
                this.jbRicomincia.setVisible(true);
                break;
            case "stop":
                JOptionPane.showMessageDialog(this, "Non ha vinto nessuno!", "Partita conclusa", JOptionPane.INFORMATION_MESSAGE);
                this.jlTitolo.setText("Partita conclusa. Non ha vinto nessuno!");
                this.jbRicomincia.setVisible(true);
                break;
            case "continua":
                break;
            default:
                break;
        }
    }

    /**
     * Metodo controllaSituazione:
     * permette di verificare lo stato
     * del tabellone di gioco e di
     * aggiornarlo negli appositi
     * attributi di classe.
     */
    private void controllaSituazione(){
        boolean check1 = false, check2 = false;

        // Controllo le righe
        // -- Riga superiore --
        if (this.bottoni[0].getActionCommand().equals(this.bottoni[1].getActionCommand())
                && !this.bottoni[0].getActionCommand().equals("vuoto") &&
                !this.bottoni[1].getActionCommand().equals("vuoto")){
            check1 = true;
        }
        if (this.bottoni[1].getActionCommand().equals(this.bottoni[2].getActionCommand())
                && !this.bottoni[1].getActionCommand().equals("vuoto") &&
                !this.bottoni[2].getActionCommand().equals("vuoto")){
            check2 = true;
        }
        this.righe[0] = (check1 & check2);
        // -- Riga centrale --
        check1 = false;
        check2 = false;
        if (this.bottoni[3].getActionCommand().equals(this.bottoni[4].getActionCommand())
                && !this.bottoni[3].getActionCommand().equals("vuoto") &&
                !this.bottoni[4].getActionCommand().equals("vuoto")){
            check1 = true;
        }
        if (this.bottoni[4].getActionCommand().equals(this.bottoni[5].getActionCommand())
                && !this.bottoni[4].getActionCommand().equals("vuoto") &&
                !this.bottoni[5].getActionCommand().equals("vuoto")){
            check2 = true;
        }
        this.righe[1] = (check1 & check2);
        // -- Riga inferiore --
        check1 = false;
        check2 = false;
        if (this.bottoni[6].getActionCommand().equals(this.bottoni[7].getActionCommand())
                && !this.bottoni[6].getActionCommand().equals("vuoto") &&
                !this.bottoni[7].getActionCommand().equals("vuoto")){
            check1 = true;
        }
        if (this.bottoni[7].getActionCommand().equals(this.bottoni[8].getActionCommand())
                && !this.bottoni[7].getActionCommand().equals("vuoto") &&
                !this.bottoni[8].getActionCommand().equals("vuoto")){
            check2 = true;
        }
        this.righe[2] = (check1 & check2);

        // Controllo le colonne
        // -- Colonna di sinistra --
        check1 = false;
        check2 = false;
        if (this.bottoni[0].getActionCommand().equals(this.bottoni[3].getActionCommand())
                && !this.bottoni[0].getActionCommand().equals("vuoto") &&
                !this.bottoni[3].getActionCommand().equals("vuoto")){
            check1 = true;
        }
        if (this.bottoni[3].getActionCommand().equals(this.bottoni[6].getActionCommand())
                && !this.bottoni[3].getActionCommand().equals("vuoto") &&
                !this.bottoni[6].getActionCommand().equals("vuoto")){
            check2 = true;
        }
        this.colonne[0] = (check1 & check2);
        // -- Colonna centrale --
        check1 = false;
        check2 = false;
        if (this.bottoni[1].getActionCommand().equals(this.bottoni[4].getActionCommand())
                && !this.bottoni[1].getActionCommand().equals("vuoto") &&
                !this.bottoni[4].getActionCommand().equals("vuoto")){
            check1 = true;
        }
        if (this.bottoni[4].getActionCommand().equals(this.bottoni[7].getActionCommand())
                && !this.bottoni[4].getActionCommand().equals("vuoto") &&
                !this.bottoni[7].getActionCommand().equals("vuoto")){
            check2 = true;
        }
        this.colonne[1] = (check1 & check2);
        // -- Colonna di destra --
        check1 = false;
        check2 = false;
        if (this.bottoni[2].getActionCommand().equals(this.bottoni[5].getActionCommand())
                && !this.bottoni[2].getActionCommand().equals("vuoto") &&
                !this.bottoni[5].getActionCommand().equals("vuoto")){
            check1 = true;
        }
        if (this.bottoni[5].getActionCommand().equals(this.bottoni[8].getActionCommand())
                && !this.bottoni[5].getActionCommand().equals("vuoto") &&
                !this.bottoni[8].getActionCommand().equals("vuoto")){
            check2 = true;
        }
        this.colonne[2] = (check1 & check2);

        // Controllo le diagonali
        // -- Diagonale di sinistra --
        check1 = false;
        check2 = false;
        if (this.bottoni[0].getActionCommand().equals(this.bottoni[4].getActionCommand())
                && !this.bottoni[0].getActionCommand().equals("vuoto") &&
                !this.bottoni[4].getActionCommand().equals("vuoto")){
            check1 = true;
        }
        if (this.bottoni[4].getActionCommand().equals(this.bottoni[8].getActionCommand())
                && !this.bottoni[4].getActionCommand().equals("vuoto") &&
                !this.bottoni[8].getActionCommand().equals("vuoto")){
            check2 = true;
        }
        this.diagonali[0] = (check1 & check2);
        // -- Diagonale di destra --
        check1 = false;
        check2 = false;
        if (this.bottoni[2].getActionCommand().equals(this.bottoni[4].getActionCommand())
                && !this.bottoni[2].getActionCommand().equals("vuoto") &&
                !this.bottoni[4].getActionCommand().equals("vuoto")){
            check1 = true;
        }
        if (this.bottoni[4].getActionCommand().equals(this.bottoni[6].getActionCommand())
                && !this.bottoni[4].getActionCommand().equals("vuoto") &&
                !this.bottoni[6].getActionCommand().equals("vuoto")){
            check2 = true;
        }
        this.diagonali[1] = (check1 & check2);
    }

    /**
     * Metodo checkVincitore:
     * permette di controllare chi
     * ha vinto e di determinare
     * il continuo o l'interruzione
     * della partita corrente.
     * @return "x" se vince la X;
     * <li>"o" se vince O;</li>
     * <li>"continua" se non ha vinto
     * nessuno ma ci sono ancora
     * caselle vuote;</li>
     * <li>"stop" se non ha vinto
     * nessuno e non ci sono
     * caselle vuote.</li>
     */
    private String checkVincitore(){
        // Aggiorna la situazione del tabellone di gioco
        controllaSituazione();

        // Controllo le righe
        for (int i = 0; i < this.righe.length; i++){
            if (this.righe[i]){
                return this.bottoni[i * 3].getActionCommand();
            }
        }

        // Controllo le colonne
        for (int i = 0; i < this.colonne.length; i++){
            if (this.colonne[i]){
                return this.bottoni[i].getActionCommand();
            }
        }

        // Controllo le diagonali
        if (diagonali[0]){
            return this.bottoni[0].getActionCommand();
        } else if (diagonali[1]){
            return this.bottoni[2].getActionCommand();
        }

        // Se nessuno ha vinto...
        // Se le caselle non sono tutte occupate, continua la partita
        for (int i = 0; i < this.bottoni.length; i++){
            if (this.bottoni[i].getActionCommand().equals("vuoto")){
                return "continua";
            }
        }
        // Altrimenti finisci la partita
        return "stop";
    }

    public static void main(String[] args) {
        @SuppressWarnings("unused")
        Tris tris = new Tris();
    }

    class AscoltaBtnNuovaPartita implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset del gioco
            numMossa = 0;
            jlTitolo.setText("Partita in corso... inizia X");
            for (JButton btn : bottoni){
                btn.setIcon(null);
                btn.setActionCommand("vuoto");
            }
            for (int i = 0; i < righe.length; i++){
                if (i < 2){
                    diagonali[i] = false;
                }
                righe[i] = false;
                colonne[i] = false;
            }
            jbRicomincia.setVisible(false);
        }
    }
}