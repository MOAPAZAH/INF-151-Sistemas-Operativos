package proyechrrn;

import java.awt.CardLayout;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class proyectoHRRN extends javax.swing.JFrame {
     String [][]tablaE = new  String[10][100];
     String [][]tablaE1 = new  String[10][100];
     Queue<Proceso> colaS;
     Proceso FinalS[] = new Proceso[10];
     Queue<Proceso> colaC;
     Proceso FinalC[] = new Proceso[10];
     Queue<Proceso> aux = new LinkedList<Proceso>();
     CardLayout layout;
     int nroS,nroC,datoS,datoC;
 public proyectoHRRN() {
     initComponents();
     layout = (CardLayout) pnlCards.getLayout();
     tablaE = new  String[10][100];
     tablaE1 = new  String[10][100];
     colaS = new LinkedList<Proceso>();
     colaC = new LinkedList<Proceso>();
     Proceso FinalS[] = new Proceso[10];
     Proceso FinalC[] = new Proceso[10]; 
     nroS = 0; nroC = 0; datoS = 0; datoC= 0;
     
    }//---------------------------------------------------------------------------------------
    private void resetear()
    {tablaE = new  String[10][100];
     tablaE1 = new  String[10][100];
     colaS = new LinkedList<Proceso>();
     colaC = new LinkedList<Proceso>();
     Proceso FinalS[] = new Proceso[10];
     Proceso FinalC[] = new Proceso[10]; 
     nroS = 0; nroC = 0; datoS = 0; datoC= 0;
    }
    //--------------------------------------------------------------------------------------
    private int HRRN_HLL(Queue<Proceso> cola,Proceso Final[],String [][]tablaE1,int datoC)
    {  aux = new LinkedList<Proceso>();
       Queue<Proceso> colaUS = new LinkedList<Proceso>();
       Proceso p = null; String q = "";
       int te = HLLCorto(cola);
       while(!cola.isEmpty())
        {  double may = 0;
           while(!cola.isEmpty())
           {    p = null; p = cola.poll();
                if (te >= p.getHLL()) 
                {   colaUS.add(p);
                    tablaE1[p.getPOS()][datoC] = (te-p.getHLL())+"";
                }
                else {aux.add(p);}
           }
           if(colaUS.isEmpty())
           { te = HLLCorto(aux); 
           }
           else{ datoC++;
                while(!colaUS.isEmpty())
                {    p = null; p = colaUS.poll();
                    double ta = tasaResp(te-p.getHLL(),p.getTC());
                    if (may < ta) 
                    { q = p.getNP();
                      may = ta;  
                    }
                    aux.add(p); 
                    tablaE1[p.getPOS()][datoC] = (double)Math.round(ta *  100d)/100 + "";
                }
                datoC++;
           }
           while(!aux.isEmpty())
           {    p = null; p = aux.poll();
                if(p.getNP().equals(q))
                { te = te + p.getTC();
                  p.setHS(te);
                  Final[p.getPOS()] = p;
                  tablaE1[p.getPOS()][datoC-1] = "["+(double)Math.round(may *  100d)/100+"]";
                }
                else {cola.add(p);
                }
           }
           
        }
       return datoC;
    }
    private double tasaResp(int TE,int TC)
    {   return (double)(TE+TC)/TC;
    }
    private static int HLLCorto(Queue<Proceso> cola)
    {   Queue<Proceso> aux = new LinkedList<Proceso>();
        Proceso p; int te = 0;
         p = null;
         p = cola.poll();
         te = p.getHLL();
         aux.add(p);
        while(!cola.isEmpty())
        {   p = null;
            p = cola.poll();
            if (te > p.getHLL()) 
            {   te = p.getHLL();   
            }
            aux.add(p); 
        }
        while(!aux.isEmpty())
        {   cola.add(aux.poll());
        }
        return te;  
    }
    private void llenarTablaTigoMoney(Proceso[]T, int C)
    {   DefaultTableModel model = (DefaultTableModel)tblUDTigo.getModel();
        DefaultTableModel model2 = (DefaultTableModel)tblUDTigo1.getModel();
        model.setRowCount(0);
        model2.setRowCount(0);
        
        Object data[] = new Object[20];
        double p1 = 0 ,p2 = 0, p3 = 0;
        for (int i = 0; i < C; i++) {
            data[0] = T[i].getNP();
            data[1] = T[i].getHLL();
            data[2] = T[i].getTC();
            data[3] = T[i].getHS();
            data[4] = T[i].getTS();
            data[5] = T[i].getTE();
            data[6] = (double)Math.round(T[i].getIS() *  100d)/100;
            model.addRow(data);
             //----PROMEDIO
            p1 = p1 + Double.parseDouble(T[i].getTS()+"");
            p2 = p2 + Double.parseDouble(T[i].getTE()+"");
            p3 = p3 + Double.parseDouble(T[i].getIS()+""); 
        }
        data[0] = "";
        data[1] = "";
        data[2] = "";
        data[3] = "";
        data[4] = "";
        data[5] = "";
        data[6] = "";
        model.addRow(data);
        data[0] = "";
        data[1] = "";
        data[2] = "";
        data[3] = "Promedio";
        data[4] = (double)Math.round(p1/C *  100d)/100;
        data[5] = (double)Math.round(p2/C *  100d)/100;
        data[6] = (double)Math.round(p3/C *  100d)/100;
        model.addRow(data);
        //-------------------------------------------------------
        data = new Object[20];
        for (int i = 0; i < C; i++) {
            for (int j = 0; j < 20; j++) 
            {  if (tablaE1[i][j]!=null) {
                    data[j] = tablaE1[i][j];
                }
              else data [j]= "";
            }
            model2.addRow(data);
        }     
    }

    private void llenarTablaRegistroDD(Proceso []T, int S)
    {  DefaultTableModel model = (DefaultTableModel)tblRDD.getModel();
       DefaultTableModel model1 = (DefaultTableModel)tblRDD1.getModel();
        model.setRowCount(0);
        model1.setRowCount(0);
        Object data[] = new Object[10];
        double p1 = 0 ,p2 = 0, p3 = 0;
        for (int i = 0; i < S; i++) {
            data[0] = T[i].getNP();
            data[1] = T[i].getHLL();
            data[2] = T[i].getTC();
            data[3] = T[i].getHS();
            data[4] = T[i].getTS();
            data[5] = T[i].getTE();
            data[6] = (double)Math.round(T[i].getIS() *  100d)/100;
            model.addRow(data);
             //----PROMEDIO
            p1 = p1 + T[i].getTS();
            p2 = p2 + T[i].getTE();
            p3 = p3 + T[i].getIS(); 
        }
        data[0] = "";
        data[1] = "";
        data[2] = "";
        data[3] = "";
        data[4] = "";
        data[5] = "";
        data[6] = "";
        model.addRow(data);
        data[0] = "";
        data[1] = "";
        data[2] = "";
        data[3] = "Promedio";
        data[4] = (double)Math.round(p1/S *  100d)/100;
        data[5] = (double)Math.round(p2/S *  100d)/100;
        data[6] = (double)Math.round(p3/S *  100d)/100;
        model.addRow(data);
        data = new Object[20];
        for (int i = 0; i < S; i++) {
            for (int j = 0; j < 20; j++) 
            {  if (tablaE[i][j]!=null) {
                    data[j] = tablaE[i][j];
                }
              else data [j]= "";
            }
            model1.addRow(data);
        }
    }
    



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        lblTitle1 = new javax.swing.JLabel();
        lblTitle2 = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        pnpMenuTitle = new javax.swing.JPanel();
        lblMenuTitle = new javax.swing.JLabel();
        btnM1 = new javax.swing.JButton();
        btnM3 = new javax.swing.JButton();
        btnM4 = new javax.swing.JButton();
        btnM5 = new javax.swing.JButton();
        btnSacaBT1 = new javax.swing.JButton();
        pnpMenuTitle1 = new javax.swing.JPanel();
        lblMenuTitle1 = new javax.swing.JLabel();
        pnlCards = new javax.swing.JPanel();
        pnlRegistroDelDia = new javax.swing.JPanel();
        pnlEmpleadosTitle1 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        btnMostarRDD = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRDD = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblRDD1 = new javax.swing.JTable();
        pnlEmpleadosTitle2 = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        pnlMostrarUsuariosTigoMoney = new javax.swing.JPanel();
        pnlEmpleadosTitle5 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        btnMostrarUDTigo = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblUDTigo = new javax.swing.JTable();
        pnlEmpleadosTitle3 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblUDTigo1 = new javax.swing.JTable();
        pnlCrearTigoMoney = new javax.swing.JPanel();
        txtCTMN = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        btnAceptarCTM = new javax.swing.JButton();
        jLabel35 = new javax.swing.JLabel();
        txtCTMM = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        txtCTMCEL = new javax.swing.JTextField();
        txtCTMN1 = new javax.swing.JTextField();
        txtCTMN2 = new javax.swing.JTextField();
        txtCTMN3 = new javax.swing.JTextField();
        txtCTMN4 = new javax.swing.JTextField();
        txtCTMN5 = new javax.swing.JTextField();
        txtCTMN6 = new javax.swing.JTextField();
        txtCTMN7 = new javax.swing.JTextField();
        txtCTMM1 = new javax.swing.JTextField();
        txtCTMM2 = new javax.swing.JTextField();
        txtCTMM3 = new javax.swing.JTextField();
        txtCTMM4 = new javax.swing.JTextField();
        txtCTMM5 = new javax.swing.JTextField();
        txtCTMM6 = new javax.swing.JTextField();
        txtCTMM7 = new javax.swing.JTextField();
        txtCTMCEL1 = new javax.swing.JTextField();
        txtCTMCEL2 = new javax.swing.JTextField();
        txtCTMCEL3 = new javax.swing.JTextField();
        txtCTMCEL4 = new javax.swing.JTextField();
        txtCTMCEL5 = new javax.swing.JTextField();
        txtCTMCEL6 = new javax.swing.JTextField();
        txtCTMCEL7 = new javax.swing.JTextField();
        txtCTMN8 = new javax.swing.JTextField();
        txtCTMM8 = new javax.swing.JTextField();
        txtCTMCEL8 = new javax.swing.JTextField();
        pnlRecargaTarjeta = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        txtCiRTA = new javax.swing.JTextField();
        btnAceptarRTA = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtMontoRTA = new javax.swing.JTextField();
        txtCiRTA1 = new javax.swing.JTextField();
        txtCiRTA2 = new javax.swing.JTextField();
        txtCiRTA3 = new javax.swing.JTextField();
        txtCiRTA4 = new javax.swing.JTextField();
        txtCiRTA5 = new javax.swing.JTextField();
        txtCiRTA6 = new javax.swing.JTextField();
        txtCiRTA7 = new javax.swing.JTextField();
        txtMontoRTA1 = new javax.swing.JTextField();
        txtMontoRTA2 = new javax.swing.JTextField();
        txtMontoRTA3 = new javax.swing.JTextField();
        txtMontoRTA4 = new javax.swing.JTextField();
        txtMontoRTA5 = new javax.swing.JTextField();
        txtMontoRTA6 = new javax.swing.JTextField();
        txtMontoRTA7 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        txtCiRTA8 = new javax.swing.JTextField();
        txtMontoRTA8 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);

        pnlHeader.setBackground(new java.awt.Color(0, 0, 0));

        lblTitle.setFont(new java.awt.Font("DejaVu Sans", 1, 36)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle.setText("PROCESO (HRRN)");

        lblTitle1.setFont(new java.awt.Font("DejaVu Sans", 1, 36)); // NOI18N
        lblTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle1.setText(" TASA DE RESPUESTA MÁS ALTA SIGUIENTE");

        lblTitle2.setFont(new java.awt.Font("DejaVu Sans", 1, 36)); // NOI18N
        lblTitle2.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTitle2.setText("Mario Oliver Apaza Huanca");

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(518, 518, 518)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                .addContainerGap(410, Short.MAX_VALUE)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTitle1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlHeaderLayout.createSequentialGroup()
                        .addComponent(lblTitle2)
                        .addGap(194, 194, 194)))
                .addGap(298, 298, 298))
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTitle1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTitle2)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pnlMenu.setBackground(new java.awt.Color(255, 102, 51));

        pnpMenuTitle.setBackground(new java.awt.Color(0, 0, 0));

        lblMenuTitle.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        lblMenuTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMenuTitle.setText("SIN HORA LL.");

        javax.swing.GroupLayout pnpMenuTitleLayout = new javax.swing.GroupLayout(pnpMenuTitle);
        pnpMenuTitle.setLayout(pnpMenuTitleLayout);
        pnpMenuTitleLayout.setHorizontalGroup(
            pnpMenuTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMenuTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnpMenuTitleLayout.setVerticalGroup(
            pnpMenuTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnpMenuTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMenuTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnM1.setBackground(new java.awt.Color(255, 102, 102));
        btnM1.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        btnM1.setText("MOSTRAR");
        btnM1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnM1MouseClicked(evt);
            }
        });
        btnM1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM1ActionPerformed(evt);
            }
        });

        btnM3.setBackground(new java.awt.Color(255, 51, 255));
        btnM3.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        btnM3.setText("RESETEAR");
        btnM3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnM3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnM3MouseEntered(evt);
            }
        });
        btnM3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM3ActionPerformed(evt);
            }
        });

        btnM4.setBackground(new java.awt.Color(255, 102, 102));
        btnM4.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        btnM4.setText("MOSTRAR");
        btnM4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnM4MouseClicked(evt);
            }
        });
        btnM4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM4ActionPerformed(evt);
            }
        });

        btnM5.setBackground(new java.awt.Color(255, 102, 102));
        btnM5.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        btnM5.setText("LLENAR");
        btnM5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnM5MouseClicked(evt);
            }
        });
        btnM5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnM5ActionPerformed(evt);
            }
        });

        btnSacaBT1.setBackground(new java.awt.Color(255, 102, 102));
        btnSacaBT1.setFont(new java.awt.Font("DejaVu Sans", 1, 14)); // NOI18N
        btnSacaBT1.setText("LLENAR");
        btnSacaBT1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSacaBT1MouseClicked(evt);
            }
        });
        btnSacaBT1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSacaBT1ActionPerformed(evt);
            }
        });

        pnpMenuTitle1.setBackground(new java.awt.Color(0, 0, 0));

        lblMenuTitle1.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        lblMenuTitle1.setForeground(new java.awt.Color(255, 255, 255));
        lblMenuTitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMenuTitle1.setText("CON HORA LL.");

        javax.swing.GroupLayout pnpMenuTitle1Layout = new javax.swing.GroupLayout(pnpMenuTitle1);
        pnpMenuTitle1.setLayout(pnpMenuTitle1Layout);
        pnpMenuTitle1Layout.setHorizontalGroup(
            pnpMenuTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMenuTitle1, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
        );
        pnpMenuTitle1Layout.setVerticalGroup(
            pnpMenuTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnpMenuTitle1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblMenuTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnM3, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btnM4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnM5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnpMenuTitle1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnpMenuTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSacaBT1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnM1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(pnpMenuTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSacaBT1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnM1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnpMenuTitle1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnM5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnM4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnM3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlCards.setBackground(new java.awt.Color(0, 0, 0));
        pnlCards.setLayout(new java.awt.CardLayout());

        pnlRegistroDelDia.setBackground(new java.awt.Color(204, 204, 255));
        pnlRegistroDelDia.setLayout(null);

        pnlEmpleadosTitle1.setBackground(new java.awt.Color(0, 0, 0));
        pnlEmpleadosTitle1.setForeground(new java.awt.Color(255, 255, 255));

        jLabel31.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(255, 255, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("Hora de llegada || Tasa de Respuesta");

        javax.swing.GroupLayout pnlEmpleadosTitle1Layout = new javax.swing.GroupLayout(pnlEmpleadosTitle1);
        pnlEmpleadosTitle1.setLayout(pnlEmpleadosTitle1Layout);
        pnlEmpleadosTitle1Layout.setHorizontalGroup(
            pnlEmpleadosTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEmpleadosTitle1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlEmpleadosTitle1Layout.setVerticalGroup(
            pnlEmpleadosTitle1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEmpleadosTitle1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlRegistroDelDia.add(pnlEmpleadosTitle1);
        pnlEmpleadosTitle1.setBounds(480, 400, 400, 80);

        btnMostarRDD.setBackground(new java.awt.Color(0, 0, 0));
        btnMostarRDD.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMostarRDD.setForeground(new java.awt.Color(153, 153, 153));
        btnMostarRDD.setText("<ACTUALIZAR>");
        btnMostarRDD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMostarRDDMouseClicked(evt);
            }
        });
        btnMostarRDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostarRDDActionPerformed(evt);
            }
        });
        pnlRegistroDelDia.add(btnMostarRDD);
        btnMostarRDD.setBounds(40, 60, 177, 31);

        tblRDD.setBackground(new java.awt.Color(0, 0, 0));
        tblRDD.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 18)); // NOI18N
        tblRDD.setForeground(new java.awt.Color(255, 255, 255));
        tblRDD.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PROCESO", "HLL", "TC", "HS", "TS", "TE", "IS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblRDD);

        pnlRegistroDelDia.add(jScrollPane2);
        jScrollPane2.setBounds(40, 130, 1220, 220);

        tblRDD1.setBackground(new java.awt.Color(0, 0, 0));
        tblRDD1.setFont(new java.awt.Font("Yu Gothic UI Semilight", 0, 18)); // NOI18N
        tblRDD1.setForeground(new java.awt.Color(255, 255, 255));
        tblRDD1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRO.", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblRDD1);

        pnlRegistroDelDia.add(jScrollPane3);
        jScrollPane3.setBounds(20, 520, 1290, 220);

        pnlEmpleadosTitle2.setBackground(new java.awt.Color(0, 0, 0));
        pnlEmpleadosTitle2.setForeground(new java.awt.Color(255, 255, 255));

        jLabel32.setFont(new java.awt.Font("DejaVu Sans", 1, 48)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("HRRN (HLL = 0)");

        javax.swing.GroupLayout pnlEmpleadosTitle2Layout = new javax.swing.GroupLayout(pnlEmpleadosTitle2);
        pnlEmpleadosTitle2.setLayout(pnlEmpleadosTitle2Layout);
        pnlEmpleadosTitle2Layout.setHorizontalGroup(
            pnlEmpleadosTitle2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEmpleadosTitle2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel32, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlEmpleadosTitle2Layout.setVerticalGroup(
            pnlEmpleadosTitle2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEmpleadosTitle2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel32, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlRegistroDelDia.add(pnlEmpleadosTitle2);
        pnlEmpleadosTitle2.setBounds(490, 20, 400, 80);

        pnlCards.add(pnlRegistroDelDia, "crdMostrarRegistroDD");

        pnlMostrarUsuariosTigoMoney.setBackground(new java.awt.Color(204, 204, 255));

        pnlEmpleadosTitle5.setBackground(new java.awt.Color(0, 0, 0));

        jLabel36.setFont(new java.awt.Font("DejaVu Sans", 1, 48)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("HRRN CON HLL");

        javax.swing.GroupLayout pnlEmpleadosTitle5Layout = new javax.swing.GroupLayout(pnlEmpleadosTitle5);
        pnlEmpleadosTitle5.setLayout(pnlEmpleadosTitle5Layout);
        pnlEmpleadosTitle5Layout.setHorizontalGroup(
            pnlEmpleadosTitle5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEmpleadosTitle5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(247, 247, 247))
        );
        pnlEmpleadosTitle5Layout.setVerticalGroup(
            pnlEmpleadosTitle5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEmpleadosTitle5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel36, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnMostrarUDTigo.setBackground(new java.awt.Color(0, 0, 0));
        btnMostrarUDTigo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        btnMostrarUDTigo.setForeground(new java.awt.Color(153, 153, 153));
        btnMostrarUDTigo.setText("<ACTUALIZAR>");
        btnMostrarUDTigo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnMostrarUDTigoMouseClicked(evt);
            }
        });
        btnMostrarUDTigo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMostrarUDTigoActionPerformed(evt);
            }
        });

        tblUDTigo.setBackground(new java.awt.Color(0, 0, 0));
        tblUDTigo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        tblUDTigo.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 18)); // NOI18N
        tblUDTigo.setForeground(new java.awt.Color(255, 255, 255));
        tblUDTigo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PROCESO", "HLL", "TC", "HS", "TS", "TE", "IS"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tblUDTigo);

        pnlEmpleadosTitle3.setBackground(new java.awt.Color(0, 0, 0));
        pnlEmpleadosTitle3.setForeground(new java.awt.Color(255, 255, 255));

        jLabel34.setFont(new java.awt.Font("DejaVu Sans", 1, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(255, 255, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Hora de llegada || Tasa de Respuesta");

        javax.swing.GroupLayout pnlEmpleadosTitle3Layout = new javax.swing.GroupLayout(pnlEmpleadosTitle3);
        pnlEmpleadosTitle3.setLayout(pnlEmpleadosTitle3Layout);
        pnlEmpleadosTitle3Layout.setHorizontalGroup(
            pnlEmpleadosTitle3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlEmpleadosTitle3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlEmpleadosTitle3Layout.setVerticalGroup(
            pnlEmpleadosTitle3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlEmpleadosTitle3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel34, javax.swing.GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                .addContainerGap())
        );

        tblUDTigo1.setBackground(new java.awt.Color(0, 0, 0));
        tblUDTigo1.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 14)); // NOI18N
        tblUDTigo1.setForeground(new java.awt.Color(255, 255, 255));
        tblUDTigo1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PRO.", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE", "TE", "RE"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblUDTigo1);

        javax.swing.GroupLayout pnlMostrarUsuariosTigoMoneyLayout = new javax.swing.GroupLayout(pnlMostrarUsuariosTigoMoney);
        pnlMostrarUsuariosTigoMoney.setLayout(pnlMostrarUsuariosTigoMoneyLayout);
        pnlMostrarUsuariosTigoMoneyLayout.setHorizontalGroup(
            pnlMostrarUsuariosTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMostrarUsuariosTigoMoneyLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(pnlMostrarUsuariosTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMostrarUsuariosTigoMoneyLayout.createSequentialGroup()
                        .addComponent(jScrollPane6)
                        .addGap(65, 65, 65))
                    .addGroup(pnlMostrarUsuariosTigoMoneyLayout.createSequentialGroup()
                        .addComponent(btnMostrarUDTigo)
                        .addGap(233, 233, 233)
                        .addComponent(pnlEmpleadosTitle5, javax.swing.GroupLayout.PREFERRED_SIZE, 434, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMostrarUsuariosTigoMoneyLayout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(pnlMostrarUsuariosTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMostrarUsuariosTigoMoneyLayout.createSequentialGroup()
                        .addComponent(pnlEmpleadosTitle3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(447, 447, 447))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMostrarUsuariosTigoMoneyLayout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1276, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(23, 23, 23))))
        );
        pnlMostrarUsuariosTigoMoneyLayout.setVerticalGroup(
            pnlMostrarUsuariosTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMostrarUsuariosTigoMoneyLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlMostrarUsuariosTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnlEmpleadosTitle5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMostrarUDTigo))
                .addGap(33, 33, 33)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addComponent(pnlEmpleadosTitle3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45))
        );

        pnlCards.add(pnlMostrarUsuariosTigoMoney, "crdUsuarioTM");

        pnlCrearTigoMoney.setBackground(new java.awt.Color(102, 102, 102));

        txtCTMN.setEditable(false);
        txtCTMN.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMN.setText("A");
        txtCTMN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMNKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("PROCESO:");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(255, 0, 0));
        jLabel33.setText("HRRN HLL DIFERENTE ");

        btnAceptarCTM.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnAceptarCTM.setText("ACEPTAR");
        btnAceptarCTM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptarCTMMouseClicked(evt);
            }
        });
        btnAceptarCTM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarCTMActionPerformed(evt);
            }
        });

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("HORA DE LLEGADA:");

        txtCTMM.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMM.setText("23");
        txtCTMM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMMKeyTyped(evt);
            }
        });

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(255, 255, 255));
        jLabel38.setText("TIEMPO DE CORRIDO:");

        txtCTMCEL.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMCEL.setText("2");
        txtCTMCEL.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMCELKeyTyped(evt);
            }
        });

        txtCTMN1.setEditable(false);
        txtCTMN1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMN1.setText("B");
        txtCTMN1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMN1KeyTyped(evt);
            }
        });

        txtCTMN2.setEditable(false);
        txtCTMN2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMN2.setText("C");
        txtCTMN2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMN2KeyTyped(evt);
            }
        });

        txtCTMN3.setEditable(false);
        txtCTMN3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMN3.setText("D");
        txtCTMN3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMN3KeyTyped(evt);
            }
        });

        txtCTMN4.setEditable(false);
        txtCTMN4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMN4.setText("E");
        txtCTMN4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMN4KeyTyped(evt);
            }
        });

        txtCTMN5.setEditable(false);
        txtCTMN5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMN5.setText("F");
        txtCTMN5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMN5KeyTyped(evt);
            }
        });

        txtCTMN6.setEditable(false);
        txtCTMN6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMN6.setText("G");
        txtCTMN6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMN6KeyTyped(evt);
            }
        });

        txtCTMN7.setEditable(false);
        txtCTMN7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMN7.setText("H");
        txtCTMN7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMN7KeyTyped(evt);
            }
        });

        txtCTMM1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMM1.setText("0");
        txtCTMM1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMM1KeyTyped(evt);
            }
        });

        txtCTMM2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMM2.setText("19");
        txtCTMM2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMM2KeyTyped(evt);
            }
        });

        txtCTMM3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMM3.setText("5");
        txtCTMM3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMM3KeyTyped(evt);
            }
        });

        txtCTMM4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMM4.setText("9");
        txtCTMM4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMM4KeyTyped(evt);
            }
        });

        txtCTMM5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMM5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMM5KeyTyped(evt);
            }
        });

        txtCTMM6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMM6.setText("13");
        txtCTMM6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMM6KeyTyped(evt);
            }
        });

        txtCTMM7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMM7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMM7KeyTyped(evt);
            }
        });

        txtCTMCEL1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMCEL1.setText("12");
        txtCTMCEL1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMCEL1KeyTyped(evt);
            }
        });

        txtCTMCEL2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMCEL2.setText("4");
        txtCTMCEL2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMCEL2KeyTyped(evt);
            }
        });

        txtCTMCEL3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMCEL3.setText("6");
        txtCTMCEL3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMCEL3KeyTyped(evt);
            }
        });

        txtCTMCEL4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMCEL4.setText("8");
        txtCTMCEL4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMCEL4KeyTyped(evt);
            }
        });

        txtCTMCEL5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMCEL5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMCEL5KeyTyped(evt);
            }
        });

        txtCTMCEL6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMCEL6.setText("10");
        txtCTMCEL6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMCEL6KeyTyped(evt);
            }
        });

        txtCTMCEL7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMCEL7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMCEL7KeyTyped(evt);
            }
        });

        txtCTMN8.setEditable(false);
        txtCTMN8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMN8.setText("I");
        txtCTMN8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMN8KeyTyped(evt);
            }
        });

        txtCTMM8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMM8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMM8KeyTyped(evt);
            }
        });

        txtCTMCEL8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCTMCEL8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCTMCEL8KeyTyped(evt);
            }
        });

        javax.swing.GroupLayout pnlCrearTigoMoneyLayout = new javax.swing.GroupLayout(pnlCrearTigoMoney);
        pnlCrearTigoMoney.setLayout(pnlCrearTigoMoneyLayout);
        pnlCrearTigoMoneyLayout.setHorizontalGroup(
            pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearTigoMoneyLayout.createSequentialGroup()
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrearTigoMoneyLayout.createSequentialGroup()
                        .addGap(469, 469, 469)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 264, Short.MAX_VALUE)
                        .addComponent(jLabel38))
                    .addGroup(pnlCrearTigoMoneyLayout.createSequentialGroup()
                        .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlCrearTigoMoneyLayout.createSequentialGroup()
                                .addGap(372, 372, 372)
                                .addComponent(jLabel33))
                            .addGroup(pnlCrearTigoMoneyLayout.createSequentialGroup()
                                .addGap(499, 499, 499)
                                .addComponent(btnAceptarCTM)))
                        .addGap(0, 448, Short.MAX_VALUE)))
                .addGap(89, 89, 89))
            .addGroup(pnlCrearTigoMoneyLayout.createSequentialGroup()
                .addGap(165, 165, 165)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCTMN6, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMN5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMN4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMN3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMN2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMN1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMN, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(txtCTMN7, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMN8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(223, 223, 223)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtCTMM, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                    .addComponent(txtCTMM1)
                    .addComponent(txtCTMM2)
                    .addComponent(txtCTMM3)
                    .addComponent(txtCTMM4)
                    .addComponent(txtCTMM5)
                    .addComponent(txtCTMM6)
                    .addComponent(txtCTMM7)
                    .addComponent(txtCTMM8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtCTMCEL, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                        .addComponent(txtCTMCEL1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtCTMCEL2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtCTMCEL3, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtCTMCEL4, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(txtCTMCEL8, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(txtCTMCEL5, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMCEL6, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMCEL7, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(159, 159, 159))
        );
        pnlCrearTigoMoneyLayout.setVerticalGroup(
            pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCrearTigoMoneyLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel33)
                .addGap(23, 23, 23)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel35)
                    .addComponent(jLabel38))
                .addGap(18, 18, 18)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCTMN, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMM, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMCEL, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCTMN1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMM1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMCEL1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCTMN2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMM2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMCEL2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCTMN3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMM3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMCEL3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCTMN4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMM4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMCEL4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCTMN5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMM5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMCEL5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCTMN6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMM6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMCEL6, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCTMN7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMM7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCTMCEL7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlCrearTigoMoneyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCTMN8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCTMM8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCTMCEL8, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnAceptarCTM)
                .addGap(36, 36, 36))
        );

        pnlCards.add(pnlCrearTigoMoney, "crdCTMoney");

        pnlRecargaTarjeta.setBackground(new java.awt.Color(102, 102, 102));
        pnlRecargaTarjeta.setLayout(null);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 255));
        jLabel11.setText("HRRN HLL = 0");
        pnlRecargaTarjeta.add(jLabel11);
        jLabel11.setBounds(450, 20, 310, 44);

        txtCiRTA.setEditable(false);
        txtCiRTA.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCiRTA.setText("A");
        txtCiRTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiRTAActionPerformed(evt);
            }
        });
        txtCiRTA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCiRTAKeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtCiRTA);
        txtCiRTA.setBounds(230, 140, 100, 44);

        btnAceptarRTA.setBackground(new java.awt.Color(255, 255, 255));
        btnAceptarRTA.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        btnAceptarRTA.setText("ACEPTAR");
        btnAceptarRTA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAceptarRTAMouseClicked(evt);
            }
        });
        btnAceptarRTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarRTAActionPerformed(evt);
            }
        });
        pnlRecargaTarjeta.add(btnAceptarRTA);
        btnAceptarRTA.setBounds(500, 720, 181, 53);

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 255, 204));
        jLabel13.setText("TIEMPO DE CORRIDA");
        pnlRecargaTarjeta.add(jLabel13);
        jLabel13.setBounds(710, 80, 258, 29);

        txtMontoRTA.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMontoRTA.setText("8");
        txtMontoRTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoRTAActionPerformed(evt);
            }
        });
        txtMontoRTA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoRTAKeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtMontoRTA);
        txtMontoRTA.setBounds(730, 140, 210, 45);

        txtCiRTA1.setEditable(false);
        txtCiRTA1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCiRTA1.setText("B");
        txtCiRTA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiRTA1ActionPerformed(evt);
            }
        });
        txtCiRTA1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCiRTA1KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtCiRTA1);
        txtCiRTA1.setBounds(230, 200, 100, 44);

        txtCiRTA2.setEditable(false);
        txtCiRTA2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCiRTA2.setText("C");
        txtCiRTA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiRTA2ActionPerformed(evt);
            }
        });
        txtCiRTA2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCiRTA2KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtCiRTA2);
        txtCiRTA2.setBounds(230, 260, 100, 44);

        txtCiRTA3.setEditable(false);
        txtCiRTA3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCiRTA3.setText("D");
        txtCiRTA3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiRTA3ActionPerformed(evt);
            }
        });
        txtCiRTA3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCiRTA3KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtCiRTA3);
        txtCiRTA3.setBounds(230, 320, 100, 44);

        txtCiRTA4.setEditable(false);
        txtCiRTA4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCiRTA4.setText("E");
        txtCiRTA4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiRTA4ActionPerformed(evt);
            }
        });
        txtCiRTA4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCiRTA4KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtCiRTA4);
        txtCiRTA4.setBounds(230, 380, 100, 44);

        txtCiRTA5.setEditable(false);
        txtCiRTA5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCiRTA5.setText("F");
        txtCiRTA5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiRTA5ActionPerformed(evt);
            }
        });
        txtCiRTA5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCiRTA5KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtCiRTA5);
        txtCiRTA5.setBounds(230, 440, 100, 44);

        txtCiRTA6.setEditable(false);
        txtCiRTA6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCiRTA6.setText("G");
        txtCiRTA6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiRTA6ActionPerformed(evt);
            }
        });
        txtCiRTA6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCiRTA6KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtCiRTA6);
        txtCiRTA6.setBounds(230, 500, 100, 44);

        txtCiRTA7.setEditable(false);
        txtCiRTA7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCiRTA7.setText("H");
        txtCiRTA7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiRTA7ActionPerformed(evt);
            }
        });
        txtCiRTA7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCiRTA7KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtCiRTA7);
        txtCiRTA7.setBounds(230, 560, 100, 44);

        txtMontoRTA1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMontoRTA1.setText("6");
        txtMontoRTA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoRTA1ActionPerformed(evt);
            }
        });
        txtMontoRTA1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoRTA1KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtMontoRTA1);
        txtMontoRTA1.setBounds(730, 200, 210, 45);

        txtMontoRTA2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMontoRTA2.setText("4");
        txtMontoRTA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoRTA2ActionPerformed(evt);
            }
        });
        txtMontoRTA2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoRTA2KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtMontoRTA2);
        txtMontoRTA2.setBounds(730, 260, 210, 45);

        txtMontoRTA3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMontoRTA3.setText("2");
        txtMontoRTA3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoRTA3ActionPerformed(evt);
            }
        });
        txtMontoRTA3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoRTA3KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtMontoRTA3);
        txtMontoRTA3.setBounds(730, 320, 210, 45);

        txtMontoRTA4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMontoRTA4.setText("6");
        txtMontoRTA4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoRTA4ActionPerformed(evt);
            }
        });
        txtMontoRTA4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoRTA4KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtMontoRTA4);
        txtMontoRTA4.setBounds(730, 380, 210, 45);

        txtMontoRTA5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMontoRTA5.setText("10");
        txtMontoRTA5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoRTA5ActionPerformed(evt);
            }
        });
        txtMontoRTA5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoRTA5KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtMontoRTA5);
        txtMontoRTA5.setBounds(730, 440, 210, 45);

        txtMontoRTA6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMontoRTA6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoRTA6ActionPerformed(evt);
            }
        });
        txtMontoRTA6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoRTA6KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtMontoRTA6);
        txtMontoRTA6.setBounds(730, 500, 210, 45);

        txtMontoRTA7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMontoRTA7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoRTA7ActionPerformed(evt);
            }
        });
        txtMontoRTA7.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoRTA7KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtMontoRTA7);
        txtMontoRTA7.setBounds(730, 560, 210, 45);

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 255, 204));
        jLabel12.setText("PROCESO");
        pnlRecargaTarjeta.add(jLabel12);
        jLabel12.setBounds(220, 80, 120, 29);

        txtCiRTA8.setEditable(false);
        txtCiRTA8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtCiRTA8.setText("I");
        txtCiRTA8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCiRTA8ActionPerformed(evt);
            }
        });
        txtCiRTA8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCiRTA8KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtCiRTA8);
        txtCiRTA8.setBounds(230, 620, 100, 44);

        txtMontoRTA8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        txtMontoRTA8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMontoRTA8ActionPerformed(evt);
            }
        });
        txtMontoRTA8.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMontoRTA8KeyTyped(evt);
            }
        });
        pnlRecargaTarjeta.add(txtMontoRTA8);
        txtMontoRTA8.setBounds(730, 620, 210, 45);

        pnlCards.add(pnlRecargaTarjeta, "crdRecar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlCards, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlCards, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtCiRTAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiRTAKeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }
    }//GEN-LAST:event_txtCiRTAKeyTyped

    private void txtCiRTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiRTAActionPerformed

    }//GEN-LAST:event_txtCiRTAActionPerformed

    private void txtMontoRTAKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoRTAKeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }
    }//GEN-LAST:event_txtMontoRTAKeyTyped

    private void btnAceptarRTAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarRTAMouseClicked
        Proceso p; int dato;int tc = 0;
        resetear();
        if (!txtMontoRTA.getText().equals("")&&!txtMontoRTA.getText().equals("0")) 
        {   dato = Integer.parseInt(txtMontoRTA.getText());
            p = new Proceso("A",dato,tc);
            this.tablaE[tc][this.datoS] = "A";
            tc++;
            colaS.add(p);
            
        }
        if (!txtMontoRTA1.getText().equals("")&&!txtMontoRTA1.getText().equals("0")) 
        {   dato = Integer.parseInt(txtMontoRTA1.getText());
            p = new Proceso("B",dato,tc);
            this.tablaE[tc][this.datoS] = "B";
            tc++;
            colaS.add(p);
        }
        if (!txtMontoRTA2.getText().equals("")&&!txtMontoRTA2.getText().equals("0")) 
        {   dato = Integer.parseInt(txtMontoRTA2.getText());
            p = new Proceso("C",dato,tc);
            this.tablaE[tc][this.datoS] = "C";
            tc++;
            colaS.add(p);
        }
        if (!txtMontoRTA3.getText().equals("")&&!txtMontoRTA3.getText().equals("0")) 
        {   dato = Integer.parseInt(txtMontoRTA3.getText());
            p = new Proceso("D",dato,tc);
            this.tablaE[tc][this.datoS] = "D";
            tc++;
            colaS.add(p);
        }
        if (!txtMontoRTA4.getText().equals("")&&!txtMontoRTA4.getText().equals("0")) 
        {   dato = Integer.parseInt(txtMontoRTA4.getText());
            p = new Proceso("E",dato,tc);
            this.tablaE[tc][this.datoS] = "E";
            tc++;
            colaS.add(p);
        }
        if (!txtMontoRTA5.getText().equals("")&&!txtMontoRTA5.getText().equals("0")) 
        {   dato = Integer.parseInt(txtMontoRTA5.getText());
            p = new Proceso("F",dato,tc);
            this.tablaE[tc][this.datoS] = "F";
            tc++;
            colaS.add(p);
        }
        if (!txtMontoRTA6.getText().equals("")&&!txtMontoRTA6.getText().equals("0")) 
        {   dato = Integer.parseInt(txtMontoRTA6.getText());
            p = new Proceso("G",dato,tc);
            this.tablaE[tc][this.datoS] = "G";
            tc++;
            colaS.add(p);
        }
        if (!txtMontoRTA7.getText().equals("")&&!txtMontoRTA7.getText().equals("0")) 
        {   dato = Integer.parseInt(txtMontoRTA7.getText());
            p = new Proceso("H",dato,tc);
            this.tablaE[tc][this.datoS] = "H";
            tc++;;
            colaS.add(p);
        }
        if (!txtMontoRTA8.getText().equals("")&&!txtMontoRTA8.getText().equals("0")) 
        {   dato = Integer.parseInt(txtMontoRTA8.getText());
            p = new Proceso("I",dato,tc);
            this.tablaE[tc][this.datoS] = "I";
            tc++;
            colaS.add(p);
        }
        this.nroS = tc;
        txtMontoRTA.setText(""); txtMontoRTA1.setText(""); txtMontoRTA2.setText(""); txtMontoRTA3.setText("");
        txtMontoRTA4.setText("");txtMontoRTA5.setText(""); txtMontoRTA6.setText(""); txtMontoRTA7.setText("");
        txtMontoRTA8.setText("");
        this.datoS++;
        this.datoS = HRRN_HLL(colaS,FinalS,this.tablaE,this.datoS);  
    }//GEN-LAST:event_btnAceptarRTAMouseClicked

    private void btnAceptarRTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarRTAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarRTAActionPerformed

    private void btnMostarRDDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMostarRDDMouseClicked
        llenarTablaRegistroDD(FinalS,nroS);  
        
        
        
        
    }//GEN-LAST:event_btnMostarRDDMouseClicked

    private void btnMostarRDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostarRDDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMostarRDDActionPerformed

    private void btnM1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnM1MouseClicked
        layout.show(pnlCards, "crdMostrarRegistroDD");  
    }//GEN-LAST:event_btnM1MouseClicked

    private void btnM1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnM1ActionPerformed

    private void btnM3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnM3MouseClicked
     
     resetear();
           
    }//GEN-LAST:event_btnM3MouseClicked

    private void btnM3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnM3ActionPerformed

    private void btnM4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnM4MouseClicked
        layout.show(pnlCards, "crdUsuarioTM"); 
    }//GEN-LAST:event_btnM4MouseClicked

    private void btnM4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM4ActionPerformed
       layout.show(pnlCards, "crdMostrarUTM"); 
    }//GEN-LAST:event_btnM4ActionPerformed

    private void btnMostrarUDTigoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMostrarUDTigoMouseClicked
        llenarTablaTigoMoney(FinalC,nroC);    
    }//GEN-LAST:event_btnMostrarUDTigoMouseClicked

    private void btnMostrarUDTigoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMostrarUDTigoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMostrarUDTigoActionPerformed

    private void btnM5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnM5MouseClicked
        layout.show(pnlCards, "crdCTMoney");
    }//GEN-LAST:event_btnM5MouseClicked

    private void btnM5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnM5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnM5ActionPerformed

    private void txtCTMNKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMNKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMNKeyTyped

    private void btnAceptarCTMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAceptarCTMMouseClicked
      Proceso p; int tc = 0; int dato1; int dato;
      resetear();
        if (!txtCTMM.getText().equals("")&&!txtCTMCEL.getText().equals("")&&!txtCTMCEL.getText().equals("0")) 
        {   dato = Integer.parseInt(txtCTMM.getText());
            dato1 = Integer.parseInt(txtCTMCEL.getText());
            p = new Proceso("A",dato,dato1,tc);
            colaC.add(p);
            this.tablaE1[tc][this.datoC] = "A";
            tc++;
        }
        if (!txtCTMM1.getText().equals("")&&!txtCTMCEL1.getText().equals("")&&!txtCTMCEL1.getText().equals("0")) 
        {   dato = Integer.parseInt(txtCTMM1.getText());
            dato1 = Integer.parseInt(txtCTMCEL1.getText());
            p = new Proceso("B",dato,dato1,tc);
            colaC.add(p);
            this.tablaE1[tc][this.datoC] = "B";
            tc++;
        }
        if (!txtCTMM2.getText().equals("")&&!txtCTMCEL2.getText().equals("")&&!txtCTMCEL2.getText().equals("0")) 
        {   dato = Integer.parseInt(txtCTMM2.getText());
            dato1 = Integer.parseInt(txtCTMCEL2.getText());
            p = new Proceso("C",dato,dato1,tc);
            colaC.add(p);
            this.tablaE1[tc][this.datoC] = "C";
            tc++;
        }
        if (!txtCTMM3.getText().equals("")&&!txtCTMCEL3.getText().equals("")&&!txtCTMCEL3.getText().equals("0")) 
        {   dato = Integer.parseInt(txtCTMM3.getText());
            dato1 = Integer.parseInt(txtCTMCEL3.getText());
            p = new Proceso("D",dato,dato1,tc);
            colaC.add(p);
            this.tablaE1[tc][this.datoC] = "D";
            tc++;
        }
        if (!txtCTMM4.getText().equals("")&&!txtCTMCEL4.getText().equals("")&&!txtCTMCEL4.getText().equals("0")) 
        {   dato = Integer.parseInt(txtCTMM4.getText());
            dato1 = Integer.parseInt(txtCTMCEL4.getText());
            p = new Proceso("E",dato,dato1,tc);
            colaC.add(p);
            this.tablaE1[tc][this.datoC] = "E";
            tc++;
        }
        if (!txtCTMM5.getText().equals("")&&!txtCTMCEL5.getText().equals("")&&!txtCTMCEL5.getText().equals("0")) 
        {   dato = Integer.parseInt(txtCTMM5.getText());
            dato1 = Integer.parseInt(txtCTMCEL5.getText());
            p = new Proceso("F",dato,dato1,tc);
            colaC.add(p);
            this.tablaE1[tc][this.datoC] = "F";
            tc++;
        }
        if (!txtCTMM6.getText().equals("")&&!txtCTMCEL6.getText().equals("")&&!txtCTMCEL6.getText().equals("0")) 
        {   dato = Integer.parseInt(txtCTMM6.getText());
            dato1 = Integer.parseInt(txtCTMCEL6.getText());
            p = new Proceso("G",dato,dato1,tc);
            colaC.add(p);
            this.tablaE1[tc][this.datoC] = "G";
            tc++;
        }
        if (!txtCTMM7.getText().equals("")&&!txtCTMCEL7.getText().equals("")&&!txtCTMCEL7.getText().equals("0")) 
        {   dato = Integer.parseInt(txtCTMM7.getText());
            dato1 = Integer.parseInt(txtCTMCEL7.getText());
            p = new Proceso("H",dato,dato1,tc);
            colaC.add(p);
            this.tablaE1[tc][this.datoC] = "H";
            tc++;
        }
        if (!txtCTMM8.getText().equals("")&&!txtCTMCEL8.getText().equals("")&&!txtCTMCEL8.getText().equals("0")) 
        {   dato = Integer.parseInt(txtCTMM8.getText());
            dato1 = Integer.parseInt(txtCTMCEL8.getText());
            p = new Proceso("I",dato,dato1,tc);
            colaC.add(p);
            this.tablaE1[tc][this.datoC] = "I";
            tc++;
        }
        nroC = tc;
        txtCTMM.setText("");
        txtCTMCEL.setText("");
        txtCTMM1.setText("");
        txtCTMCEL1.setText("");
        txtCTMM2.setText("");
        txtCTMCEL2.setText("");
        txtCTMM3.setText("");
        txtCTMCEL3.setText("");
        txtCTMM4.setText("");
        txtCTMCEL4.setText("");
        txtCTMM5.setText("");
        txtCTMCEL5.setText("");
        txtCTMM6.setText("");
        txtCTMCEL6.setText("");
        txtCTMM7.setText("");
        txtCTMCEL7.setText("");
        txtCTMM8.setText("");
        txtCTMCEL8.setText("");
        this.datoC++;
        datoC=HRRN_HLL(colaC,FinalC,this.tablaE1,this.datoC);   
    }//GEN-LAST:event_btnAceptarCTMMouseClicked

    private void btnAceptarCTMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarCTMActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAceptarCTMActionPerformed

    private void txtCTMMKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMMKeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }
    }//GEN-LAST:event_txtCTMMKeyTyped

    private void txtCTMCELKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMCELKeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }
    }//GEN-LAST:event_txtCTMCELKeyTyped

    private void btnSacaBT1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSacaBT1MouseClicked
             layout.show(pnlCards, "crdRecar");  
    }//GEN-LAST:event_btnSacaBT1MouseClicked

    private void btnSacaBT1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSacaBT1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSacaBT1ActionPerformed

    private void txtMontoRTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoRTAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTAActionPerformed

    private void txtCiRTA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiRTA1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA1ActionPerformed

    private void txtCiRTA1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiRTA1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA1KeyTyped

    private void txtCiRTA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiRTA2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA2ActionPerformed

    private void txtCiRTA2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiRTA2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA2KeyTyped

    private void txtCiRTA3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiRTA3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA3ActionPerformed

    private void txtCiRTA3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiRTA3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA3KeyTyped

    private void txtCiRTA4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiRTA4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA4ActionPerformed

    private void txtCiRTA4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiRTA4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA4KeyTyped

    private void txtCiRTA5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiRTA5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA5ActionPerformed

    private void txtCiRTA5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiRTA5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA5KeyTyped

    private void txtCiRTA6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiRTA6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA6ActionPerformed

    private void txtCiRTA6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiRTA6KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA6KeyTyped

    private void txtCiRTA7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiRTA7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA7ActionPerformed

    private void txtCiRTA7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiRTA7KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA7KeyTyped

    private void txtMontoRTA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoRTA1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA1ActionPerformed

    private void txtMontoRTA1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoRTA1KeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA1KeyTyped

    private void txtMontoRTA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoRTA2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA2ActionPerformed

    private void txtMontoRTA2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoRTA2KeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA2KeyTyped

    private void txtMontoRTA3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoRTA3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA3ActionPerformed

    private void txtMontoRTA3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoRTA3KeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA3KeyTyped

    private void txtMontoRTA4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoRTA4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA4ActionPerformed

    private void txtMontoRTA4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoRTA4KeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA4KeyTyped

    private void txtMontoRTA5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoRTA5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA5ActionPerformed

    private void txtMontoRTA5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoRTA5KeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA5KeyTyped

    private void txtMontoRTA6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoRTA6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA6ActionPerformed

    private void txtMontoRTA6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoRTA6KeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA6KeyTyped

    private void txtMontoRTA7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoRTA7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA7ActionPerformed

    private void txtMontoRTA7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoRTA7KeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA7KeyTyped

    private void txtCTMN1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMN1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMN1KeyTyped

    private void txtCTMN2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMN2KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMN2KeyTyped

    private void txtCTMN3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMN3KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMN3KeyTyped

    private void txtCTMN4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMN4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMN4KeyTyped

    private void txtCTMN5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMN5KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMN5KeyTyped

    private void txtCTMN6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMN6KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMN6KeyTyped

    private void txtCTMN7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMN7KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMN7KeyTyped

    private void txtCTMM1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMM1KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMM1KeyTyped

    private void txtCTMM2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMM2KeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMM2KeyTyped

    private void txtCTMM3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMM3KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMM3KeyTyped

    private void txtCTMM4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMM4KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMM4KeyTyped

    private void txtCTMM5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMM5KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMM5KeyTyped

    private void txtCTMM6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMM6KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMM6KeyTyped

    private void txtCTMM7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMM7KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMM7KeyTyped

    private void txtCTMCEL1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMCEL1KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMCEL1KeyTyped

    private void txtCTMCEL2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMCEL2KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMCEL2KeyTyped

    private void txtCTMCEL3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMCEL3KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMCEL3KeyTyped

    private void txtCTMCEL4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMCEL4KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMCEL4KeyTyped

    private void txtCTMCEL5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMCEL5KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMCEL5KeyTyped

    private void txtCTMCEL6KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMCEL6KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMCEL6KeyTyped

    private void txtCTMCEL7KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMCEL7KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMCEL7KeyTyped

    private void txtCTMN8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMN8KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMN8KeyTyped

    private void txtCTMM8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMM8KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMM8KeyTyped

    private void txtCTMCEL8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCTMCEL8KeyTyped
       char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtCTMCEL8KeyTyped

    private void txtCiRTA8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCiRTA8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA8ActionPerformed

    private void txtCiRTA8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCiRTA8KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCiRTA8KeyTyped

    private void txtMontoRTA8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMontoRTA8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA8ActionPerformed

    private void txtMontoRTA8KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMontoRTA8KeyTyped
        char c= evt.getKeyChar();
        if (c>'9' || c<'0')
        { evt.consume();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_txtMontoRTA8KeyTyped

    private void btnM3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnM3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btnM3MouseEntered
    public boolean minimo6Boleto(String dato)
    {   int x = Integer.parseInt(dato);
        if (x < 7) 
        { return true;}
        else return false;
    }

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(proyectoHRRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(proyectoHRRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(proyectoHRRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(proyectoHRRN.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        proyectoHRRN pt = new proyectoHRRN();
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new proyectoHRRN().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptarCTM;
    private javax.swing.JButton btnAceptarRTA;
    private javax.swing.JButton btnM1;
    private javax.swing.JButton btnM3;
    private javax.swing.JButton btnM4;
    private javax.swing.JButton btnM5;
    private javax.swing.JButton btnMostarRDD;
    private javax.swing.JButton btnMostrarUDTigo;
    private javax.swing.JButton btnSacaBT1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel lblMenuTitle;
    private javax.swing.JLabel lblMenuTitle1;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTitle1;
    private javax.swing.JLabel lblTitle2;
    private javax.swing.JPanel pnlCards;
    private javax.swing.JPanel pnlCrearTigoMoney;
    private javax.swing.JPanel pnlEmpleadosTitle1;
    private javax.swing.JPanel pnlEmpleadosTitle2;
    private javax.swing.JPanel pnlEmpleadosTitle3;
    private javax.swing.JPanel pnlEmpleadosTitle5;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlMostrarUsuariosTigoMoney;
    private javax.swing.JPanel pnlRecargaTarjeta;
    private javax.swing.JPanel pnlRegistroDelDia;
    private javax.swing.JPanel pnpMenuTitle;
    private javax.swing.JPanel pnpMenuTitle1;
    private javax.swing.JTable tblRDD;
    private javax.swing.JTable tblRDD1;
    private javax.swing.JTable tblUDTigo;
    private javax.swing.JTable tblUDTigo1;
    private javax.swing.JTextField txtCTMCEL;
    private javax.swing.JTextField txtCTMCEL1;
    private javax.swing.JTextField txtCTMCEL2;
    private javax.swing.JTextField txtCTMCEL3;
    private javax.swing.JTextField txtCTMCEL4;
    private javax.swing.JTextField txtCTMCEL5;
    private javax.swing.JTextField txtCTMCEL6;
    private javax.swing.JTextField txtCTMCEL7;
    private javax.swing.JTextField txtCTMCEL8;
    private javax.swing.JTextField txtCTMM;
    private javax.swing.JTextField txtCTMM1;
    private javax.swing.JTextField txtCTMM2;
    private javax.swing.JTextField txtCTMM3;
    private javax.swing.JTextField txtCTMM4;
    private javax.swing.JTextField txtCTMM5;
    private javax.swing.JTextField txtCTMM6;
    private javax.swing.JTextField txtCTMM7;
    private javax.swing.JTextField txtCTMM8;
    private javax.swing.JTextField txtCTMN;
    private javax.swing.JTextField txtCTMN1;
    private javax.swing.JTextField txtCTMN2;
    private javax.swing.JTextField txtCTMN3;
    private javax.swing.JTextField txtCTMN4;
    private javax.swing.JTextField txtCTMN5;
    private javax.swing.JTextField txtCTMN6;
    private javax.swing.JTextField txtCTMN7;
    private javax.swing.JTextField txtCTMN8;
    private javax.swing.JTextField txtCiRTA;
    private javax.swing.JTextField txtCiRTA1;
    private javax.swing.JTextField txtCiRTA2;
    private javax.swing.JTextField txtCiRTA3;
    private javax.swing.JTextField txtCiRTA4;
    private javax.swing.JTextField txtCiRTA5;
    private javax.swing.JTextField txtCiRTA6;
    private javax.swing.JTextField txtCiRTA7;
    private javax.swing.JTextField txtCiRTA8;
    private javax.swing.JTextField txtMontoRTA;
    private javax.swing.JTextField txtMontoRTA1;
    private javax.swing.JTextField txtMontoRTA2;
    private javax.swing.JTextField txtMontoRTA3;
    private javax.swing.JTextField txtMontoRTA4;
    private javax.swing.JTextField txtMontoRTA5;
    private javax.swing.JTextField txtMontoRTA6;
    private javax.swing.JTextField txtMontoRTA7;
    private javax.swing.JTextField txtMontoRTA8;
    // End of variables declaration//GEN-END:variables
}
