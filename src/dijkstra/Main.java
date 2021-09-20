package dijsktra;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.lang.*;
import java.io.*;


class ShortestPath {

    static final int V = 81;
    static int a = 0;
    static int [] dist = new int[81];
    static  int [] gecilenSehirler = new  int[20];

    int minDistance(int[] dist, Boolean[] visit)
    {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (!visit[v] && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }

        return min_index;
    }

    int gecilenSehirlerListe( int [][] graph , int src , int plaka){

        int[] dist = new int[V];

        int plakaTemp = 0;



        Boolean[] visit = new Boolean[V];

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            visit[i] = false;
        }

        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {


            int u = minDistance(dist, visit);

            visit[u] = true;


            for (int v = 0; v < V; v++){


                if (!visit[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])

                {

                    dist[v] = dist[u] + graph[u][v];

                    if(v == plaka)
                        plakaTemp = u;
                }


            }
        }

        return plakaTemp;
    }

    int [] gecilenSehirlerYazdır(int [][] graph,int source, int plaka)
    {
        int plakaTemp = 0 , listeIndex = 1,index = 0;
        int [] liste  = new int[20];


        ShortestPath t = new ShortestPath();
        plakaTemp = t.gecilenSehirlerListe(graph, source,plaka);
        liste[0] = plakaTemp;

        while (plakaTemp != source){

            plakaTemp = t.gecilenSehirlerListe(graph,source,plakaTemp);

            liste[listeIndex] = plakaTemp;
            listeIndex++;
        }


        int sayac = 0 ;
        for(int i = 0; i < liste.length ; i++)
        {
            if(liste[i] !=0)
                sayac++;

        }

        int [] clearListe = new int[sayac];
        for(int i = 0; i < sayac ; i++)
        {
            clearListe[i] = liste[i];

        }

        for(int i = 0; i < clearListe.length / 2; i++)
        {
            int temp = clearListe[i];
            clearListe[i] = clearListe[clearListe.length - i - 1];
            clearListe[clearListe.length - i - 1] = temp;

        }

        int [] gecilenSehirler = new int[clearListe.length+1];
        for(int i = 0; i <= clearListe.length; i++)
        {
            if (i == clearListe.length){
                gecilenSehirler[i] = plaka;
            }
            else
                gecilenSehirler[i] = clearListe[i];

        }

        return  gecilenSehirler;

    }

    int[] dijkstra(int[][] graph, int src)
    {
        int[] dist = new int[V];

        int plakaTemp = 0;



        Boolean[] visit = new Boolean[V];

        for (int i = 0; i < V; i++) {
            dist[i] = Integer.MAX_VALUE;
            visit[i] = false;
        }

        dist[src] = 0;


        for (int count = 0; count < V - 1; count++) {


            int u = minDistance(dist, visit);
            visit[u] = true;

            for (int v = 0; v < V; v++){

                if (!visit[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v])
                    dist[v] = dist[u] + graph[u][v];

            }
        }

        return dist;

    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner file = new Scanner(new File("C:\\Users\\Hakan\\Desktop\\Project- 4\\komsuuzaklik.txt"));

        int[][] graph = new int[81][81];

        for (int i = 0 ; i < 81 ; i++){
            String [] value = file.nextLine().split(",");
            for (int j = 0 ; j < 81 ; j++){
                graph[i][j] = Integer.parseInt(value[j]);

            }
        }
        ShortestPath t = new ShortestPath();
        String dizi[] = {"Adana", "Adıyaman", "Afyon", "Ağrı", "Amasya",
                "Ankara", "Antalya", "Artvin", "Aydın", "Balıkesir", "Bilecik","Bingöl",
                "Bitlis", "Bolu", "Burdur", "Bursa", "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır",
                "Edirne", "Elazığ", "Erzincan","Erzurum","Eskişehir", "Gaziantep", "Giresun", "Gümüşhane",
                "Hakkari", "Hatay", "Isparta", "Mersin", "İstanbul", "İzmir", "Kars", "Kastamonu",
                "Kayseri", "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya","Malatya", "Manisa",
                "K.maraş", "Mardin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Rize", "Sakarya",
                "Samsun", "Siirt", "Sinop", "Sivas", "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Şanlıurfa",
                "Uşak", "Van", "Yozgat", "Zonguldak", "Aksaray", "Bayburt", "Karaman", "Kırıkkale", "Batman",
                "Şırnak", "Bartın", "Ardahan", "Iğdır", "Yalova", "Karabük", "Kilis", "Osmaniye", "Düzce"};

        JFrame frame = new JFrame("ÖDEV");
        frame.setLayout(new FlowLayout());

        JPanel panelSehirler = new JPanel();
        JPanel panelHarita = new JPanel();
        ImageIcon imageIcon = new ImageIcon("C:\\Users\\Hakan\\Desktop\\Project- 4\\harita.png");
        JLabel haritaLabel = new JLabel(imageIcon);
        panelHarita.add(haritaLabel);


        JComboBox comboBoxSehir = new JComboBox();
        JButton buttonAdd = new JButton("Ekle");
        for( int i = 0;i<81;i++){

            comboBoxSehir.insertItemAt(dizi[i],i);
        }
        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        ArrayList<Integer> arrayList = new ArrayList<>();

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                textArea.setText(textArea.getText() + comboBoxSehir.getSelectedItem() + "\n");
                panelSehirler.updateUI();
                arrayList.add(comboBoxSehir.getSelectedIndex());
                a++;
            }

        });

        int [][] sehirPiksel = {
                {613,445}, {803,372}, {307,295}, {1093,192}, {638,144}, {448,196},
                {310,445}, {1005,83}, {131,361}, {144,215},{278,182},{937,275},
                {1044,307},{379,135},{288,377},{219,174},{55,168},{503,151},{586,153},
                {209,369},{926,355},{75,43},{855,297},{868,211},{980,187},{315,213},
                {747,430},{800,121},{863,152},{1157,360},{670,505},{305,373},{569,456},
                {220,106},{90,307},{1088,121},{510,87},{620,300},{116,42},{536,267},
                {275,128},{431,371},{276,239},{802,327},{108,294},{717,389},{965,401},
                {161,412},{1002,283},{572,311},{570,363},{766,117},{925,103},{304,130},
                {668,93},{1037,345},{597,35},{714,215},{127,103},{685,171},{875,109},
                {877,260},{838,421},{235,397},{1125,289},{576,214},{394,80},{528,331},
                {913,165},{479,429},{498,213},{986,353},{1075,375},{426,65},{1060,84},
                {1153,171},{234,139},{445,103},{732,462},{673,437},{351,129}
        };

        JTextArea distTextArea = new JTextArea();
        distTextArea.setEditable(false);

        JButton mesafeHesapla = new JButton(" Mesafe Hesapla");
        mesafeHesapla.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//GEÇİLEN ŞEHİRLER

                mesafeHesapla.setText("En kısa yolu göster");

                Graphics g = panelHarita.getGraphics();
                g.setColor(Color.BLUE);

                dist = t.dijkstra(graph,40);
                gecilenSehirler = t.gecilenSehirlerYazdır(graph,40,arrayList.get(0));
                distTextArea.setText("41- "+ (arrayList.get(0)+1)+" arası mesafe = "+ dist[arrayList.get(0)] +" km'dir." + "\n");
                g.drawLine(sehirPiksel[40][0], sehirPiksel[40][1], sehirPiksel[gecilenSehirler[1]][0], sehirPiksel[gecilenSehirler[1]][1]);
                for (int i = 2 ; i < gecilenSehirler.length ; i++) {

                    g.drawLine(sehirPiksel[gecilenSehirler[i-1]][0],sehirPiksel[gecilenSehirler[i-1]][1],
                            sehirPiksel[gecilenSehirler[i]][0], sehirPiksel[gecilenSehirler[i]][1]);


                }

                for (int i = 1 ; i < a ; i++) {

                    Random rand = new Random();
                    int x = rand.nextInt(250);
                    int y = rand.nextInt(250);
                    int z = rand.nextInt(250);
                    g.setColor(new Color(x,y,z));
                    dist = t.dijkstra(graph,arrayList.get(i-1));
                    gecilenSehirler = t.gecilenSehirlerYazdır(graph,arrayList.get(i-1),arrayList.get(i));
                    for(int j = 1 ; j < gecilenSehirler.length ; j++) {
                        g.drawLine(sehirPiksel[gecilenSehirler[j - 1]][0], sehirPiksel[gecilenSehirler[j - 1]][1],
                                sehirPiksel[gecilenSehirler[j]][0], sehirPiksel[gecilenSehirler[j]][1]);


                    }
                    distTextArea.setText(distTextArea.getText() + (arrayList.get(i-1)+1) + " - " +(arrayList.get(i)+1) + "arası mesafe = " + dist[arrayList.get(i)]+ "km'dir." + "\n" );


                }

            }
        });
        panelSehirler.add(distTextArea);
        panelSehirler.add(mesafeHesapla);
        panelSehirler.add(comboBoxSehir);
        panelSehirler.add(buttonAdd);
        panelSehirler.add(textArea);
        panelSehirler.setLayout(new BoxLayout(panelSehirler,BoxLayout.Y_AXIS));
        frame.add(panelSehirler);
        frame.add(panelHarita);
        frame.pack();
        frame.setVisible(true);
    }

}


